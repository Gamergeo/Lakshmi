package com.project.lakshmi.business.operation.importer;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.Importer.OperationImporterOrigin;

import javassist.NotFoundException;

@Service
public interface OperationImporterService {

	void importOperations(OperationImporterOrigin origin, InputStream file) throws IOException;


}
