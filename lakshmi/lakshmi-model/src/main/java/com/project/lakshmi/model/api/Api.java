package com.project.lakshmi.model.api;

import java.util.ArrayList;
import java.util.List;

public enum Api {

	YAHOO, CRYPTOWATCH, NONE;
	
	public String getCode() {
		return name();
	}
	
	public static List<Api> getManagedApi() {
		List<Api> apis = new ArrayList<Api>();
		apis.add(CRYPTOWATCH);
		apis.add(YAHOO);
		return apis;
	}
}
