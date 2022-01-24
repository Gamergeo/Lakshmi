package com.project.lakshmi.model.asset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.project.lakshmi.model.DatabaseName;
import com.project.lakshmi.model.api.ApiIdentifier;

@Entity(name = DatabaseName.ASSET.TABLE)
@Table(name = DatabaseName.ASSET.TABLE)
public class Asset implements Serializable {

	private static final long serialVersionUID = -1864542003746307771L;

	@Id
	@Column(name=DatabaseName.ID)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;

	@Column(name=DatabaseName.ASSET.ISIN)
	private String isin; // Pour les cryptos, correspond au cod

	@Column(name=DatabaseName.ASSET.LABEL)
	private String label;
	
	@Column(name=DatabaseName.ASSET.LINK)
	private String link;

    @OneToOne
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

	public ApiIdentifier getApiIdentifier() {
		return apiIdentifier;
	}

	public void setApiIdentifier(ApiIdentifier apiIdentifier) {
		this.apiIdentifier = apiIdentifier;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Deux assets sont egales si elles ont le même ISIN
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
		return "EUR".equals(getIsin());
	}
}
