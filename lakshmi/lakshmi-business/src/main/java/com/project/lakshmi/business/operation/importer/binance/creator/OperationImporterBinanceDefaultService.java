package com.project.lakshmi.business.operation.importer.binance.creator;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;

/**
 * Cas de toutes les lignes non ignorés non trade
 */
@Service
public interface OperationImporterBinanceDefaultService {
	
	Operation importNextOperation(RawTextFile rawFile);
}
