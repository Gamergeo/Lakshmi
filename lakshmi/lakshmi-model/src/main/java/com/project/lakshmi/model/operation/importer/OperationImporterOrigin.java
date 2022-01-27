package com.project.lakshmi.model.operation.importer;

public enum OperationImporterOrigin {
	
	BINANCE("Binance"),KUCOIN("Kucoin");
	
	private OperationImporterOrigin(String fileName) {
		this.fileName = fileName;
	}

	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
