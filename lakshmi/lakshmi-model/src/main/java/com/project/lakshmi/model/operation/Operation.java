package com.project.lakshmi.model.operation;

import java.io.Serializable;
import java.util.Date;

public interface Operation extends Serializable {
	
	public Date getDate();

	public void setDate(Date date);

	public Double getAmount();

	public void setAmount(Double amount);

	public OperationType getOperationType();

}
