package com.project.lakshmi.model.operation.investment;

import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.OperationType;

public class OperationInvestment extends Operation {
	
	private static final long serialVersionUID = 1695502602883468400L;

	private Investment investment;
	
	private InvestmentType investmentType;
	
	@Override
	public OperationType getOperationType() {
		return OperationType.INVESTMENT;
	}

	public Investment getInvestment() {
		return investment;
	}

	public void setInvestment(Investment investment) {
		this.investment = investment;
	}

	public InvestmentType getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(InvestmentType investmentType) {
		this.investmentType = investmentType;
	}
	
	@Override
	public OperationInvestment asOperationInvestment() {
		return this;
	}
}
