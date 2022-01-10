package com.project.lakshmi.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.asset.AssetService;

/**
 * General action (header, menu,...)
 */
@RequestMapping("main")
@Controller
public class MainAction extends AbstractAction {
	
	@Autowired
	AssetService assetService;

	@GetMapping("main")
	public ModelAndView main() {
		return new ModelAndView("main");
	}
}
