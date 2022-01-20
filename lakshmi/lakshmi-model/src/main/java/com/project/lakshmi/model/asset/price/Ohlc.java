package com.project.lakshmi.model.asset.price;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.project.lakshmi.model.DatabaseName;
import com.project.lakshmi.model.asset.Asset;

@Entity(name = DatabaseName.OHLC.TABLE)
@Table(name = DatabaseName.OHLC.TABLE)
public class Ohlc {
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name=DatabaseName.OHLC.ID_ASSET, referencedColumnName = DatabaseName.ID)
	private Asset asset;
	
	@Column(name=DatabaseName.PRICE.DATE)
//	@Temporal(TemporalType.TIMESTAMP)
	private Instant date;	
	
	@Column(name=DatabaseName.OHLC.OPEN)
	private Double open;
	
	@Column(name=DatabaseName.OHLC.HIGH)
	private Double high;
	
	@Column(name=DatabaseName.OHLC.LOW)
	private Double low;
	
	@Column(name=DatabaseName.OHLC.CLOSE)
	private Double close;

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

}
