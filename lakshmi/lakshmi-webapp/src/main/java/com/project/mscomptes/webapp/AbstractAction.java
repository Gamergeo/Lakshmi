package com.project.mscomptes.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class AbstractAction {
	
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	
	protected String getUrl() {
		return getRequest().getRequestURI();
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
