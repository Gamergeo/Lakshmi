package com.project.lakshmi.business.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.lakshmi.business.api.cryptowatch.CryptoWatchApiService;
import com.project.lakshmi.business.api.yahoo.YahooApiService;
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.technical.ApplicationException;

/**
 * Generic call to api
 */
@Service("apiService")
public class ApiServiceImpl implements ApiService {
	
	@Autowired
	CryptoWatchApiService cryptoWatchApiService;

	@Autowired
	YahooApiService yahooApiService;
	
	protected String call(String uri) {
		return call(uri, new ArrayList<NameValuePair>(), null, null);
	}
	
	protected String call(String uri, List<NameValuePair> parameters) {
		return call(uri, parameters, null, null);
	}
	
	protected String call(String uri, List<NameValuePair> parameters, String apiKeyHeaderName, String apiKey) {
		String response_content = "";
		URIBuilder query;
		CloseableHttpClient client;
		HttpGet request;
		
		try {
			query = new URIBuilder(uri);

			query.addParameters(parameters);

	    	client = HttpClients.createDefault();
	    	request = new HttpGet(query.build());
		} catch (URISyntaxException exception) {
			throw new ApplicationException(exception.getMessage());
		}

	    request.setHeader(HttpHeaders.ACCEPT, "application/json");
	    
	    if (!StringUtils.isEmpty(apiKeyHeaderName)) {
	    	request.addHeader(apiKeyHeaderName, apiKey);
	    }

	    CloseableHttpResponse response;
		try {
			response = client.execute(request);
		    try {
		    	System.out.println(response.getStatusLine() + uri + parameters.toString());
		    	HttpEntity entity = response.getEntity();
		    	response_content = EntityUtils.toString(entity);
		    	EntityUtils.consume(entity);
		    } finally {
		    	response.close();
		    }
		} catch (IOException exception) {
			throw new ApplicationException(exception.getMessage());
		}

	    return response_content;
	}
	
	@Override
	public Ohlc getPriceOhlc(Asset asset, Instant instant) {
		
		
		if (asset.isEuro()) { // ne devrait pas arriver
			throw new ApplicationException("On ne doit pas chercher le prix d'un euro");
			
		} else if (Api.CRYPTOWATCH.equals(asset.getApiIdentifier().getApi())) {
			return cryptoWatchApiService.getPriceOhlc(asset, instant);
		}
		
		throw new NotYetImplementedException("import api non implémenté");
	}
	
	@Override
	public List<Ohlc> getHistoricalOhlc(List<Asset> assets) {
		
		// On va trier les assets en plusieurs listes, suivant l'api concerné
		List<Asset> cryptoWatchAsset = new ArrayList<Asset>();
		List<Asset> yahooAsset = new ArrayList<Asset>();
		
		for (Asset asset : assets) {
			
			ApiIdentifier apiIdentifier = asset.getApiIdentifier();
			
			// L'euro n'a pas d'identifier
			if (apiIdentifier != null) {
				if (Api.CRYPTOWATCH.equals(apiIdentifier.getApi())) {
					cryptoWatchAsset.add(asset);
				} else if (Api.YAHOO.equals(apiIdentifier.getApi())) {
					yahooAsset.add(asset);
				} else {
					throw new NotYetImplementedException("Api non definie");
				}
			}
		}
		
		List<Ohlc> ohlc = new ArrayList<Ohlc>();
		
		ohlc.addAll(cryptoWatchApiService.getHistoricalOhlc(cryptoWatchAsset));
		ohlc.addAll(yahooApiService.getHistoricalOhlc(yahooAsset));
		
		return ohlc;
	}

}
