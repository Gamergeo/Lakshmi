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
			operationQifExporterService.writeOperation(operation);
			operationTextExporterService.writeOperation(operation);
		} else {
			operationQifExporterService.writeOperation(operation);
		}
	}

	@Override
	public OperationImporterOrigin getOrigin() {
		return origin;
	}

	public void setOrigin(OperationImporterOrigin origin) {
		this.origin = origin;
	}
}
