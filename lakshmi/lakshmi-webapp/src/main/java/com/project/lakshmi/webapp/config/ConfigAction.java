package com.project.lakshmi.webapp.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.config.ConfigService;
import com.project.lakshmi.model.config.Config;
import com.project.lakshmi.webapp.AbstractAction;

@RequestMapping("config")
@Controller
public class ConfigAction extends AbstractAction {

	@Autowired
	private ConfigService configService;
	
	@PostMapping("view")
	public ModelAndView view() {
		ModelAndView model = new ModelAndView("config/configView");
		model.addObject("config", configService.find());
		return model;
	}
	
	@PostMapping("save")
	public ModelAndView save(Config config) throws IOException {
		
		configService.saveOrUpdate(config);
		ModelAndView model = new ModelAndView("config/configView");
		return model;
	}
}
