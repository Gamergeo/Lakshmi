package com.project.lakshmi.business.operation.exporter.qif.financing;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.investment.OperationInvestment;

/**
 * Cas withdraw / deposit
 */
@Service
public interface OperationInvestmentFinancingQifExporterService {

	void writeOperation(OperationInvestment operation);
	

}
