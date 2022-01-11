package com.project.lakshmi.webapp.checklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.checklist.ChecklistService;
import com.project.lakshmi.webapp.AbstractAction;

@RequestMapping("checklist")
@Controller
public class ChecklistAction extends AbstractAction {
	
	@Autowired
	private ChecklistService checklistService; 
	
	@PostMapping("view")
	public ModelAndView view() {
		
		checklistService.createChecklist();
		
		ModelAndView model = new ModelAndView("checklist/checklistView");
		return model;
	}
	
}
