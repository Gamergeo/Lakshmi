package com.project.lakshmi.model.config;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.project.lakshmi.model.DatabaseName;

@Entity(name = DatabaseName.CONFIG.TABLE)
@Table(name = DatabaseName.CONFIG.TABLE)
public class Config implements Serializable {

	private static final long serialVersionUID = -1864542003746307771L;

	@Id
	@Column(name=DatabaseName.ID)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;
	
	@Column(name=DatabaseName.CONFIG.YAHOO_KEY)
	private String yahooKey;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYahooKey() {
		return yahooKey;
	}

	public void setYahooKey(String yahooKey) {
		this.yahooKey = yahooKey;
	}
}
