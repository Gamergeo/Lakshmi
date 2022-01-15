package com.project.lakshmi.business.operation.importer.binance.creator;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;

@Service
public interface OperationImporterBinanceTradeService {
	
	Operation importNextOperation(RawTextFile rawFile);
}
