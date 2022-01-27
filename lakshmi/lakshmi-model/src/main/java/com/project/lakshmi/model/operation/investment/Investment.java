package com.project.lakshmi.model.operation.investment;

import java.util.AbstractMap;
import java.util.Map.Entry;

import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.technical.ApplicationException;

public class Investment {
	
	private Asset asset;
	
	private Double quantity;
	
	// Certains assets sont irrecupérables en api
	// Cependantn Kucoin precise les prix
	// Donc dans certains cas, on utilise une coversion
	// Ex : ETH2 = 0.94 ETH
	private Entry<Asset, Double> specialConversion;
	
	public Investment(Asset asset, Double quantity) {
		super();
		this.asset = asset;
		this.quantity = quantity;
	}

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
	
	public void addInvestment(Investment addedInvestment) {
		
		if (addedInvestment == null) {
			return;
		} else {
			
			Asset asset = getAsset();
			Asset addedAsset = addedInvestment.getAsset();
			
			// Ce n'est pas les même asset : erreur
			if (!asset.equals(addedAsset)) {
				throw new ApplicationException("Impossible d'ajouter des investissements sur "
						+ "deux asset différents : " + asset.getIsin() + " / " + addedAsset.getIsin());
			} else { // On ajoute les quantités
				setQuantity(getQuantity() + addedInvestment.getQuantity()); 
			}
		}
	}
	
	public boolean isSpecialConversion() {
		return specialConversion != null;
	}

	public Double getSpecialConversionRate() {
		return specialConversion.getValue();
	}
	
	public Asset getSpecialConversionCurrency() {
		return specialConversion.getKey();
	}

	public void setSpecialConversion(Asset currency, Double rate) {
		this.specialConversion = new AbstractMap.SimpleEntry<Asset, Double>(currency, rate);
	}
}
