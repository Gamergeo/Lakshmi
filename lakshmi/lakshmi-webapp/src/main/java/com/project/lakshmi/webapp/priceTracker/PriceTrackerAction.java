package com.project.lakshmi.webapp.priceTracker;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.api.ApiService;
import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.business.ohlc.exporter.OhlcExporterService;
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.technical.FileUtils;
import com.project.lakshmi.webapp.AbstractAction;
import com.project.lakshmi.webapp.response.json.JsonResponse;
import com.project.lakshmi.webapp.response.json.StringResponse;

@RequestMapping("priceTracker")
@Controller
public class PriceTrackerAction extends AbstractAction {
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	OhlcExporterService ohlcExporterService;

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
	public @ResponseBody JsonResponse generate() throws URISyntaxException, IOException {
		List<Ohlc> ohlc = assetService.getAllHistoricalData();
		
		String fileName = ohlcExporterService.exportOhlc(ohlc);
		
		return new StringResponse("Fichier disponible à " + FileUtils.getUri(fileName));
	}
}
