package com.project.lakshmi.business.operation.exporter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.importer.OperationImporterOrigin;

/**
 * Cr�e un fichier secondaire pour les op�rations � v�rifier
 */
@Service
public interface OperationExporterService {
	
	void exportOperations(OperationImporterOrigin origin, List<Operation> operations);

	OperationImporterOrigin getOrigin();
}
