package com.project.lakshmi.webapp.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

@SuppressWarnings("serial")
public abstract class Tag extends RequestContextAwareTag {
	
	private final Logger LOGGER = LogManager.getLogger(Tag.class);
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) pageContext.getRequest();
	}
	
	protected JspWriter getOut() {
		return pageContext.getOut();
	}
	
	protected void handleError(Exception exception) {
		LOGGER.error("Error in tag " +this.getClass() + " : " + exception.getMessage());
		exception.printStackTrace();
		throw new ApplicationContextException("error lors du tag ", exception);
	}
	
	protected void print(String html) {
		try {
			getOut().print(html);
		} catch (IOException exception) {
			handleError(exception);
		}
	}
	
	protected void includePage(String page) {
		
		try {
			String jspPage = "/tags/" + page;
			pageContext.include(jspPage);
		} catch (Exception exception) {
			print(exception.getMessage());
		}
	}
}
