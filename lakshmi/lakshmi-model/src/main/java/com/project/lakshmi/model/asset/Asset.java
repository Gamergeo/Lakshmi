package com.project.lakshmi.model.asset;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.project.lakshmi.model.DatabaseName;
import com.project.lakshmi.model.LakshmiConstants;
import com.project.lakshmi.model.api.ApiIdentifier;

@Entity(name = DatabaseName.ASSET.TABLE)
@Table(name = DatabaseName.ASSET.TABLE)
public class Asset implements Serializable {

	private static final long serialVersionUID = -1864542003746307771L;

	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;

	@Column
	private String isin; // Pour les cryptos, correspond au cod

	@Column
	private String label;

	@Enumerated(EnumType.STRING)
	private AssetType type;
	
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ApiIdentifier apiIdentifier;
    
//	@OneToMany(mappedBy="checklist", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
//	private List<Price> price = new ArrayList<Price>();
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}
	
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

	public ApiIdentifier getApiIdentifier() {
		return apiIdentifier;
	}

	public void setApiIdentifier(ApiIdentifier apiIdentifier) {
		this.apiIdentifier = apiIdentifier;
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
        if ((this.getIsin() == null) ? (asset.getIsin() != null) : !this.getIsin().equals(asset.getIsin())) {
            return false;
        }

        return true;
	}
	
	public boolean isEuro() {
		return LakshmiConstants.ASSET_EURO_ISIN.equals(getIsin());
	}
}
