package com.project.lakshmi.business.operation.importer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.binance.OperationImporterBinanceService;
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

	@Override
	public List<Operation> importFile(OperationImporterOrigin origin, RawTextFile rawFile) {
		// On valide le header
		validateHeader(origin, rawFile);
		
		List<Operation> operations = new ArrayList<Operation>();
		
		// On passe la main aux constructeurs spécifiques à l'origin
		Operation operation = importNextOperation(origin, rawFile);
		
		while (operation != null) {
			addOperation(operations, operation);
			operation = importNextOperation(origin, rawFile);
		}
		
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
	 * Valide le header, si nécéssaire
	 * Lance une exception si le header n'est pas valide
	 * @param origin
	 * @param rawFile
	 */
	private void validateHeader(OperationImporterOrigin origin, RawTextFile rawFile) {
		
		if (OperationImporterOrigin.BINANCE.equals(origin)) {
			operationImporterBinanceService.validateHeader(rawFile);
		} else {		
			throw new NotYetImplementedException("importNext is not implemented for origin " + origin);
		}
	}
	
	private Operation importNextOperation(OperationImporterOrigin origin, RawTextFile rawFile) {
		
		if (OperationImporterOrigin.BINANCE.equals(origin)) {
			return operationImporterBinanceService.importNextOperation(rawFile);
		}
		
		throw new NotYetImplementedException("importNext is not implemented for origin " + origin);
	}
	
}
