package com.project.lakshmi.business.asset.apiIdentifier;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.DatabaseService;
import com.project.lakshmi.model.api.ApiIdentifier;

@Service
public interface ApiIdentifierService extends DatabaseService<ApiIdentifier>{

	/**
	 * @return une liste de tous les identifiants présents
	 */
	List<ApiIdentifier> getAllIdentifiers();

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

	/**
	 * @return an api identifier correclty set with asset and currency
	 */
	ApiIdentifier createIdentifier(String pair, String market);
	
}

