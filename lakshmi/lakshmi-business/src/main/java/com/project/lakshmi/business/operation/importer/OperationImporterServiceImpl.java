package com.project.lakshmi.business.operation.importer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.binance.OperationImporterBinanceService;
import com.project.lakshmi.business.operation.importer.kucoin.OperationImporterKucoinService;
import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.OperationType;
import com.project.lakshmi.model.operation.importer.OperationImporterOrigin;
import com.project.lakshmi.model.operation.investment.InvestmentType;
import com.project.lakshmi.model.operation.investment.OperationInvestment;

@Service("operationImporterService")
public class OperationImporterServiceImpl implements OperationImporterService {
	
	@Autowired
	OperationImporterBinanceService operationImporterBinanceService;
	
	@Autowired
	OperationImporterKucoinService operationImporterKucoinTradeService;

	@Override
	public List<Operation> importFile(OperationImporterOrigin origin, RawTextFile rawFile, RawTextFile feeFile) {
		
		cleanFile(origin, rawFile);
		
		// On valide le header
		validateHeader(origin, rawFile, feeFile);
		
		List<Operation> operations = new ArrayList<Operation>();
		
		// On passe la main aux constructeurs spécifiques à l'origin
		Operation operation = importNextOperation(origin, rawFile, feeFile);
		
		while (operation != null) {
			addOperation(operations, operation);
			operation = importNextOperation(origin, rawFile, feeFile);
		}
		
		// On associe les fee si besoin (cas kucoin)
		associateFee(origin, operations, feeFile);
		
		return operations;
	}
	
	/**
	 * Plutot que d'importer n opérations de stacking, on en importe une seule
	 * La date est alors la dernière
	 * @param existingOperation
	 * @param operationToAdd
	 */
	private void addOperation(List<Operation> existingOperations, Operation operationToAdd) {
		
		boolean addOperation = true;
		
		if (OperationType.INVESTMENT.equals(operationToAdd.getOperationType())) {
			OperationInvestment operationInvestmentToAdd = operationToAdd.asOperationInvestment();

			// Traitement spécial pour le stacking et le mining
			if (InvestmentType.STACKING.equals(operationInvestmentToAdd.getInvestmentType()) ||
					InvestmentType.MINING.equals(operationInvestmentToAdd.getInvestmentType())) {
				
				// On check si une operation de stacking pour cet asset existe déja
				for (Operation existingOperation : existingOperations) {

					if (OperationType.INVESTMENT.equals(operationToAdd.getOperationType())) {
						OperationInvestment existingOperationInvestment = existingOperation.asOperationInvestment();
						
						// On a trouvé une opération de même type et l'asset correspond
						if (existingOperationInvestment.getInvestmentType().equals(operationInvestmentToAdd.getInvestmentType()) &&
								existingOperationInvestment.getInvestment().getAsset().equals(operationInvestmentToAdd.getInvestment().getAsset())) {
							
							// Dans ce cas on update l'existante en ajoutant la quantité
							existingOperationInvestment.getInvestment().addInvestment(operationInvestmentToAdd.getInvestment());
							
							// Si la date est postérieure, c'est celle ci qui est prise en compte
							if (operationInvestmentToAdd.getDate().isAfter(existingOperationInvestment.getDate())) {
								existingOperationInvestment.setDate(operationInvestmentToAdd.getDate());
							}
							
							addOperation = false;
						}
					}
				}
			}
		}
		
		if (addOperation) {
			existingOperations.add(operationToAdd);
		}
	}
	
	/**
	 * Nettoie le fichier, en l'occurence, supprime les caractères en trop
	 */
	private void cleanFile(OperationImporterOrigin origin, RawTextFile rawFile) {
		
		if (OperationImporterOrigin.BINANCE.equals(origin)) {
			operationImporterBinanceService.cleanFile(rawFile);
		} else if (OperationImporterOrigin.KUCOIN.equals(origin)) {
			return;
		} else {		
			throw new NotYetImplementedException("validateHeader is not implemented for origin " + origin);
		}
	}
	
	/**
	 * Valide le header, si nécéssaire
	 * Lance une exception si le header n'est pas valide
	 * @param origin
	 * @param rawFile
	 */
	private void validateHeader(OperationImporterOrigin origin, RawTextFile rawFile, RawTextFile feeFile) {
		
		if (OperationImporterOrigin.BINANCE.equals(origin)) {
			operationImporterBinanceService.validateHeader(rawFile);
		} else if (OperationImporterOrigin.KUCOIN.equals(origin)) {
			operationImporterKucoinTradeService.validateHeaders(rawFile, feeFile);
		} else {		
			throw new NotYetImplementedException("validateHeader is not implemented for origin " + origin);
		}
	}
	
	private Operation importNextOperation(OperationImporterOrigin origin, RawTextFile rawFile, RawTextFile feeFile) {
		
		if (OperationImporterOrigin.BINANCE.equals(origin)) {
			return operationImporterBinanceService.importNextOperation(rawFile);
		} else if (OperationImporterOrigin.KUCOIN.equals(origin)) {
			return operationImporterKucoinTradeService.importNextOperation(rawFile, feeFile);
		} 
		
		throw new NotYetImplementedException("importNext is not implemented for origin " + origin);
	}
	
	/**
	 * Dans le cas de Kucoin, on associe les fee à posteriori à partir du fee file
	 */
	private void associateFee(OperationImporterOrigin origin, List<Operation> operations, RawTextFile feeFile) {
		
		if (OperationImporterOrigin.BINANCE.equals(origin)) { // Rien à faire
			return;
		} else if (OperationImporterOrigin.KUCOIN.equals(origin)) {
			operationImporterKucoinTradeService.associateFee(operations, feeFile);
		} 
	}
	
}
