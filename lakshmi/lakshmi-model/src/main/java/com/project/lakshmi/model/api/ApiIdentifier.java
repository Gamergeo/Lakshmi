package com.project.lakshmi.model.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.project.lakshmi.model.DatabaseName;
import com.project.lakshmi.model.asset.Asset;

@Entity(name = DatabaseName.API_IDENTIFIER.TABLE)
@Table(name = DatabaseName.API_IDENTIFIER.TABLE)
public class ApiIdentifier {
	
	// Same as asset id
	@Id
	@Column
	protected Integer id;
	
	@MapsId 
	@OneToOne(mappedBy = "apiIdentifier")
	@JoinColumn(name = DatabaseName.ID)   //same name as id @Column
	private Asset asset;
	
	@Column(name=DatabaseName.API_IDENTIFIER.API)
	@Enumerated(EnumType.STRING)
	private Api api;
	
	@Column(name=DatabaseName.API_IDENTIFIER.MARKET)
	private String market;

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

	public Api getApi() {
		return api;
	}

	public void setApi(Api api) {
		this.api = api;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}
	
}
