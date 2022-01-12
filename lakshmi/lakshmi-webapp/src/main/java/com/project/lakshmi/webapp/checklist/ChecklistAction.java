package com.project.lakshmi.webapp.checklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.checklist.ChecklistService;
import com.project.lakshmi.business.checklist.ChecklistStepService;
import com.project.lakshmi.model.checklist.ChecklistStep;
import com.project.lakshmi.webapp.AbstractAction;
import com.project.lakshmi.webapp.response.json.EmptyResponse;
import com.project.lakshmi.webapp.response.json.JsonResponse;

@RequestMapping("checklist")
@Controller
public class ChecklistAction extends AbstractAction {
	
	@Autowired
	private ChecklistService checklistService; 
	
	@Autowired
	private ChecklistStepService checklistStepService; 
	
	@PostMapping("view")
	public ModelAndView view() {
		
		ModelAndView model = new ModelAndView("checklist/checklistView");
		model.addObject("checklist", checklistService.getLastChecklist());
		return model;
	}
	
	@PostMapping("save")
	public @ResponseBody JsonResponse save(ChecklistStep checklistStep) {
		checklistStepService.saveOrUpdate(checklistStep);
		
		return new EmptyResponse();  
	}
	
}
