package com.project.lakshmi.business.operation.importer.binance.creator;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.binance.extractor.OperationImporterBinanceExtractorService;
import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentMultiTrade;
import com.project.lakshmi.technical.ApplicationException;

@Service("operationImporterBinanceMultiTradeService")
public class OperationImporterBinanceMultiTradeServiceImpl implements OperationImporterBinanceMultiTradeService {
	
	@Autowired
	private OperationImporterBinanceExtractorService operationImporterBinanceExtractorService;
	
	@Override
	public Operation importNextOperation(RawTextFile rawFile) {
		OperationInvestmentMultiTrade operation = new OperationInvestmentMultiTrade();
		
		String firstLine = rawFile.getAndRemoveNext();
		Instant operationDate = operationImporterBinanceExtractorService.getDate(firstLine);
		operation.setDate(operationDate);
		
		// On met en place les infos correspondantes à la ligne trouvée
		fillMultiTradeInfos(operation, firstLine);
		
		// On cherche si la prochaine ligne concerne ce trade
		String line = rawFile.getNext();
		Instant lineDate = null;
		
		if (line != null) {
			lineDate = operationImporterBinanceExtractorService.getDate(line);
		} else {
			lineDate = null;
		}
		
		while (operationDate.equals(lineDate)) {
			
			//La ligne est prise en compte : on la supprime du fichier
			rawFile.removeNext();
			fillMultiTradeInfos(operation, line);
			line = rawFile.getNext(); // On prends la prochaine ligne sans la supprimer
			
			if (line != null) {
				lineDate = operationImporterBinanceExtractorService.getDate(line);
			} else {
				lineDate = null;
			}
		}
		
		// Il manque une partie au trade, exception
		if (operation.getInvestment() == null || operation.getBalancingInvestments().isEmpty()) {
			throw new ApplicationException("Trade incorrect : " + firstLine);
		}
		
		return operation;
	}
	
	private void fillMultiTradeInfos(OperationInvestmentMultiTrade operation, String line) {
		
		Investment lineInvestment = operationImporterBinanceExtractorService.getInvestment(line);
		
		// On met en place les infos correspondantes à la ligne trouvée
		// Pour la main part, on additionne, pour la left on mets tous les investissements dans la liste
		if (operationImporterBinanceExtractorService.isMultiTradeMain(line)) {
			
			if (operation.getInvestment() == null) {
				operation.setInvestment(lineInvestment);
			} else {
				operation.getInvestment().addInvestment(lineInvestment);
			}
		} else if (operationImporterBinanceExtractorService.isMultiTradeBalancing(line)) {
			operation.addBalancingInvestment(lineInvestment);
		} else { // Ne devrait pas arriver
			throw new ApplicationException("fillMultiTradeInfos error");
		}
	}
}
