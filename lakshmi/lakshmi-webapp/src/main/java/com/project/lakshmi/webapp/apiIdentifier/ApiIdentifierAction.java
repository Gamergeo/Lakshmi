package com.project.lakshmi.webapp.apiIdentifier;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.business.asset.apiIdentifier.ApiIdentifierService;
import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.webapp.AbstractAction;
import com.project.lakshmi.webapp.response.json.ModelObjectResponse;

@RequestMapping("apiIdentifier")
@Controller
public class ApiIdentifierAction extends AbstractAction {
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	ApiIdentifierService apiIdentifierService;
	
	@GetMapping("getMarkets")
	public @ResponseBody List<String> getMarkets(@RequestParam String isin) {
		
		// On récupère la liste en session
		@SuppressWarnings("unchecked")
		List<ApiIdentifier> cryptowatchIdentifiers = (List<ApiIdentifier>) getSession().getAttribute(SESSION_ATTRRIBUTE_PAIRLIST);
		
		// On cherche les identifiants qui correspondent à ceux de cryptowatch
		List<ApiIdentifier> correspondingIdentifiers = apiIdentifierService.findIdentifier(cryptowatchIdentifiers, isin);
		
		List<String> markets = new ArrayList<String>();
		
		for (ApiIdentifier apiIdentifier : correspondingIdentifiers) {
			markets.add(apiIdentifier.getMarket());
		}
			
		return markets;
	}
	
	@GetMapping("getCurrencies")
	public @ResponseBody List<ModelObjectResponse> getCurrency(@RequestParam String isin, @RequestParam String market) {
		// On récupère la liste en session
		@SuppressWarnings("unchecked")
		List<ApiIdentifier> cryptowatchIdentifiers = (List<ApiIdentifier>) getSession().getAttribute(SESSION_ATTRRIBUTE_PAIRLIST);
		
		// On cherche les identifiants qui correspondent à ceux de cryptowatch
		List<ApiIdentifier> correspondingIdentifiers = apiIdentifierService.findIdentifier(cryptowatchIdentifiers, isin, market);
		
		List<ModelObjectResponse> response = new ArrayList<ModelObjectResponse>();
		
		// On convertit les asset en object json
		for (ApiIdentifier apiIdentifier : correspondingIdentifiers) {
			ModelObjectResponse model = new ModelObjectResponse(apiIdentifier.getCurrency().getId(), apiIdentifier.getCurrency().getIsin());
			response.add(model);
		}
		
		return response;
	}
}
