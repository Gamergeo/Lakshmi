package com.project.lakshmi.webapp.response.json;

import java.io.Serializable;

public class StringResponse implements JsonResponse, Serializable {

	private static final long serialVersionUID = -394009301748337515L;
	
	private String message;

	public StringResponse() {
	}

	public StringResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
