package com.project.lakshmi.model.api;

import java.io.Serializable;

public enum Market implements Serializable {

	BINANCE("binance"), KRAKEN("kraken");
	
	private String code;

	private Market(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
