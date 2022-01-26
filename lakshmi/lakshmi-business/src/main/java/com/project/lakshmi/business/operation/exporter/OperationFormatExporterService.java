package com.project.lakshmi.business.operation.exporter;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.investment.OperationInvestment;

/**
 * Cr�e un fichier secondaire pour les op�rations � v�rifier
 */
@Service
public interface OperationFormatExporterService {
	
	void createFile();
	
	void writeOperation(OperationInvestment operation);
	

}
