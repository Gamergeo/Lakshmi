package com.project.lakshmi.model.operation;

import java.util.Date;

public class OperationInvestment implements Operation {
	
	private static final long serialVersionUID = 1695502602883468400L;

	private Date date;
	
	private Double amount;
	
	private boolean cleared;
	
	private Double itemAmount;
	
	private InvestmentItem item; 
	
	private Double fee; 
	
	private String memo;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public boolean isCleared() {
		return cleared;
	}

	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}

	public Double getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}

	public InvestmentItem getItem() {
		return item;
	}

	public void setItem(InvestmentItem item) {
		this.item = item;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Double getItemPrice() {
		return (getAmount() - getFee()) / getItemAmount();
	}

	@Override
	public OperationType getOperationType() {
		return OperationType.INVESTMENT;
	}
}
