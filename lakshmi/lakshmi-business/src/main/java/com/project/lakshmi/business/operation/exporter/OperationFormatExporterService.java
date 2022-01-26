package com.project.lakshmi.business.operation.exporter;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.investment.OperationInvestment;

/**
 * Crée un fichier secondaire pour les opérations à vérifier
 */
@Service
public interface OperationFormatExporterService {
	
	void createFile();
	
	void writeOperation(OperationInvestment operation);
	

}
