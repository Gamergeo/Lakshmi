package com.project.mscomptes.webapp;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.mscomptes.business.asset.AssetService;
import com.project.mscomptes.model.AssetType;

/**
 * General action (header, menu,...)
 */
@RequestMapping("main")
@Controller
public class MainAction extends AbstractAction {
	
	@Autowired
	AssetService assetService;

	@GetMapping("view")
	public ModelAndView view() {
		ModelAndView model = new ModelAndView("main");
		
		model.addObject("cryptoList", assetService.findAll(AssetType.CRYPTO));
		model.addObject("stockList", assetService.findAll(AssetType.STOCK));
		model.addObject("notManagedList", assetService.findAllNotManaged());
		
		return model;
	}
	
	@GetMapping("generate")
	public @ResponseBody String generate() throws URISyntaxException, IOException {
		assetService.generateCsv();
		return "ok";
	}	
	
}
