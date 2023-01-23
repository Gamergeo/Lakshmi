package com.project.lakshmi.business.operation.importer.kucoin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.lakshmi.business.operation.importer.kucoin.extractor.fee.OperationImporterKucoinFeeExtractorService;
import com.project.lakshmi.business.operation.importer.kucoin.extractor.trade.OperationImporterKucoinTradeExtractorService;
import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.OperationDateComparer;
import com.project.lakshmi.model.operation.investment.Investment;
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
		
		// Un header est n�c�ssaire
		String expectedHeader = OperationImporterKucoinConstants.TRADE_FILE.HEADER; 
		String fileHeader = rawFile.getAndRemoveNext();
		
		if (!expectedHeader.equals(fileHeader)) {
			throw new ApplicationException("Header non valide : " + fileHeader 
					+ " (attendu : " + expectedHeader + ")");
		}
		
		if (feeFile == null) {
			throw new ApplicationException("Fee file non fourni"); 
		}
		
		// Un header est n�c�ssaire
		expectedHeader = OperationImporterKucoinConstants.FEE_FILE.HEADER; 
		fileHeader = removeSpecialCharacterFromHeader(feeFile.getAndRemoveNext());
		
		if (!expectedHeader.equals(fileHeader)) {
			throw new ApplicationException("Header non valide : " + fileHeader 
					+ " (attendu : " + expectedHeader + ")");
		}
	}

	// Caract�res sp�ciaux � supprimer
	private String removeSpecialCharacterFromHeader(String header) {
		
		for (String specialCharacter : OperationImporterKucoinConstants.FEE_FILE.SPECIAL_CHARACTERS) {
			header = header.replace(specialCharacter, "");
		}
		
		return header;
	}
	
	@Override
	public Operation importNextOperation(RawTextFile tradeFile, RawTextFile feeFile) {
		
		// On ne supprime pas encore la ligne concern�
		String line = tradeFile.getAndRemoveNext();
		
		// Le fichier est termin�
		if (line == null) {
			return null;
		}
		
		// D'abord on cr�e l'op�ration
		OperationInvestmentTrade operation = operationImporterKucoinTradeExtractorService.getOperation(line);
		
//		// On update l'op�ration avec les trade suivant si besoin
//		Integer numberOperationMerged = updateOperation(tradeFile, feeFile, operation);
//		
//		// On update les fee avec le feefile, si besoin (cas feeCurrency != KCS)
//		// Dans ce cas, on va prends n lignes, n etant le nombre d'opeation merge
//		updateFee(feeFile, operation, numberOperationMerged);
//	
		return operation;
	}
	
	/**
	 * Associe les fees aux operations d�ja import�e
	 */
	@Override
	public void associateFee(List<Operation> operations, RawTextFile feeFile) {

		// D'abord, on trie les operations par date invers�e (plus recente en premier)
		Collections.sort(operations, new OperationDateComparer());
		Collections.reverse(operations);
		
		// On importe le feeFile sous une liste d'op�ration tri� par date decroissante
		List<OperationInvestmentTrade> feeOperations = getFeesOperation(feeFile);
		
		// On associe la premi�re op�ration � son fee
		// Pour cela, il faut que le fee ne soit pas d�ja mis en place (cas KCS)
		// Et que la date du fee > sup�rieur � la date de l'op�ration
		// On prends alors la plus recente
		for (Operation operation : operations) {
			
			// On a normalement que des trade
			OperationInvestmentTrade trade = (OperationInvestmentTrade) operation;
			
			// Le fee n'est pas d�ja renseign�, on le recherche
			Investment fee = getNextFee(trade, feeOperations);
			trade.setFeeInvestment(fee);
		}
		
		// On remets les operations dans l'ordre (inutile mais sympa)
		Collections.reverse(operations);
	}
	
	private List<OperationInvestmentTrade> getFeesOperation(RawTextFile feeFile) {
		 String line = feeFile.getAndRemoveNext();
		 List<OperationInvestmentTrade> feeOperations = new ArrayList<OperationInvestmentTrade>();
		 
		 while (!StringUtils.isEmpty(line)) {
			 
			 OperationInvestmentTrade fee = operationImporterKucoinFeeExtractorService.getFeeOperation(line);
			 
			 // On a trouv� un fee, on l'ajoute
			 if (fee != null) {
				 feeOperations.add(fee);
			 }
			 
			 line = feeFile.getAndRemoveNext();
		 }
		 
		 // On trie par date invers�e
		Collections.sort(feeOperations, new OperationDateComparer());
		Collections.reverse(feeOperations);
		 
		 return feeOperations;
	}
	
	/**
	 * @return le fee cens� correspondre � l'op�ration
	 * Peut etre nul si on en trouve pas
	 */
	private Investment getNextFee(OperationInvestmentTrade trade, List<OperationInvestmentTrade> feeOperations) {
		
		// Le fee est d�ja renseign�, on ne fait rien
		if (trade.getFeeInvestment() != null) {
			return trade.getFeeInvestment();
		}
		
		// Il n'y a pas de fee 
		if (feeOperations.isEmpty()) {
			return null;
		}
		
		// Sinon, on cherche on prends la premi�re ligne, si la date du fee est sup�rieure ou egale� la date de l'op�ration
		if (trade.getDate().isBefore(feeOperations.get(0).getDate()) ||
				trade.getDate().equals(feeOperations.get(0).getDate())) {
			
			// Dans ce cas on a trouv� le fee, on le supprime de la liste
			Investment fee = feeOperations.get(0).getFeeInvestment();
			feeOperations.remove(0);
			
			return fee; 
		}
		
		return null;
	}
	
//	/**
//	 * Update l'op�ration avec les lignes suivantes si :
//	 * 		- elles ont la m�me date
//	 * 		- elles ont les m�mes asset
//	 * @return le nombre d'op�ration merg�
//	 */
//	private Integer updateOperation(RawTextFile tradeFile, RawTextFile feeFile, OperationInvestmentTrade operationToUpdate) {
//		// On cherche si la prochaine ligne concerne ce trade
//		String line = tradeFile.getNext();
//		int lineNumber = 1;
//		int numberOperationMerged = 1;
//		
//		Instant lineDate = null;
//		if (line != null) {
//			lineDate = operationImporterKucoinTradeExtractorService.getDate(line);
//		} else { // Fichier termin�
//			lineDate = null;
//		}
//		
//		// C'est la m�me date
//		while (operationToUpdate.getDate().equals(lineDate)) {
//			
//			// C'est les m�mes assets
//			if (operationToUpdate.getInvestment().getAsset().equals(operationImporterKucoinTradeExtractorService.getAsset(line)) &&
//					operationToUpdate.getBalancingInvestment().getAsset().equals(operationImporterKucoinTradeExtractorService.getBalancingAsset(line))) {
//
//				// Dans ce cas on ajoute les investissements
//				operationToUpdate.getInvestment().addInvestment(operationImporterKucoinTradeExtractorService.getMainInvestment(line));
//				operationToUpdate.getBalancingInvestment().addInvestment(operationImporterKucoinTradeExtractorService.getBalancingInvestment(line));
//				operationToUpdate.getFeeInvestment().addInvestment(operationImporterKucoinTradeExtractorService.getFeeInvestment(line));
////				updateFee(feeFile, operationToUpdate);
//				
//				// On supprime la ligne du fichier
//				tradeFile.removeLine(lineNumber);
//				line = tradeFile.getNext();
//				
//				numberOperationMerged ++;
//			} else { // On passe a la suivante
//				line = tradeFile.getNext();
//				lineNumber ++;
//			}
//			
//			if (line != null) {
//				lineDate = operationImporterKucoinTradeExtractorService.getDate(line);
//			} else { // Fichier termin�
//				lineDate = null;
//			}
//		}
//		
//		return numberOperationMerged;
//	}
//	/** 
//	 * On cherche les fees correspondants
//	 * On admets que les fees sont dans l'ordre.
//	 * On prends numberOperationMerged line
//	 * 		Attention, dans le cas du KCS, le fee se trouve dans le mainFile, on ne fait donc rien
//	 */
//	private void updateFee(RawTextFile feeFile, OperationInvestmentTrade operation, Integer numberOperationMerged) {
//
//		if (operation.getFeeInvestment() == null) { // On a pas d�ja import� le fee
//			
//			String feeLine = feeFile.getAndRemoveNext();
//			
//			if (feeLine == null) {
//				throw new ApplicationException("Pas assez de fees dans le feefile");
//			}
//			
//			operation.setFeeInvestment(operationImporterKucoinFeeExtractorService.getInvestment(feeLine));
//			
//			// Au moins deux op�rations ont �t� merg�s, on merge les fees aussi
//			for (int i = 2; i <= numberOperationMerged; i++) {
//				
//				feeLine = feeFile.getAndRemoveNext();
//				
//				if (feeLine == null) {
//					throw new ApplicationException("Pas assez de fees dans le feefile");
//				}
//				
//				operation.getFeeInvestment().addInvestment(operationImporterKucoinFeeExtractorService.getInvestment(feeLine));
//			}
//		}
//	}
//	
//	/**
//	 * Pr�pare le fichier de fee (supprime les lignes non pertinentes)
//	 * Le fichier est � l'envers, on inverse les lignes
//	 */
//	@Override
//	public RawTextFile prepareFeeFile(RawTextFile feeFile) {
//		RawTextFile resultFile = new RawTextFile();
//		 
//		 String line = feeFile.getAndRemoveNext();
//		 
//		 while (!StringUtils.isEmpty(line)) {
//			 
//			 // C'est bien un fee, on l'ajoute
//			 if (operationImporterKucoinFeeExtractorService.isFee(line)) {
//				 resultFile.addLine(line);
//			 }
//			 
//			 line = feeFile.getAndRemoveNext();
//		 }
//		 
//		 resultFile.reverseLine();
//		 return resultFile;
//	}
}
