package com.project.lakshmi.business.operation.exporter.trade;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;

@Service
public interface OperationInvestmentTradeExporterService {

	/**
	 * Un trade se compose de trois opérations distinctes :
	 * 		Le principal
	 * 		L'équilibrage
	 * 		Les fees
	 * @param investment
	 */
	void writeOperation(OperationInvestmentTrade trade);
	

}
