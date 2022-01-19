package com.project.lakshmi.business.api.cryptowatch;

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
import com.project.lakshmi.business.api.ApiServiceImpl;
import com.project.lakshmi.business.asset.ohlc.OhlcService;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;

@Service("cryptoApiService")
public class CryptoWatchApiServiceImpl extends ApiServiceImpl implements CryptoWatchApiService {
	
	@Autowired
	OhlcService ohlcService;
	
	@Override
	public List<Ohlc> getOhlc(Asset asset) throws URISyntaxException, IOException {
		String uri = CryptoWatchApiConstants.BASE_URI 
				+ asset.getApiIdentifier().getMarket() + "/" 
				+ asset.getApiIdentifier().getPair()
				+ "/ohlc";
		
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    parameters.add(new BasicNameValuePair(CryptoWatchApiConstants.PERIOD_PARAMETER_NAME, CryptoWatchApiConstants.PERIOD_PARAMETER_VALUE));
	    
	    String result = call(uri, parameters);
	    
	    ObjectMapper mapper = new ObjectMapper();
		JsonNode resultNode = mapper.readTree(result).findValue(CryptoWatchApiConstants.PERIOD_PARAMETER_VALUE);
		
		List<Ohlc> ohlcList = new ArrayList<Ohlc>();
		
		Iterator<JsonNode> iterator = resultNode.elements();
		
		while(iterator.hasNext()) {
			JsonNode line = iterator.next();

			Ohlc ohlc = new Ohlc();
			ohlc.setAsset(asset);
			ohlc.setDate(line.get(0).asLong());
			ohlc.setOpen(line.get(1).asDouble());
			ohlc.setHigh(line.get(2).asDouble());
			ohlc.setLow(line.get(3).asDouble());
			ohlc.setClose(line.get(4).asDouble());
			
			ohlcService.saveOrUpdate(ohlc, asset.getApiIdentifier().getCurrency());
			
			ohlcList.add(ohlc);
		}
		
		return ohlcList;
	}

}
