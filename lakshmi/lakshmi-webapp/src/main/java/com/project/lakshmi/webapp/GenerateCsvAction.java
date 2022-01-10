package com.project.lakshmi.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * General action (header, menu,...)
 */
@RequestMapping("generateCsv")
@Controller
public class GenerateCsvAction extends AbstractAction {
	
	@GetMapping("view")
	public ModelAndView view() {
		ModelAndView model = new ModelAndView("generateCsv/generateCsvView");
		return model;
	}
	
}
