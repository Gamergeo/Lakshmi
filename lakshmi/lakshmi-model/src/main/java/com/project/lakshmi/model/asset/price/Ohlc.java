package com.project.lakshmi.model.asset.price;

import java.time.Instant;

import com.project.lakshmi.model.asset.Asset;

public class Ohlc {
	
	private Integer id;
	
	private Asset asset;
	
	private Asset currency;
	
	private Instant date;	
	
	private Double open;
	
	private Double high;
	
	private Double low;
	
	private Double close;
	
	public Ohlc(Asset asset) {
		this.asset = asset;
		this.currency = asset.getApiIdentifier().getCurrency();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}
	
	public void setDate(long timestamp) {
		this.date = Instant.ofEpochMilli(timestamp);
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public Asset getCurrency() {
		return currency;
	}

	public void setCurrency(Asset currency) {
		this.currency = currency;
	}
	
	// Lorsque l'on récupère qu'une seule donnée, on init tout avec celle-ci
	public void setPrice(Double price) {
		setOpen(price);
		setLow(price);
		setHigh(price);
		setClose(price);
	}
	
}
