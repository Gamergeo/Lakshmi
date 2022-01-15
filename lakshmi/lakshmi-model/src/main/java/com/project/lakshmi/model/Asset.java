package com.project.lakshmi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "asset")
@Table(name = "asset")
public class Asset implements Serializable {

	private static final long serialVersionUID = -1864542003746307771L;

	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;
	
	@Column
	private String name;
	
	@Column
	private String isin;

	@Column
	@Enumerated(EnumType.STRING)
	private AssetType type;
	
	@ManyToOne
	private Asset dependence;
	
	// Yahoo finance
	@Column
	private String symbol;
	
	@Column
	private String market;
	
	@Column
	private String link;
	
	@Column
	private boolean managed;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AssetType getType() {
		return type;
	}

	public void setType(AssetType type) {
		this.type = type;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public Asset getDependence() {
		return dependence;
	}

	public void setDependence(Asset dependence) {
		this.dependence = dependence;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isManaged() {
		return managed;
	}

	public void setManaged(boolean managed) {
		this.managed = managed;
	}
	
	/**
	 * TODO : pour l'instant, deux assets sont egales si elles ont le même nom
	 */
	@Override
	public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (object.getClass() != this.getClass()) {
            return false;
        }

        final Asset asset = (Asset) object;
        if ((this.getName() == null) ? (asset.getName() != null) : !this.getName().equals(asset.getName())) {
            return false;
        }

        return true;
	}
}
