package com.project.lakshmi.business.operation.exporter.qif.financing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.exporter.qif.QifExporterConstants;
import com.project.lakshmi.business.operation.exporter.qif.OperationQifExporterServiceImpl;
import com.project.lakshmi.business.operation.investment.InvestmentService;
import com.project.lakshmi.model.operation.investment.InvestmentType;
import com.project.lakshmi.model.operation.investment.OperationInvestment;
import com.project.lakshmi.technical.NumberUtil;

@Service("operationInvestmentFinancingQifExporterService")
public class OperationInvestmentFinancingQifExporterServiceImpl extends OperationQifExporterServiceImpl implements OperationInvestmentFinancingQifExporterService {
	
	@Autowired
	InvestmentService investmentService;

	@Override
	public void writeOperation(OperationInvestment operation) {
		
		// Un withdraw correspond à une vente, un deposit à un achat
		
		// On récupère le montant total de l'opération
		Double totalPrice = investmentService.getTotal(operation.getInvestment(), operation.getDate());
		
		String memo;
		
		if (InvestmentType.WITHDRAW.equals(operation.getInvestmentType())) {
			memo = "Retrait ";
		} else {
			memo = "Depot ";
		}
		
		memo += NumberUtil.toExactString(operation.getInvestment().getQuantity()) + " ";
		memo += operation.getInvestment().getAsset().getIsin();
		
		write(operation.getDate(), operation.getInvestment(), getFinancingType(operation.getInvestmentType()), 
				totalPrice, 0d, memo);
		
	}

	/**
	 * @return le type d'investissement dans le cas d'un FINANCING uniquement
	 */
	protected String getFinancingType(InvestmentType invesmentType) {
		// Dans le cas d'un trade, on ecrit sell ou buy suivant la quantité
		if (InvestmentType.DEPOSIT.equals(invesmentType)) {
			return QifExporterConstants.TYPE_BUY;
		} else {
			return QifExporterConstants.TYPE_SELL;
		}
	}
}
