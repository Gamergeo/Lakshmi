package com.project.lakshmi.model.operation.investment;

import com.project.lakshmi.model.operation.OperationType;
import com.project.lakshmi.technical.ApplicationException;

public class OperationInvestmentTrade extends OperationInvestment {
	
	private static final long serialVersionUID = 1695502602883468400L;

	// Représente la contre partie
	private Investment balancingInvestment;
	
	// Représente les fees, peut etre nul
	private Investment feeInvestment;
	
	@Override
	public OperationType getOperationType() {
		return OperationType.INVESTMENT;
	}
	
	@Override
	public InvestmentType getInvestmentType() {
		return InvestmentType.TRADE;
	}

	public void setInvestmentType(InvestmentType investmentType) {
		throw new ApplicationException("Impossible de changer le type d'investissement pour un trade");
	}

	public Investment getBalancingInvestment() {
		return balancingInvestment;
	}

	public void setBalancingInvestment(Investment balancingInvestment) {
		this.balancingInvestment = balancingInvestment;
	}

	public Investment getFeeInvestment() {
		return feeInvestment;
	}

	public void setFeeInvestment(Investment feeInvestment) {
		this.feeInvestment = feeInvestment;
	}
	
}
