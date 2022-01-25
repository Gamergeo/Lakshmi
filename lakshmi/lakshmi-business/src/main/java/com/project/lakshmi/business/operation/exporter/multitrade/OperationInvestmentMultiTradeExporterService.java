package com.project.lakshmi.business.operation.exporter.multitrade;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.investment.OperationInvestmentMultiTrade;

@Service
public interface OperationInvestmentMultiTradeExporterService {

	/**
	 * Un multi trade se compose de plusieurs op�rations distinctes :
	 * 		Le principal
	 * 		Les �quilibrages
	 */
	void writeOperation(OperationInvestmentMultiTrade multitrade);
}
