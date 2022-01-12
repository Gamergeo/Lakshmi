package com.project.lakshmi.webapp.response.json;

import java.io.Serializable;

public class ModelObjectResponse  implements JsonResponse, Serializable {

	private static final long serialVersionUID = -8226109725568105560L;
	
	private Integer id;
	
	public ModelObjectResponse(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

}
