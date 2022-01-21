package com.project.lakshmi.webapp.priceTracker;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.api.ApiService;
import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.webapp.AbstractAction;

@RequestMapping("priceTracker")
@Controller
public class PriceTrackerAction extends AbstractAction {
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	ApiService apiService;

	@PostMapping("view")
	public ModelAndView view() throws URISyntaxException, IOException {
		ModelAndView model = new ModelAndView("priceTracker/priceTrackerView");
		
//		List<Ohlc> ohlc = assetService.getAllHistoricalData();
		
		model.addObject("cryptoList", assetService.findAllManagedByApi(Api.CRYPTOWATCH));
		model.addObject("stockList", assetService.findAllManagedByApi(Api.YAHOO));
		model.addObject("notManagedList", assetService.findAllNotManaged());
		
		return model;
	}
	
	@GetMapping("generate")
	public @ResponseBody String generate() throws URISyntaxException, IOException {
//		assetService.generateCsv();
		return "ok";
	}	
	
}
