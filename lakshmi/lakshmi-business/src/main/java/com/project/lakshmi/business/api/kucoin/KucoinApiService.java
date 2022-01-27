package com.project.lakshmi.business.api.kucoin;

import java.util.List;

import com.project.lakshmi.business.api.ApiService;
import com.project.lakshmi.model.api.ApiIdentifier;

public interface KucoinApiService extends ApiService {

	/**
	 * @return une liste de tous les identifiants présents
	 */
	List<ApiIdentifier> getAllIdentifiers();
}
