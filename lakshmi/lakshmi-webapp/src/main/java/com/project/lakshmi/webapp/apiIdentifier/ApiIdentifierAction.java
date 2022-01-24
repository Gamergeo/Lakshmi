package com.project.lakshmi.webapp.apiIdentifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.lakshmi.business.api.cryptowatch.CryptoWatchApiService;
import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.business.asset.apiIdentifier.ApiIdentifierService;
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.webapp.AbstractAction;
import com.project.lakshmi.webapp.response.json.JsonResponse;
import com.project.lakshmi.webapp.response.json.ModelObjectResponse;
import com.project.lakshmi.webapp.response.json.StringResponse;

@RequestMapping("apiIdentifier")
@Controller
public class ApiIdentifierAction extends AbstractAction {
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	ApiIdentifierService apiIdentifierService;
	
	@Autowired
	CryptoWatchApiService cryptoWatchApiService;
	
	@GetMapping("getMarkets")
	public @ResponseBody Set<String> getMarkets(@RequestParam String isin) {
		
		// On récupère la liste en session
		@SuppressWarnings("unchecked")
		List<ApiIdentifier> cryptowatchIdentifiers = (List<ApiIdentifier>) getSession().getAttribute(SESSION_ATTRRIBUTE_PAIRLIST);
		
		// On cherche les identifiants qui correspondent à ceux de cryptowatch
		List<ApiIdentifier> correspondingIdentifiers = apiIdentifierService.findIdentifier(cryptowatchIdentifiers, isin);
		
		Set<String> markets = new HashSet<String>();
		
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
	
	@GetMapping("isIdentifierAvailable")
	public @ResponseBody JsonResponse isIdentifierAvailable(Integer id) {
		ApiIdentifier apiIdentifier = apiIdentifierService.findById(id);
		
		if (!Api.CRYPTOWATCH.equals(apiIdentifier.getApi())) {
			return new StringResponse("true");
		}
		
		@SuppressWarnings("unchecked")
		List<ApiIdentifier> cryptowatchIdentifiers = (List<ApiIdentifier>) getSession().getAttribute(SESSION_ATTRRIBUTE_PAIRLIST);
		
		// On verifie si elle est dans notre liste
		for (ApiIdentifier cryptowatchIdentifier : cryptowatchIdentifiers) {
			
			if (cryptowatchIdentifier.getMarket().equals(apiIdentifier.getMarket()) &&
				cryptowatchIdentifier.getAsset().equals(apiIdentifier.getAsset()) &&
				cryptowatchIdentifier.getCurrency().equals(apiIdentifier.getCurrency())) {
				
				return new StringResponse("true");
			}
		}
		
		return new StringResponse("false");
	}
	
	@GetMapping("refreshAllIdentifiers")
	public @ResponseBody JsonResponse refreshAllIdentifiers() {
		getSession().setAttribute(SESSION_ATTRRIBUTE_PAIRLIST, cryptoWatchApiService.getAllIdentifiers());
		return new StringResponse("OK");
	}
}
