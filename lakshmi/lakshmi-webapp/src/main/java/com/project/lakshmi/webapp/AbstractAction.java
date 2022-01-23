package com.project.lakshmi.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.lakshmi.technical.ApplicationException;

public abstract class AbstractAction {
	
	protected final String SESSION_ATTRRIBUTE_PAIRLIST = "pairlist";
	
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	
	protected String getUrl() {
		return getRequest().getRequestURI();
	}
	
	@ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handleException(ApplicationException exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	protected HttpSession getSession() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session = attr.getRequest().getSession(true);
		session.setMaxInactiveInterval(500*60);
	    return session; // true == allow create
	}
	
	protected String getForwardUrl(String url) {
		return "forward:" + url + ".do";
	}
	
	protected String getRedirectUrl(String url) {
		return "redirect:" + url + ".do";
	}
	
}
