package com.project.lakshmi.business.operation.exporter.qif.stacking;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.investment.OperationInvestment;

/**
 * Valable pour le stacking et le mining
 */
@Service
public interface OperationInvestmentStackingQifExporterService {

	void writeOperation(OperationInvestment operation);

}
