package com.project.lakshmi.business.operation.importer.binance.creator;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;

/**
 * Cas  particulier des multi trade (small bnb exchange)
 */
@Service
public interface OperationImporterBinanceMultiTradeService {
	
	Operation importNextOperation(RawTextFile rawFile);
}
