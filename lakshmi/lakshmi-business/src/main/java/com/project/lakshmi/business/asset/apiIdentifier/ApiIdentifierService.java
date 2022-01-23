package com.project.lakshmi.business.asset.apiIdentifier;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.model.asset.Asset;

@Service
public interface ApiIdentifierService {

	/**
	 * @return une liste de tous les identifiants présents
	 */
	List<ApiIdentifier> getAllIdentifiers();

	/**
	 * @return l'asset associé à une pair du type btcusdt
	 */
	Asset getAsset(String pair);

	/**
	 * @return la currency associé à une pair du type btcusdt
	 * Renvoie null si l'asset n'existe pas en base
	 */
	Asset getCurrency(String pair);

	Asset getCurrency(String pair, Asset asset);

	/**
	 * @param identifiers (liste préenregistré des identifiants)
	 * @param isin
	 * @return tous les identifiants qui peuvent convenir à l'isin
	 */
	List<ApiIdentifier> findIdentifier(List<ApiIdentifier> apiIdentifiers, String isin);

	/**
	 * @param identifiers (liste préenregistré des identifiants)
	 * @param isin
	 * @param market
	 * @return tous les identifiants qui peuvent convenir à l'isin
	 */
	List<ApiIdentifier> findIdentifier(List<ApiIdentifier> apiIdentifiers, String isin, String market);
	
}

