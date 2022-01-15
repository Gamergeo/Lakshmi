package com.project.lakshmi.model.operation.investment;

public enum InvestmentType {
	
	TRADE, // Exchange between two Investment
	MULTI_TRADE, // Use for Binance small bnb conversion
	MINING, // Revenu de minage
	STACKING, // Revenu de stacking
	WITHDRAW, // Retrait
	DEPOSIT // Depot
	;
}
