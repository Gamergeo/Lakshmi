package com.project.lakshmi.webapp;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.model.AssetType;
import com.project.lakshmi.business.asset.AssetService;

/**
 * General action (header, menu,...)
 */
@RequestMapping("priceTracker")
@Controller
public class PriceTrackerAction extends AbstractAction {
	
	@Autowired
	AssetService assetService;

	@GetMapping("view")
	public ModelAndView view() {
		ModelAndView model = new ModelAndView("priceTracker/priceTracker");
		
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