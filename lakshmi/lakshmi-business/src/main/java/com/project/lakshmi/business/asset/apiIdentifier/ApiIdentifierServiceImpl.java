package com.project.lakshmi.business.asset.apiIdentifier;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.business.api.cryptowatch.CryptoWatchApiService;
import com.project.lakshmi.business.api.kucoin.KucoinApiService;
import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.persistance.IDao;
import com.project.lakshmi.persistance.asset.apiidentifier.ApiIdentifierDao;

@Service("apiIdentifierService")
public class ApiIdentifierServiceImpl extends AbstractDatabaseService<ApiIdentifier> implements ApiIdentifierService {
	
	@Autowired
	CryptoWatchApiService cryptoWatchApiService;
	
	@Autowired
	KucoinApiService kucoinApiService;
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	ApiIdentifierDao apiIdentifierDao;

	@Override
	public IDao<ApiIdentifier> getDao() {
		return apiIdentifierDao;
	}
	
	/**
	 * @param identifiers (liste préenregistré des identifiants)
	 * @param isin
	 * @return tous les identifiants qui peuvent convenir à l'isin et à l'api
	 */
	@Override
	public List<ApiIdentifier> getMatchingIdentifier(Api api, String isin) {
		
		List<ApiIdentifier> results = new ArrayList<ApiIdentifier>();
		
		for (ApiIdentifier apiIdentifier : getAllIdentifier(api)) {
			
			String pair = apiIdentifier.getRawSymbol();
			
			// la raw pair start avec l'isin, on est ok
			if (pair.toUpperCase().startsWith(isin.toUpperCase())) {
				results.add(apiIdentifier);
			}
		}
		
		return results;
	}
	
	/**
	 * @param identifiers (liste préenregistré des identifiants)
	 * @param isin
	 * @param market
	 * @return tous les identifiants qui peuvent convenir à l'isin
	 */
	@Override
	public List<ApiIdentifier> getMatchingIdentifier(Api api, String isin, String market) {
		
		List<ApiIdentifier> matchingIdentifiers = getMatchingIdentifier(api, isin);
		
		// Market n'est pas spécifié, on renvoie sans le prendre en compte
		if (StringUtils.isEmpty(market)) {
			return matchingIdentifiers;
		}
		
		List<ApiIdentifier> results = new ArrayList<ApiIdentifier>();
		
		// On verifie que les marchés correspondent
		for (ApiIdentifier apiIdentifier : matchingIdentifiers) {
			
			if (apiIdentifier.getMarket().equals(market)) {
				results.add(apiIdentifier);
			}
		}
		
		return results;
	}
	
	@Override
	public ApiIdentifier getMatchingIdentifier(Api api, String isin, String currencyIsin, String market) {

		List<ApiIdentifier> matchingIdentifiers = getMatchingIdentifier(api, isin, market);
		
		// On verifie que les currency correspondent
		for (ApiIdentifier matchingIdentifier : matchingIdentifiers) {
			
			String matchingCurrencyIsin = getCurrencyIsin(isin, matchingIdentifier);
			
			if (matchingCurrencyIsin.equals(currencyIsin.toUpperCase())) {
				return matchingIdentifier;
			}
		}
		
		return null;
	}
	
	/**
	 * @return une liste de tous les identifiants présents pour l'api
	 */
	private List<ApiIdentifier> getAllIdentifier(Api api) {
		
		if (Api.KUCOIN.equals(api)) {
			return kucoinApiService.getAllIdentifiers();
		} else if (Api.CRYPTOWATCH.equals(api)) {
			return cryptoWatchApiService.getAllIdentifiers();
		} else { // Vide
			return new ArrayList<ApiIdentifier>();
		}
	}
	
	/**
	 * @return l'isin de la currency
	 * Pour cela on prends le raw, et on supprime l'isin (plus d'eventuels caractères spéciaiux)
	 */
	@Override
	public String getCurrencyIsin(String assetIsin, ApiIdentifier identifier) {
		
		String rawSymbol = identifier.getRawSymbol().toUpperCase().replaceFirst(assetIsin.toUpperCase(), "");
		
		// Dans le cas Kucoin, il y a aussi des caractères spéciaux
		if (Api.KUCOIN.equals(identifier.getApi())) {
			rawSymbol = rawSymbol.replace("-", "");
		}

		// Isin toujours en upper case
		return rawSymbol.toUpperCase();
	}
}