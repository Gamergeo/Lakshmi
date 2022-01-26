package com.project.lakshmi.model.operation.importer;

public enum OperationImporterOrigin {
	
	BINANCE,KUCOIN;
	
	public String getLabel() {
		String prefix = name().substring(0,1);
		String suffix = name().substring(1);
		
		return prefix + suffix.toLowerCase();
	}
}
