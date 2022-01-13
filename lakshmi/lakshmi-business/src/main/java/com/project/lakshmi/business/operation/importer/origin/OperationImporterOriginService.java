package com.project.lakshmi.business.operation.importer.origin;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.OperationType;
import com.project.lakshmi.model.operation.Importer.OperationImporterOrigin;

@Service
public interface OperationImporterOriginService {

	String getHeader(OperationImporterOrigin origin);

	OperationType getOperationType(OperationImporterOrigin origin);

	String getSeparator(OperationImporterOrigin origin);

	int getItemAmountIndex(OperationImporterOrigin origin);


}
