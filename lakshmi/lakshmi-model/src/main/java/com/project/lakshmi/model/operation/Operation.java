package com.project.lakshmi.model.operation;

import java.io.Serializable;
import java.util.Date;

import com.project.lakshmi.model.operation.investment.OperationInvestment;
import com.project.lakshmi.technical.ApplicationException;

public abstract class Operation implements Serializable {

	private static final long serialVersionUID = 8617172259352125727L;
	
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public abstract OperationType getOperationType();
	
	public OperationInvestment asOperationInvestment() {
		throw new ApplicationException("Impossible to transform " + getOperationType() + " as investment");
	}
}
