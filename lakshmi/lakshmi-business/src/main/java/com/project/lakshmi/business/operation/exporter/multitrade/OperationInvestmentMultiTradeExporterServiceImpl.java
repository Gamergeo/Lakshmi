package com.project.lakshmi.business.operation.exporter.multitrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.exporter.trade.OperationInvestmentTradeExporterServiceImpl;
import com.project.lakshmi.business.operation.investment.InvestmentService;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentMultiTrade;

@Service("operationInvestmentMultiTradeExporterService")
public class OperationInvestmentMultiTradeExporterServiceImpl extends OperationInvestmentTradeExporterServiceImpl implements OperationInvestmentMultiTradeExporterService {
	
	@Autowired
	InvestmentService investmentService;

	/**
	 * Un trade se compose de trois op�rations distinctes :
	 * 		Le principal
	 * 		L'�quilibrage
	 * 		Les fees
	 * @param investment
	 */
	@Override
	public void writeOperation(OperationInvestmentMultiTrade multiTrade) {
		
		// On r�cup�re le montant total de l'op�ration
		Double totalPrice = investmentService.getTotal(multiTrade.getInvestment(), multiTrade.getDate());
		
		// Op�ration principale
		// Commentaire
		String memo = "Conversion " + toExactString(multiTrade.getInvestment().getQuantity()) + " BNB";
		write(multiTrade.getDate(), multiTrade.getInvestment(), getTradeType(multiTrade.getInvestment()), 
				totalPrice, 0d, memo);
		
		// Op�rations d'equilibrage
		for (Investment balancingInvestment : multiTrade.getBalancingInvestments()) {
			memo = "Conversion de ";
			memo += toRoundedString(balancingInvestment.getQuantity()) + " "; 
			memo += balancingInvestment.getAsset().getIsin() + " en BNB";

			// On r�cup�re le prix de la transaction balancing
			totalPrice = investmentService.getTotal(balancingInvestment, multiTrade.getDate());
			
			write(multiTrade.getDate(), balancingInvestment, getTradeType(balancingInvestment),
					totalPrice, 0d, memo);
		}
	}
}