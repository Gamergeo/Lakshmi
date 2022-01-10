package com.project.lakshmi.business.api.stock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.lakshmi.business.api.ApiService;
import com.project.lakshmi.model.Asset;
import com.project.lakshmi.model.Ohlc;
import com.project.lakshmi.technical.TechnicalConstants;

@Service("stockApiService")
public class StockApiServiceImpl implements StockApiService {
	
	@Autowired
	ApiService apiService;
	
	@Override
	public List<Ohlc> call(List<Asset> assets) throws URISyntaxException, IOException {
		
		List<Ohlc> results = new ArrayList<Ohlc>();
		
		// Afin de minimiser les requetes, on fait par groupe de 10
		for (int i = 0; i < assets.size(); i = i + 10) {
			
			int max = Math.min(assets.size(), i + 10);
			
			List<Asset> subList = assets.subList(i, max);
			results.addAll(makeCall(subList));
		}
		
		return results;
	}
	
	private List<Ohlc> makeCall(List<Asset> assets) throws URISyntaxException, IOException {
		String uri = "https://yfapi.net/v8/finance/spark";
		
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    parameters.add(new BasicNameValuePair("interval","1d"));
	    parameters.add(new BasicNameValuePair("range","5y"));
	    parameters.add(new BasicNameValuePair("symbols", getSymbols(assets)));
	    
	    String result = apiService.call(uri, parameters, TechnicalConstants.API_KEY_HEADER_YAHOO, TechnicalConstants.API_KEY_YAHOO);
	    
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode rootNode = mapper.readTree(result);
		List<Ohlc> ohlcList = new ArrayList<Ohlc>();
	    
	    for (Asset asset : assets) {
	    	
	    	JsonNode assetNode = rootNode.findValue(asset.getSymbol());
	    	JsonNode timestampNode = assetNode.findValue("timestamp");
	    	JsonNode closeNode = assetNode.findValue("close");
	    	
			Iterator<JsonNode> timestampIterator = timestampNode.elements();
			Iterator<JsonNode> closeIterator = closeNode.elements();
			
			while(timestampIterator.hasNext()) {
				JsonNode timestamp = timestampIterator.next();
				JsonNode close = closeIterator.next();
				
				Ohlc ohlc = new Ohlc(asset.getIsin());
				ohlc.setDate(timestamp.asLong());
				ohlc.setPrice(close.asDouble());
				
				if (ohlc.getOpenPrice() != 0) {
					ohlcList.add(ohlc);
				}
			}
	    }
		
		return ohlcList;
	}
	
	private String getSymbols(List<Asset> assets) {
		String symbols = "";
		
		for (Asset asset : assets) {
			
			if (symbols.isEmpty()) {
				symbols += asset.getSymbol();
			} else {
				symbols += "," + asset.getSymbol();
			}
		}
		
		return symbols;
	}
	
}
