package com.project.lakshmi.model.api;

public enum Api {

	YAHOO, CRYPTOWATCH, NONE;
	
	public String getCode() {
		return name();
	}
}
