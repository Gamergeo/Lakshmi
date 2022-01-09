package com.project.mscomptes.business.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;

@Service
public interface ApiService {

	String call(String uri, List<NameValuePair> parameters) throws URISyntaxException, IOException;

	String call(String uri, List<NameValuePair> parameters, String apiKeyHeaderName, String apiKey)
			throws URISyntaxException, IOException;

}
