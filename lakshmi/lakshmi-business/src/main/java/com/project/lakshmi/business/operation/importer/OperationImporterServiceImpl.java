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
	
//	
//	
//	@Autowired
//	OperationImporterExtractorService operationImporterCreatorService;
//	
//	@Autowired
//	OperationImporterValidatorService operationImporterValidatorService;
//	
//	@Override
//	public void importOperations(OperationImporterOrigin origin, RawTextFile rawFile) throws IOException {
//		
//		// Etape 2 : Création d'un OperationImporterFile
//		OperationImporterFile operationImporterFile = operationImporterCreatorService.createFile(origin, rawFile);
//		
//		// On valide le fichier
//		operationImporterValidatorService.validate(operationImporterFile);
//		
//		// On extrait les operations à créer
//		
//		// On crée le fichier QIF correspondant
//		
//		
////		// Validation du header
////		String header = reader.readLine();
////		validate(origin, header);
////		
////		// Lecture des lignes
////		String line = reader.readLine();
////		List<Operation> operations = new ArrayList<Operation>();
////		
////		while (line != null) {
////			operations.add(readLine(origin, line));
////			line = reader.readLine();
////		}
//	}
//	
////	private List<String> getNextLines(OperationImporterOrigin origin, BufferedReader reader) {
////		
////		// On prends la première ligne
////		String line = reader.readLine();
////		
////		// Dans le cas d'un trade binance, on prends 3 lignes d'un coup
////		if (operationImporterOriginService.getNumberLines(origin, null))
////	}
////	
////	private List<Operation> importOperation(OperationImporterOrigin origin, List<String> lines) {
////		
////	}
//////	
//////	private void importOperation(OperationImporterOrigin origin, InputStream file) throws IOException {
//////		
//////	}
////	
////	
////	// Le header doit être OK
////	private void validate(OperationImporterOrigin origin, String fileHeader) {
////		String expectedHeader = operationImporterOriginService.getHeader(origin);
////		
////		if (!expectedHeader.equals(fileHeader)) {
////			throw new ApplicationException("Header non valide : " + fileHeader 
////					+ " (attendu : " + expectedHeader + ")");
////		}
////	}
////	
////	private Operation readLine(OperationImporterOrigin origin, String line) {
////		
////		Operation operation;
////		OperationType operationType = operationImporterOriginService.getOperationType(origin);
////		
////		if (OperationType.CURRENT.equals(operationType)) {
////			throw new NotYetImplementedException("read line current operation not implemented");
////		} else if (OperationType.INVESTMENT.equals(operationType)) {
////			operation = operationInvestmentImporterService.readLine(origin, line);
////		} else {
////			throw new NotYetImplementedException("read line other operation not implemented");
////		}
////		
////		return operation;
////	}
}
