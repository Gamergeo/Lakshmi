package com.project.lakshmi.business.api.cryptowatch;

import java.util.List;

import com.project.lakshmi.business.api.ApiService;
import com.project.lakshmi.model.api.ApiIdentifier;

public interface CryptoWatchApiService extends ApiService {

	List<ApiIdentifier> getAllIdentifiers();

}
