package com.project.lakshmi.business.operation.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.investment.OperationInvestmentImporterService;
import com.project.lakshmi.business.operation.importer.origin.OperationImporterOriginService;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.OperationType;
import com.project.lakshmi.model.operation.Importer.OperationImporterOrigin;
import com.project.lakshmi.technical.ApplicationException;

@Service("operationImporterService")
public class OperationImporterServiceImpl implements OperationImporterService {
	
	@Autowired
	OperationInvestmentImporterService operationInvestmentImporterService;
	
	@Autowired
	OperationImporterOriginService operationImporterOriginService;
	
	@Override
	public void importOperations(OperationImporterOrigin origin, InputStream file) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		
		// Validation du header
		String header = reader.readLine();
		validate(origin, header);
		
		// Lecture des lignes
		String line = reader.readLine();
		List<Operation> operations = new ArrayList<Operation>();
		
		while (line != null) {
			operations.add(readLine(origin, line));
			line = reader.readLine();
		}
	}
	
//	private List<String> getNextLines(OperationImporterOrigin origin, InputStream file) {
//		
//		// On prends la première ligne
//		String line = reader.readLine();
//		
//		// Dans le cas d'un trade binance, on prends 3 lignes d'un coup
//		if (OperationImporterOrigin.BINANCE.equals(line))
//	}
//	
//	private void importOperation(OperationImporterOrigin origin, InputStream file) throws IOException {
//		
//	}
	
	
	// Le header doit être OK
	private void validate(OperationImporterOrigin origin, String fileHeader) {
		String expectedHeader = operationImporterOriginService.getHeader(origin);
		
		if (!expectedHeader.equals(fileHeader)) {
			throw new ApplicationException("Header non valide : " + fileHeader 
					+ " (attendu : " + expectedHeader + ")");
		}
	}
	
	private Operation readLine(OperationImporterOrigin origin, String line) {
		
		Operation operation;
		OperationType operationType = operationImporterOriginService.getOperationType(origin);
		
		if (OperationType.CURRENT.equals(operationType)) {
			throw new NotYetImplementedException("read line current operation not implemented");
		} else if (OperationType.INVESTMENT.equals(operationType)) {
			operation = operationInvestmentImporterService.readLine(origin, line);
		} else {
			throw new NotYetImplementedException("read line other operation not implemented");
		}
		
		return operation;
	}
}
