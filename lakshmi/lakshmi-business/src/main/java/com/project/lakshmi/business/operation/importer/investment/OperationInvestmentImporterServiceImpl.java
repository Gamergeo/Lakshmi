package com.project.lakshmi.business.operation.importer.investment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.origin.OperationImporterOriginService;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.OperationInvestment;
import com.project.lakshmi.model.operation.Importer.OperationImporterOrigin;

@Service("operationImporterService")
public class OperationInvestmentImporterServiceImpl implements OperationInvestmentImporterService {
	
	@Autowired
	OperationImporterOriginService operationImporterOriginService;
	
	@Override
	public Operation readLine(OperationImporterOrigin origin, String line) {
		
		OperationInvestment operation = new OperationInvestment();
		
		// On split la ligne
		String[] lineValues = line.split(operationImporterOriginService.getSeparator(origin));
		
		// On récupère les infos importantes
		Double itemAmount = Double.valueOf(lineValues[operationImporterOriginService.getItemAmountIndex(origin)]);
		operation.setItemAmount(itemAmount);
		
		return operation;
	}
	
}
