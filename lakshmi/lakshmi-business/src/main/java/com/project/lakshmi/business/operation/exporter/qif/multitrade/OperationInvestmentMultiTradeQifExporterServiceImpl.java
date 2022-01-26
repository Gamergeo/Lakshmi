package com.project.lakshmi.business.operation.exporter.qif.multitrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.exporter.qif.trade.OperationInvestmentTradeQifExporterServiceImpl;
import com.project.lakshmi.business.operation.investment.InvestmentService;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentMultiTrade;
import com.project.lakshmi.technical.NumberUtil;

@Service("operationInvestmentMultiTradeExporterService")
public class OperationInvestmentMultiTradeQifExporterServiceImpl extends OperationInvestmentTradeQifExporterServiceImpl implements OperationInvestmentMultiTradeQifExporterService {
	
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
		String memo = "Conversion " + NumberUtil.toExactString(multiTrade.getInvestment().getQuantity()) + " BNB";
		write(multiTrade.getDate(), multiTrade.getInvestment(), getTradeType(multiTrade.getInvestment()), 
				totalPrice, 0d, memo);
		
		// Op�rations d'equilibrage
		for (Investment balancingInvestment : multiTrade.getBalancingInvestments()) {
			memo = "Conversion de ";
			memo += NumberUtil.toExactString(balancingInvestment.getQuantity()) + " "; 
			memo += balancingInvestment.getAsset().getIsin() + " en BNB";

			// On r�cup�re le prix de la transaction balancing
			totalPrice = investmentService.getTotal(balancingInvestment, multiTrade.getDate());
			
			write(multiTrade.getDate(), balancingInvestment, getTradeType(balancingInvestment),
					totalPrice, 0d, memo);
		}
	}
}