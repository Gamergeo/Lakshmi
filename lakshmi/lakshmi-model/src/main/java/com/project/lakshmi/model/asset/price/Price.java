package com.project.lakshmi.model.asset.price;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.project.lakshmi.model.DatabaseName;
import com.project.lakshmi.model.asset.Asset;

@Entity(name = DatabaseName.PRICE.TABLE)
@Table(name = DatabaseName.PRICE.TABLE)
public class Price {
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name=DatabaseName.PRICE.ID_ASSET, referencedColumnName = DatabaseName.ID)
	private Asset asset;
	
	@Column(name=DatabaseName.PRICE.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;	
	
	@Column(name=DatabaseName.PRICE.PRICE)
	private Double price;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
