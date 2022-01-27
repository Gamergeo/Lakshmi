package com.project.lakshmi.model.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Api {

	YAHOO, CRYPTOWATCH, KUCOIN, NONE;
	
	public String getCode() {
		return name();
	}
	
	public static List<Api> getManagedApi() {
		List<Api> apis = new ArrayList<Api>();
		apis.add(Api.YAHOO);
		apis.add(Api.CRYPTOWATCH);
		apis.add(Api.KUCOIN);
		
		return apis;
	}
}
