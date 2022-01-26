package com.project.lakshmi.business.operation.exporter.qif.trade;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;

@Service
public interface OperationInvestmentTradeQifExporterService {

	/**
	 * Un trade se compose de trois opérations distinctes :
	 * 		Le principal
	 * 		L'équilibrage
	 * 		Les fees
	 * @param investment
	 */
	void writeOperation(OperationInvestmentTrade trade);
	

}
