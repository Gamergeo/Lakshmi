package com.project.lakshmi.webapp.apiIdentifier;

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
	public @ResponseBody Set<String> getMarkets(@RequestParam Api api, @RequestParam String isin) {
		
		// On cherche les identifiants qui correspondent au bon api et isin
		List<ApiIdentifier> correspondingIdentifiers = apiIdentifierService.getMatchingIdentifier(api, isin);
		
		Set<String> markets = new HashSet<String>();
		
		for (ApiIdentifier apiIdentifier : correspondingIdentifiers) {
			markets.add(apiIdentifier.getMarket());
		}
			
		return markets;
	}
	
	@GetMapping("getCurrencies")
	public @ResponseBody Set<String> getCurrency(@RequestParam Api api, 
																@RequestParam String isin, 
																@RequestParam(required=false) String market) {
		
		// On cherche les identifiants qui correspondent à ceux de cryptowatch
		List<ApiIdentifier> correspondingIdentifiers = apiIdentifierService.getMatchingIdentifier(api, isin, market);
		
		Set<String> currencies = new HashSet<String>();
		
		for (ApiIdentifier apiIdentifier : correspondingIdentifiers) {
			currencies.add(apiIdentifierService.getCurrencyIsin(isin, apiIdentifier));
		}
			
		return currencies;
	}
	
	@GetMapping("isIdentifierAvailable")
	public @ResponseBody JsonResponse isIdentifierAvailable(Integer id) {
		
		ApiIdentifier apiIdentifier = apiIdentifierService.findById(id);
		
		// On est pas dans le cas Kucoin cryptowatch, on renvoie toujours true
		if (!Api.CRYPTOWATCH.equals(apiIdentifier.getApi()) && !Api.KUCOIN.equals(apiIdentifier.getApi())) {
			return new StringResponse("true");
		}
		
		// On cherche les identifiants qui correspondent à ceux de l'asset
		ApiIdentifier correspondingIdentifier = apiIdentifierService.getMatchingIdentifier(apiIdentifier.getApi(), 
				apiIdentifier.getAsset().getIsin(), apiIdentifier.getCurrency().getIsin(), apiIdentifier.getMarket());
		
		// Si un identifiant à été trouvé, on est bon
		if (correspondingIdentifier != null) {
			return new StringResponse("true");
		}
		
		return new StringResponse("false");
	}
	
}
