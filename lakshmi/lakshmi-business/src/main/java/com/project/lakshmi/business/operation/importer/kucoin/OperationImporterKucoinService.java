package com.project.lakshmi.business.operation.importer.kucoin;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;

@Service
public interface OperationImporterKucoinService {

	/**
	 * Valide les header
	 */
	void validateHeaders(RawTextFile rawFile, RawTextFile feeFile);
	
	RawTextFile prepareFeeFile(RawTextFile feeFile);

	Operation importNextOperation(RawTextFile rawFile, RawTextFile feeFile);
}
