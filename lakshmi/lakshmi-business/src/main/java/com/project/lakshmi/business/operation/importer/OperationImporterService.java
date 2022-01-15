package com.project.lakshmi.business.operation.importer;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.importer.OperationImporterOrigin;

@Service
public interface OperationImporterService {
	
	
	void importFile(OperationImporterOrigin origin, RawTextFile rawFile);
	
//	void importOperations(OperationImporterOrigin origin, RawTextFile rawFile) throws IOException;


}
