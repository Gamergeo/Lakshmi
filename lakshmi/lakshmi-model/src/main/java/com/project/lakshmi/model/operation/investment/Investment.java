package com.project.lakshmi.model.operation.investment;

import com.project.lakshmi.model.Asset;

public class Investment {
	
	private Asset asset;
	
	private Double quantity;

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
}
