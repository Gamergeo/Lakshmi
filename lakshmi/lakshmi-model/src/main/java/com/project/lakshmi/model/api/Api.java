package com.project.lakshmi.model.api;

import java.util.Arrays;
import java.util.List;

public enum Api {

	YAHOO, CRYPTOWATCH, KUCOIN, NONE;
	
	public String getCode() {
		return name();
	}
	
	public static List<Api> getManagedApi() {
		List<Api> apis = Arrays.asList(values());
		apis.remove(Api.NONE);
		
		return apis;
	}
}
