package com.project.lakshmi.webapp.apiIdentifier;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.model.api.Market;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.webapp.AbstractAction;
import com.project.lakshmi.webapp.response.json.ModelObjectResponse;

@RequestMapping("apiIdentifier")
@Controller
public class ApiIdentifierAction extends AbstractAction {
	
	private final String SESSION_ATTRRIBUTE_PAIRLIST = "pairlist";
	
	@Autowired
	AssetService assetService;
	
	@GetMapping("getMarkets")
	public @ResponseBody List<Market> getMarkets(@RequestParam String isin) {
		
		// On récupère la liste en session
		String pairList = (String) getSession().getAttribute(SESSION_ATTRRIBUTE_PAIRLIST);
		
		// Elle n'est pas initialisé
		if (StringUtils.isEmpty(pairList)) {
			
		}
		
		List<Market> markets = new ArrayList<Market>();
		markets.add(Market.BINANCE);
		markets.add(Market.KRAKEN);
		
		return markets;
	}
	
	@GetMapping("getCurrencies")
	public @ResponseBody List<ModelObjectResponse> getCurrency(@RequestParam String isin, @RequestParam String market) {
		
		List<ModelObjectResponse> result = new ArrayList<ModelObjectResponse>();
		
		for (Asset asset : assetService.findAll()) {
			ModelObjectResponse model = new ModelObjectResponse(asset.getId(), asset.getIsin());
			result.add(model);
		}
		
		return result;
	}
}
