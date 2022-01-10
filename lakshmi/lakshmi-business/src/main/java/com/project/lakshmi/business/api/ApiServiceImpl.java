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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("apiService")
public class ApiServiceImpl implements ApiService {
	
	@Override
	public String call(String uri, List<NameValuePair> parameters) throws URISyntaxException, IOException {
		return call(uri, parameters, null, null);
	}
	
	@Override
	public String call(String uri, List<NameValuePair> parameters, String apiKeyHeaderName, String apiKey) throws URISyntaxException, IOException {
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
	    	System.out.println(response.getStatusLine());
	    	HttpEntity entity = response.getEntity();
	    	response_content = EntityUtils.toString(entity);
	    	EntityUtils.consume(entity);
	    } finally {
	    	response.close();
	    }

	    return response_content;
	}

}
