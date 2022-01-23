package com.project.lakshmi.webapp.response.json;

import java.io.Serializable;

public class ModelObjectResponse implements JsonResponse, Serializable {

	private static final long serialVersionUID = -8226109725568105560L;
	
	private Integer id;
	
	private String label;
	
	public ModelObjectResponse(Integer id) {
		super();
		this.id = id;
	}
	
	public ModelObjectResponse(Integer id, String label) {
		super();
		this.id = id;
		this.label = label;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
