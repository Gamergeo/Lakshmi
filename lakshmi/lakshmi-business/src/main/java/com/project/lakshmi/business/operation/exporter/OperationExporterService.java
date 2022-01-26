package com.project.lakshmi.business.operation.exporter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.importer.OperationImporterOrigin;

/**
 * Crée un fichier secondaire pour les opérations à vérifier
 */
@Service
public interface OperationExporterService {
	
	void exportOperations(OperationImporterOrigin origin, List<Operation> operations);

	OperationImporterOrigin getOrigin();
}
