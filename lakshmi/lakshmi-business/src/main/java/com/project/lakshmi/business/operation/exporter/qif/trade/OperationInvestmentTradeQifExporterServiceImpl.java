package com.project.lakshmi.business.operation.exporter.qif.trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.exporter.qif.QifExporterConstants;
import com.project.lakshmi.business.operation.exporter.qif.OperationQifExporterServiceImpl;
import com.project.lakshmi.business.operation.investment.InvestmentService;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;
import com.project.lakshmi.technical.NumberUtil;

@Service("operationInvestmentTradeExporterService")
public class OperationInvestmentTradeQifExporterServiceImpl extends OperationQifExporterServiceImpl implements OperationInvestmentTradeQifExporterService {
	
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
	public void writeOperation(OperationInvestmentTrade trade) {
		
		// On r�cup�re le montant total de l'op�ration
		Double totalPrice = investmentService.getTotal(trade.getInvestment(), trade.getDate());
		Double feePrice = 0d;
		
		if (trade.getFeeInvestment() != null) {
			feePrice = investmentService.getTotal(trade.getFeeInvestment(), trade.getDate());
		}
		
		// Dans le cas d'une vente, les frais sont n�gatif (particularit� qif)
//		if (trade.getInvestment().getQuantity() <= 0) {
//			feePrice = -feePrice;
//		}
		
		// Op�ration principale
		String memo = trade.getInvestment().getQuantity() >= 0 ?  "Achat " : "Vente ";
		memo += NumberUtil.toExactString(trade.getInvestment().getQuantity()) + " ";
		memo += trade.getInvestment().getAsset().getIsin();
		write(trade.getDate(), trade.getInvestment(), getTradeType(trade.getInvestment()), 
				totalPrice, feePrice, memo);
		
		// Op�ration d'equilibrage (attention, le prix total retranch� des fees)
		memo = "Equilibrage ";
		memo += NumberUtil.toExactString(trade.getBalancingInvestment().getQuantity()) + " "; 
		memo += trade.getBalancingInvestment().getAsset().getIsin();
		
		// Le total price de l'equilibrage d�pends du fait que ca soit une vente ou non
		Double totalBalancingPrice;
		if (trade.getInvestment().getQuantity() >= 0) {
			// Dans le cas d'un achat, l'�quilibrage est le total - fee
			totalBalancingPrice = totalPrice - feePrice;
		} else {
			totalBalancingPrice = totalPrice + feePrice;
		}
		
		write(trade.getDate(), trade.getBalancingInvestment(), getTradeType(trade.getBalancingInvestment()),
				totalBalancingPrice, 0d, memo);
		
		// Fee
		if (trade.getFeeInvestment() != null) {
			memo = "Frais " + trade.getFeeInvestment().getAsset().getIsin();
			write(trade.getDate(), trade.getFeeInvestment(), QifExporterConstants.TYPE_SELL,
					feePrice, 0d, memo);
		}
	}

	/**
	 * @return le type d'investissement dans le cas d'un TRADE uniquement
	 */
	protected String getTradeType(Investment invesment) {
		// Dans le cas d'un trade, on ecrit sell ou buy suivant la quantit�
		if (invesment.getQuantity() >= 0) {
			return QifExporterConstants.TYPE_BUY;
		} else {
			return QifExporterConstants.TYPE_SELL;
		}
	}
}
