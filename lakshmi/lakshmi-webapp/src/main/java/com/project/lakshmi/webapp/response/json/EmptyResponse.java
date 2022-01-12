package com.project.lakshmi.webapp.response.json;

import java.io.Serializable;

public class EmptyResponse implements JsonResponse, Serializable {

	private static final long serialVersionUID = -8226109725568105560L;
	
	private boolean empty = true;

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
}
