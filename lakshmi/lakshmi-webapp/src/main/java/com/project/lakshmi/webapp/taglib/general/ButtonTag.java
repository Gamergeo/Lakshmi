package com.project.lakshmi.webapp.taglib.general;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.project.lakshmi.webapp.taglib.Tag;

public class ButtonTag extends Tag  {  
  
	private static final long serialVersionUID = -2182341279522447430L;

	private String label;

	private String size;
	
	private String onclick;
	
	@Override
	public void doFinally() {
		HttpServletRequest request = getRequest();

		System.out.println(getLabel());
		request.setAttribute("label", getLabel());

		System.out.println(getRequest().getAttribute("label"));
		request.setAttribute("size", getSize());
		request.setAttribute("onclick", getOnclick());
		
		includePage("buttonTag.jsp");
	}
	
	@Override
	protected int doStartTagInternal() throws Exception {
		return SKIP_BODY;
	}

	public String getSize() {
		if (StringUtils.isEmpty(size)) {
			return "normal";
		}
		
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}  