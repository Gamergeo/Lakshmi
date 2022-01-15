package com.project.lakshmi.business.operation.importer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.binance.OperationImporterBinanceService;
import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.importer.OperationImporterOrigin;

@Service("operationImporterService")
/**
 * cf Documentation/Lakshmi/Technique/Import/import.txt
 */
public class OperationImporterServiceImpl implements OperationImporterService {
	
	@Autowired
	OperationImporterBinanceService operationImporterBinanceService;

	@Override
	public void importFile(OperationImporterOrigin origin, RawTextFile rawFile) {
		// On valide le header
		validateHeader(origin, rawFile);
		
		List<Operation> operations = new ArrayList<Operation>();
		
		// On passe la main aux constructeurs spécifiques à l'origin
		Operation operation = importNextOperation(origin, rawFile);
		
		while (operation != null) {
			operations.add(operation);
			operation = importNextOperation(origin, rawFile);
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
