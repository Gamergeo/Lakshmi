package com.project.lakshmi.model.operation.investment;

import com.project.lakshmi.model.Asset;
import com.project.lakshmi.technical.ApplicationException;

public class Investment {
	
	private Asset asset;
	
	private Double quantity;
	
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
						+ "deux asset différents : " + asset.getName() + " / " + addedAsset.getName());
			} else { // On ajoute les quantités
				setQuantity(getQuantity() + addedInvestment.getQuantity()); 
			}
		}
	}
}
