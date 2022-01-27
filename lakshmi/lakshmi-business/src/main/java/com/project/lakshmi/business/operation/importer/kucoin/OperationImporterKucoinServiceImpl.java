package com.project.lakshmi.business.operation.importer.kucoin;

import java.time.Instant;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.kucoin.extractor.fee.OperationImporterKucoinFeeExtractorService;
import com.project.lakshmi.business.operation.importer.kucoin.extractor.trade.OperationImporterKucoinTradeExtractorService;
import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;
import com.project.lakshmi.technical.ApplicationException;

@Service("operationImporterKucoinService")
public class OperationImporterKucoinServiceImpl implements OperationImporterKucoinService {
	
	@Autowired
	private OperationImporterKucoinTradeExtractorService operationImporterKucoinTradeExtractorService;
	
	@Autowired
	private OperationImporterKucoinFeeExtractorService operationImporterKucoinFeeExtractorService;

	@Override
	public void validateHeaders(RawTextFile rawFile, RawTextFile feeFile) {
		
		// Un header est nécéssaire
		String expectedHeader = OperationImporterKucoinConstants.TRADE_FILE.HEADER; 
		String fileHeader = rawFile.getAndRemoveNext();
		
		if (!expectedHeader.equals(fileHeader)) {
			throw new ApplicationException("Header non valide : " + fileHeader 
					+ " (attendu : " + expectedHeader + ")");
		}
		
		if (feeFile == null) {
			throw new ApplicationException("Fee file non fourni"); 
		}
		
		// Un header est nécéssaire
		expectedHeader = OperationImporterKucoinConstants.FEE_FILE.HEADER; 
		fileHeader = removeSpecialCharacterFromHeader(feeFile.getAndRemoveNext());
		
		if (!expectedHeader.equals(fileHeader)) {
			throw new ApplicationException("Header non valide : " + fileHeader 
					+ " (attendu : " + expectedHeader + ")");
		}
	}

	// Caractères spéciaux à supprimer
	private String removeSpecialCharacterFromHeader(String header) {
		
		for (String specialCharacter : OperationImporterKucoinConstants.FEE_FILE.SPECIAL_CHARACTERS) {
			header = header.replace(specialCharacter, "");
		}
		
		return header;
	}
	
	@Override
	public Operation importNextOperation(RawTextFile tradeFile, RawTextFile feeFile) {
		
		// On ne supprime pas encore la ligne concerné
		String firstLine = tradeFile.getAndRemoveNext();
		
		// Le fichier est terminé
		if (firstLine == null) {
			return null;
		}
		
		// D'abord on crée l'opération
		OperationInvestmentTrade operation = operationImporterKucoinTradeExtractorService.getOperation(firstLine);
		
		// On update l'opération avec les trade suivant si besoin
		Integer numberOperationMerged = updateOperation(tradeFile, feeFile, operation);
		
		// On update les fee avec le feefile, si besoin (cas feeCurrency != KCS)
		// Dans ce cas, on va prends n lignes, n etant le nombre d'opeation merge
		updateFee(feeFile, operation, numberOperationMerged);
	
		return operation;
	}
	
	/**
	 * Update l'opération avec les lignes suivantes si :
	 * 		- elles ont la même date
	 * 		- elles ont les mêmes asset
	 * @return le nombre d'opération mergé
	 */
	private Integer updateOperation(RawTextFile tradeFile, RawTextFile feeFile, OperationInvestmentTrade operationToUpdate) {
		// On cherche si la prochaine ligne concerne ce trade
		String line = tradeFile.getNext();
		int lineNumber = 1;
		int numberOperationMerged = 1;
		
		Instant lineDate = null;
		if (line != null) {
			lineDate = operationImporterKucoinTradeExtractorService.getDate(line);
		} else { // Fichier terminé
			lineDate = null;
		}
		
		// C'est la même date
		while (operationToUpdate.getDate().equals(lineDate)) {
			
			// C'est les mêmes assets
			if (operationToUpdate.getInvestment().getAsset().equals(operationImporterKucoinTradeExtractorService.getAsset(line)) &&
					operationToUpdate.getBalancingInvestment().getAsset().equals(operationImporterKucoinTradeExtractorService.getBalancingAsset(line))) {

				// Dans ce cas on ajoute les investissements
				operationToUpdate.getInvestment().addInvestment(operationImporterKucoinTradeExtractorService.getMainInvestment(line));
				operationToUpdate.getBalancingInvestment().addInvestment(operationImporterKucoinTradeExtractorService.getBalancingInvestment(line));
				operationToUpdate.getFeeInvestment().addInvestment(operationImporterKucoinTradeExtractorService.getFeeInvestment(line));
//				updateFee(feeFile, operationToUpdate);
				
				// On supprime la ligne du fichier
				tradeFile.removeLine(lineNumber);
				line = tradeFile.getNext();
				
				numberOperationMerged ++;
			} else { // On passe a la suivante
				line = tradeFile.getNext();
				lineNumber ++;
			}
			
			if (line != null) {
				lineDate = operationImporterKucoinTradeExtractorService.getDate(line);
			} else { // Fichier terminé
				lineDate = null;
			}
		}
		
		return numberOperationMerged;
	}
	/** 
	 * On cherche les fees correspondants
	 * On admets que les fees sont dans l'ordre.
	 * On prends numberOperationMerged line
	 * 		Attention, dans le cas du KCS, le fee se trouve dans le mainFile, on ne fait donc rien
	 */
	private void updateFee(RawTextFile feeFile, OperationInvestmentTrade operation, Integer numberOperationMerged) {

		if (operation.getFeeInvestment() == null) { // On a pas déja importé le fee
			
			String feeLine = feeFile.getAndRemoveNext();
			
			if (feeLine == null) {
				throw new ApplicationException("Pas assez de fees dans le feefile");
			}
			
			operation.setFeeInvestment(operationImporterKucoinFeeExtractorService.getInvestment(feeLine));
			
			// Au moins deux opérations ont été mergés, on merge les fees aussi
			for (int i = 2; i <= numberOperationMerged; i++) {
				
				feeLine = feeFile.getAndRemoveNext();
				
				if (feeLine == null) {
					throw new ApplicationException("Pas assez de fees dans le feefile");
				}
				
				operation.getFeeInvestment().addInvestment(operationImporterKucoinFeeExtractorService.getInvestment(feeLine));
			}
		}
	}
	
	/**
	 * Prépare le fichier de fee (supprime les lignes non pertinentes)
	 * Le fichier est à l'envers, on inverse les lignes
	 */
	@Override
	public RawTextFile prepareFeeFile(RawTextFile feeFile) {
		RawTextFile resultFile = new RawTextFile();
		 
		 String line = feeFile.getAndRemoveNext();
		 
		 while (!StringUtils.isEmpty(line)) {
			 
			 // C'est bien un fee, on l'ajoute
			 if (operationImporterKucoinFeeExtractorService.isFee(line)) {
				 resultFile.addLine(line);
			 }
			 
			 line = feeFile.getAndRemoveNext();
		 }
		 
		 resultFile.reverseLine();
		 return resultFile;
	}
}
