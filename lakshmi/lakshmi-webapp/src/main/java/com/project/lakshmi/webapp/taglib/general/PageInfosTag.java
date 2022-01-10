package com.project.lakshmi.webapp.taglib.general;

import javax.servlet.http.HttpServletRequest;

import com.project.lakshmi.webapp.taglib.Tag;

public class PageInfosTag extends Tag  {  
  
	private static final long serialVersionUID = -2182341279522447430L;

	private String title;
	
	@Override
	public void doFinally() {
		HttpServletRequest request = getRequest();
		
		request.setAttribute("title", getTitle());
		
		includePage("general/pageInfosTag.jsp");
	}
	
	@Override
	protected int doStartTagInternal() throws Exception {
		return SKIP_BODY;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}  