package com.project.lakshmi.business.operation.exporter.multitrade;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.investment.OperationInvestmentMultiTrade;

@Service
public interface OperationInvestmentMultiTradeExporterService {

	/**
	 * Un multi trade se compose de plusieurs opérations distinctes :
	 * 		Le principal
	 * 		Les équilibrages
	 */
	void writeOperation(OperationInvestmentMultiTrade multitrade);
}
