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
import javax.persistence.Transient;

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
	
	@OneToOne
	@JoinColumn(name = DatabaseName.API_IDENTIFIER.ID_ASSET_CURRENCY, referencedColumnName = DatabaseName.ID)
	private Asset currency;
	
	@Column(name=DatabaseName.API_IDENTIFIER.API)
	@Enumerated(EnumType.STRING)
	private Api api;
	
	@Column(name=DatabaseName.API_IDENTIFIER.MARKET)
	private String market;
	
	@Column(name=DatabaseName.API_IDENTIFIER.SYMBOL)
	private String symbol;
	
	// Utilis� pour retrouver tous les identifiants
	@Transient
	private String rawSymbol;
	
	public ApiIdentifier() {}
	
	public ApiIdentifier(Asset asset, Asset currency, String market) {
		super();
		this.asset = asset;
		this.currency = currency;
		this.market = market;
		this.api = Api.CRYPTOWATCH;
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

	public Asset getCurrency() {
		return currency;
	}

	public void setCurrency(Asset currency) {
		this.currency = currency;
	}
	
	public String getPair() {
		return (asset.getIsin() + currency.getIsin()).toLowerCase();
	}

	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	// Correspond � l'identifiant de l'api
	public String getApiSymbol() {
		
		// Dans le cas de crypto watch c'est affich� "Market - isinassetisincurrency"
		if (Api.CRYPTOWATCH.equals(getApi())) {
			String market = getMarket();
			String symbol = getAsset().getIsin().toLowerCase() + getCurrency().getIsin().toLowerCase(); 
			
			return market + " - " + symbol;
		}
		
		// Dans le cas de kucoin c'est affich� "ISINASSET- ISINCURRENCY"
		if (Api.KUCOIN.equals(getApi())) {
			return getAsset().getIsin().toUpperCase() + "-" + getCurrency().getIsin().toUpperCase(); 
		} 
		
		return getSymbol();
	}

	public String getRawSymbol() {
		return rawSymbol;
	}

	public void setRawSymbol(String rawSymbol) {
		this.rawSymbol = rawSymbol;
	}
	
}
