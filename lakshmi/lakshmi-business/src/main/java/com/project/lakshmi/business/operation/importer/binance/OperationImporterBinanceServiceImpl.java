package com.project.lakshmi.business.operation.importer.binance;

import java.util.Date;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.binance.extractor.OperationImporterBinanceConstants;
import com.project.lakshmi.business.operation.importer.binance.extractor.OperationImporterBinanceExtractorService;
import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;
import com.project.lakshmi.technical.ApplicationException;

@Service("operationImporterBinanceService")
public class OperationImporterBinanceServiceImpl implements OperationImporterBinanceService {
	
	@Autowired
	private OperationImporterBinanceExtractorService operationImporterBinanceExtractorService;
	
	/**
	 * Valide le header, si nécéssaire
	 * Lance une exception si le header n'est pas valide
	 * @param origin
	 * @param rawFile
	 */
	@Override
	public void validateHeader(RawTextFile rawFile) {
		
		// Un header est nécéssaire
		String expectedHeader = OperationImporterBinanceConstants.HEADER; 
		String fileHeader = rawFile.getAndRemoveNext();
		
		if (!expectedHeader.equals(fileHeader)) {
			throw new ApplicationException("Header non valide : " + fileHeader 
					+ " (attendu : " + expectedHeader + ")");
		}
	}

	@Override
	public Operation importNextOperation(RawTextFile rawFile) {
		
		// On ne supprime pas encore la ligne concerné
		String firstLine = rawFile.getNext();
		
		// Le fichier est terminé
		if (firstLine == null) {
			return null;
		}
		
		Operation operation = null;
		
		if (operationImporterBinanceExtractorService.isTrade(firstLine)) {
			operation = importNextTradeOperation(rawFile);
		} else {
			operation = importNextNonTradeOperation(firstLine);
		}
		
		return operation;
	}
	
	private Operation importNextNonTradeOperation(String line) {
		throw new NotYetImplementedException();
	}
	
	/*
	 *  Cas particulier du trade Binance :
	 *  Il est sur plusieurs lignes (2 ou 3)
	 * 	Ces lignes sont dans un ordre aléatoire 
	 */
	private Operation importNextTradeOperation(RawTextFile rawFile) {
		OperationInvestmentTrade operation = new OperationInvestmentTrade();
		
		String firstLine = rawFile.getAndRemoveNext();
		Date operationDate = operationImporterBinanceExtractorService.getDate(firstLine);
		operation.setDate(operationDate);
		boolean mainFound = false;
		boolean balancingFound = false;
		boolean feeFound = false;
		
		// On met en place les infos correspondantes à la ligne trouvée
		if (operationImporterBinanceExtractorService.isTradeMain(firstLine)) {
			mainFound = true;
			fillTradeMainInfos(operation, firstLine);
		} else if (operationImporterBinanceExtractorService.isTradeBalancing(firstLine)) {
			balancingFound = true;
			fillTradeBalancingInfos(operation, firstLine);
		} else if (operationImporterBinanceExtractorService.isTradeFee(firstLine)) {
			feeFound = true;
			fillTradeFeeInfos(operation, firstLine);
		} else { // Ne devrait pas arriver
			throw new ApplicationException("importNextTradeOperation error");
		}
		
		// On cherche la prochaine ligne qui concerne ce trade
		String line = rawFile.getNext();
		int lineNumber = 1;
		boolean continueSearch = true;
		
		while (continueSearch) {

			Date lineDate = operationImporterBinanceExtractorService.getDate(line);
			boolean removeLine = false;
			
			// Si les dates ne correspondent pas, la recherche est arrété, dans tous les cas
			if (!operationDate.equals(lineDate)) {
				continueSearch = false;
			} else {
				
				// Si on trouve une partie et que celle ci n'a pas déja été définie,
				// on le fait et on remove la ligne correspondante
				if (!mainFound && operationImporterBinanceExtractorService.isTradeMain(line)) {
					mainFound = true;
					removeLine = true;
					fillTradeMainInfos(operation, line);
				} else if (!balancingFound && operationImporterBinanceExtractorService.isTradeBalancing(line)) {
					balancingFound = true;
					removeLine = true;
					fillTradeBalancingInfos(operation, line);
				} else if (!feeFound && operationImporterBinanceExtractorService.isTradeFee(line)) {
					feeFound = true;
					removeLine = true;
					fillTradeFeeInfos(operation, line);
				}

				// Si la ligne est a supprimé, on l'a delete
				if (removeLine) {
					rawFile.removeLine(lineNumber);
				}
				
				// Si tout a été trouvé, on arrete la recherche
				if (mainFound && balancingFound && feeFound) {
					continueSearch = false;
				}
			}
			
			// Si on continue la recherche, on prépare la prochaine ligne
			if (continueSearch) {
				
				// Si la ligne n'a pas été supprimé, on augmente le ligne number
				if (!removeLine) {
					lineNumber ++;
				}
				
				line = rawFile.getLine(lineNumber);
				
				// Si la ligne est nulle, on a fini le fichier, la recherche s'arrete
				if (line == null) {
					continueSearch = false;
				}
			}
		}
		
		// Si au moins le main et le balancing ne sont pas definis, c'est une erreur
		// Le fee peut être nul
		if (!mainFound || !balancingFound) {
			throw new ApplicationException("Error : L'opération n'a pas pu etre importé, infos manquante : " + firstLine);
		}
		
		return operation;
	}
	
	private void fillTradeMainInfos(OperationInvestmentTrade operation, String line) {
		Investment investment =  operationImporterBinanceExtractorService.getInvestment(line);
		operation.setInvestment(investment);
	}
	
	private void fillTradeBalancingInfos(OperationInvestmentTrade operation, String line) {
		Investment investment =  operationImporterBinanceExtractorService.getInvestment(line);
		operation.setBalancingInvestment(investment);
	}
	
	private void fillTradeFeeInfos(OperationInvestmentTrade operation, String line) {
		Investment investment =  operationImporterBinanceExtractorService.getInvestment(line);
		operation.setFeeInvestment(investment);
	}
	
}
