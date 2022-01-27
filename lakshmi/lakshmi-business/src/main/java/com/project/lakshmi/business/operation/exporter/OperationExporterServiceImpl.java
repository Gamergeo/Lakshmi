package com.project.lakshmi.business.operation.exporter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.exporter.qif.OperationQifExporterService;
import com.project.lakshmi.business.operation.exporter.text.OperationTextExporterService;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.importer.OperationImporterOrigin;
import com.project.lakshmi.model.operation.investment.InvestmentType;
import com.project.lakshmi.model.operation.investment.OperationInvestment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;

@Service("operationExporterService")
public class OperationExporterServiceImpl implements OperationExporterService {
	
	private OperationImporterOrigin origin;
	
	@Autowired
	OperationQifExporterService operationQifExporterService;
	
	@Autowired
	OperationTextExporterService operationTextExporterService;

	@Override
	public String exportOperations(OperationImporterOrigin origin, List<Operation> operations) {
		
		setOrigin(origin);
		
		// On crée les deux fichiers
		String qifFileName = operationQifExporterService.createFile();
		operationTextExporterService.createFile();
		
		for (Operation operation : operations) {
			// On ne peut ecrire que des investissements pour l'instant
			writeOperation(operation.asOperationInvestment());
		}
		
		return qifFileName;
	}
	
	private void writeOperation(OperationInvestment operation) {
		
		// Dans le cas withdraw / deposit, il faut renseigner les deux fichiers
		if (InvestmentType.WITHDRAW.equals(operation.getInvestmentType()) ||
				InvestmentType.DEPOSIT.equals(operation.getInvestmentType())) {
			operationTextExporterService.writeOperation(operation);
		} 
		
		// Si c'est un trade et qu'il n'y a pas de fee, c'est un point d'attention 
		if (InvestmentType.TRADE.equals(operation.getInvestmentType())) {
			 OperationInvestmentTrade trade = (OperationInvestmentTrade) operation;
			 
			 if (trade.getFeeInvestment() == null) {
				operationTextExporterService.writeOperation(operation);
			 }
		 }

		// Dans tous les cas on exporte vers le fichier qif
		operationQifExporterService.writeOperation(operation);
	}

	@Override
	public OperationImporterOrigin getOrigin() {
		return origin;
	}

	public void setOrigin(OperationImporterOrigin origin) {
		this.origin = origin;
	}
}
