package com.project.lakshmi.business.api.yahoo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.lakshmi.business.api.ApiServiceImpl;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.technical.ApplicationException;

@Service("yahooApiService")
public class YahooApiServiceImpl extends ApiServiceImpl implements YahooApiService {
	
	@Override
	public Ohlc getPriceOhlc(Asset asset, Instant instant) {
		throw new NotYetImplementedException("On ne peux pas recuperer le prix sur yahoo pour l'instant");
	}
	
	@Override
	public List<Ohlc> getHistoricalOhlc(List<Asset> assets) {
		List<Ohlc> results = new ArrayList<Ohlc>();
		
		// Afin de minimiser les requetes, on fait par groupe de 10
		for (int i = 0; i < assets.size(); i = i + 10) {
			
			int max = Math.min(assets.size(), i + 10);
			
			List<Asset> subList = assets.subList(i, max);
			results.addAll(getOhlc(subList, YahooApiConstants.INTERVAL_DAY));
		}
		
		return results;
	}
	
	private List<Ohlc> getOhlc(List<Asset> assets, String interval) {
		String uri = YahooApiConstants.BASE_URI;
		
	    List<NameValuePair> headers = new ArrayList<NameValuePair>();
	    headers.add(new BasicNameValuePair(YahooApiConstants.API_KEY_HEADER_YAHOO, YahooApiConstants.API_KEY_YAHOO));
		
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    parameters.add(new BasicNameValuePair(YahooApiConstants.PARAMETER_INTERVAL,interval));
	    parameters.add(new BasicNameValuePair(YahooApiConstants.PARAMETER_RANGE, YahooApiConstants.RANGE_5Y));
	    parameters.add(new BasicNameValuePair(YahooApiConstants.PARAMETER_SYMBOL, getSymbols(assets)));
	    
	    String result = getContent(call(uri, headers, parameters));
	    
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode rootNode;
		try {
			rootNode = mapper.readTree(result);
		} catch (JsonProcessingException exception) {
			throw new ApplicationException(exception.getMessage());
		}
		
		List<Ohlc> ohlcList = new ArrayList<Ohlc>();
	    
	    for (Asset asset : assets) {
	    	
	    	JsonNode assetNode = rootNode.findValue(asset.getApiIdentifier().getSymbol());
	    	JsonNode timestampNode = assetNode.findValue("timestamp");
	    	JsonNode closeNode = assetNode.findValue("close");
	    	
			Iterator<JsonNode> timestampIterator = timestampNode.elements();
			Iterator<JsonNode> closeIterator = closeNode.elements();
			
			while(timestampIterator.hasNext()) {
				JsonNode timestamp = timestampIterator.next();
				JsonNode close = closeIterator.next();
				
				Ohlc ohlc = new Ohlc(asset);
				ohlc.setDate(timestamp.asLong());
				ohlc.setPrice(close.asDouble());
				
				ohlcList.add(ohlc);
			}
	    }
		
		return ohlcList;
	}
	
	private String getSymbols(List<Asset> assets) {
		String symbols = "";
		
		for (Asset asset : assets) {
			
			if (symbols.isEmpty()) {
				symbols += asset.getApiIdentifier().getSymbol();
			} else {
				symbols += "," + asset.getApiIdentifier().getSymbol();
			}
		}
		
		return symbols;
	}
}
