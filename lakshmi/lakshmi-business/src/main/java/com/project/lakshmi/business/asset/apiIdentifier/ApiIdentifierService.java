package com.project.lakshmi.business.asset.apiIdentifier;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.DatabaseService;
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.api.ApiIdentifier;

@Service
public interface ApiIdentifierService extends DatabaseService<ApiIdentifier>{

	/**
	 * @return tous les identifiants qui peuvent convenir à l'isin et à l'api
	 */
	List<ApiIdentifier> getMatchingIdentifier(Api api, String isin);

	/**
	 * @return tous les identifiants qui peuvent convenir à l'isin et au market
	 */
	List<ApiIdentifier> getMatchingIdentifier(Api api, String isin, String market);

	ApiIdentifier getMatchingIdentifier(Api api, String isin, String currencyIsin, String market);

	/**
	 * @return l'isin de la currency
	 * Pour cela on prends le raw, et on supprime l'isin (plus d'eventuels caractères spéciaiux)
	 */
	String getCurrencyIsin(String assetIsin, ApiIdentifier identifier);

}

