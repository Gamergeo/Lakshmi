package com.project.lakshmi.webapp.generateCsv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.webapp.AbstractAction;

@RequestMapping("generateCsv")
@Controller
public class GenerateCsvAction extends AbstractAction {
	
	@PostMapping("view")
	public ModelAndView view() {
		ModelAndView model = new ModelAndView("generateCsv/generateCsvView");
		return model;
	}
	
}
