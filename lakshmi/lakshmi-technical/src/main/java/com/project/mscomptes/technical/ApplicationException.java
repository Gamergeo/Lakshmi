package com.project.mscomptes.technical;

public class ApplicationException extends RuntimeException {
	
	private static final long serialVersionUID = 7159791881893837879L;
	
	public ApplicationException(String errorMessage) {
	    super(errorMessage);
	}

	public ApplicationException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
