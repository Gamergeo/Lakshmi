package com.project.lakshmi.model.operation.investment;

import java.util.ArrayList;
import java.util.List;

import com.project.lakshmi.model.operation.OperationType;
import com.project.lakshmi.technical.ApplicationException;

public class OperationInvestmentMultiTrade extends OperationInvestment {
	
	private static final long serialVersionUID = 1695502602883468400L;

	// Représente les contre parties
	private List<Investment> balancingInvestments = new ArrayList<Investment>();
	
	@Override
	public OperationType getOperationType() {
		return OperationType.INVESTMENT;
	}
	
	@Override
	public InvestmentType getInvestmentType() {
		return InvestmentType.MULTI_TRADE;
	}

	public void setInvestmentType(InvestmentType investmentType) {
		throw new ApplicationException("Impossible de changer le type d'investissement pour un trade");
	}
	
	public List<Investment> getBalancingInvestments() {
		return balancingInvestments;
	}

	public void addBalancingInvestment(Investment balancingInvestment) {
		this.balancingInvestments.add(balancingInvestment);
	}
}
