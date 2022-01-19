package com.project.lakshmi.business.api;

import java.io.IOException;
import java.net.URISyntaxException;
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
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;

/**
 * Generic call to api
 */
@Service("apiService")
public abstract class ApiServiceImpl implements ApiService {
	
	@Autowired
	CryptoWatchApiService cryptoWatchApiService;
	
	protected String call(String uri, List<NameValuePair> parameters) throws URISyntaxException, IOException {
		return call(uri, parameters, null, null);
	}
	
	protected String call(String uri, List<NameValuePair> parameters, String apiKeyHeaderName, String apiKey) throws URISyntaxException, IOException {
		 String response_content = "";
	
	    URIBuilder query = new URIBuilder(uri);
	    query.addParameters(parameters);

	    CloseableHttpClient client = HttpClients.createDefault();
	    HttpGet request = new HttpGet(query.build());

	    request.setHeader(HttpHeaders.ACCEPT, "application/json");
	    
	    if (!StringUtils.isEmpty(apiKeyHeaderName)) {
	    	request.addHeader(apiKeyHeaderName, apiKey);
	    }

	    CloseableHttpResponse response = client.execute(request);

	    try {
	    	System.out.println(response.getStatusLine() + uri + parameters.toString());
	    	HttpEntity entity = response.getEntity();
	    	response_content = EntityUtils.toString(entity);
	    	EntityUtils.consume(entity);
	    } finally {
	    	response.close();
	    }

	    return response_content;
	}
	
	@Override
	public List<Ohlc> getOhlc(Asset asset) throws URISyntaxException, IOException {
		
		if (Api.CRYPTOWATCH.equals(asset.getApiIdentifier().getApi())) {
			return cryptoWatchApiService.getOhlc(asset);
		}
		
		throw new NotYetImplementedException("import api non implémenté");
	}
//
//	public abstract List<Ohlc> getPrice(Asset asset, Date date);

}
