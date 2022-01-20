package com.project.lakshmi.model.operation;

import java.time.Instant;

public class OperationCurrent extends Operation {
	
	private static final long serialVersionUID = 1695502602883468400L;

	private Instant date;
	
	private Double amount;
	
	private boolean cleared;
	
	private String payee;
	
	private String memo;
	
	private String category;
	
	private String paymentMethod;

	public OperationType getOperationType() {
		return OperationType.CURRENT;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
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

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
