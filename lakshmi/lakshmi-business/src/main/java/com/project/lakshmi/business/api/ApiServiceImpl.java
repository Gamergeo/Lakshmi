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

import com.project.lakshmi.business.api.cryptowatch.CryptoWatchApiService;
import com.project.lakshmi.business.api.kucoin.KucoinApiService;
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

	@Autowired
	KucoinApiService kucoinApiService;
	
	protected CloseableHttpResponse call(String uri) {
		return call(uri, new ArrayList<NameValuePair>(), new ArrayList<NameValuePair>());
	}
	
	protected CloseableHttpResponse call(String uri, List<NameValuePair> parameters) {
		return call(uri, new ArrayList<NameValuePair>(), parameters);
	}
	
	protected CloseableHttpResponse call(String uri, List<NameValuePair> headers, List<NameValuePair> parameters) {
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
	    
	    for (NameValuePair header : headers) {
	    	request.addHeader(header.getName(), header.getValue());
	    }

	    CloseableHttpResponse response;
		try {
			response = client.execute(request);
		} catch (IOException exception) {
			throw new ApplicationException(exception.getMessage());
		}
		
		System.out.println(response.getStatusLine() + " " + uri + " " + parameters.toString());

	    return response;
	}
	
	protected Integer getStatus(CloseableHttpResponse response) {
		return response.getStatusLine().getStatusCode();
	}
	
	protected String getContent(CloseableHttpResponse response) {
		String response_content = "";
		
		try {
			try {
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
		} else if (Api.KUCOIN.equals(asset.getApiIdentifier().getApi())) {
			return kucoinApiService.getPriceOhlc(asset, instant);
		}
		
		throw new NotYetImplementedException("import api non implémenté");
	}
	
	@Override
	public List<Ohlc> getHistoricalOhlc(List<Asset> assets) {
		
		// On va trier les assets en plusieurs listes, suivant l'api concerné
		List<Asset> cryptoWatchAsset = new ArrayList<Asset>();
		List<Asset> yahooAsset = new ArrayList<Asset>();
		List<Asset> kucoinAsset = new ArrayList<Asset>();
		
		for (Asset asset : assets) {
			
			ApiIdentifier apiIdentifier = asset.getApiIdentifier();
			
			// L'euro n'a pas d'identifier
			if (Api.CRYPTOWATCH.equals(apiIdentifier.getApi())) {
				cryptoWatchAsset.add(asset);
			} else if (Api.YAHOO.equals(apiIdentifier.getApi())) {
				yahooAsset.add(asset);
			} else if (Api.KUCOIN.equals(apiIdentifier.getApi())) {
				kucoinAsset.add(asset);
			} else if (Api.NONE.equals(apiIdentifier.getApi())) {
				// Rien à faire
			} else {
				throw new NotYetImplementedException("Api non definie");
			}
		}
		
		List<Ohlc> ohlc = new ArrayList<Ohlc>();
		
		ohlc.addAll(cryptoWatchApiService.getHistoricalOhlc(cryptoWatchAsset));
		ohlc.addAll(yahooApiService.getHistoricalOhlc(yahooAsset));
		ohlc.addAll(kucoinApiService.getHistoricalOhlc(kucoinAsset));
		
		return ohlc;
	}

}
