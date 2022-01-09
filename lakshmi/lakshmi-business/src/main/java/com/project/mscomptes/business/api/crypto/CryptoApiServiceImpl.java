package com.project.mscomptes.business.api.crypto;

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
import com.project.mscomptes.business.api.ApiService;
import com.project.mscomptes.business.ohlc.OhlcService;
import com.project.mscomptes.model.Asset;
import com.project.mscomptes.model.Ohlc;

@Service("cryptoApiService")
public class CryptoApiServiceImpl implements CryptoApiService {
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	OhlcService ohlcService;

	@Override
	public List<Ohlc> call(List<Asset> assets) throws URISyntaxException, IOException {
		
		List<Ohlc> results = new ArrayList<Ohlc>();
		
		for (Asset asset : assets) {
			results.addAll(call(asset, asset.getDependence(), results));
		}
		
		return results;
	}
	
	private List<Ohlc> call(Asset asset, Asset dependence, List<Ohlc> datas) throws URISyntaxException, IOException {
		
		String pair;
		
		if (dependence == null) {
			
			// Busd is exception
			if (asset.getIsin().equals("BUSD")) {
				pair = "eur" + asset.getIsin().toLowerCase();
			} else {
				pair = asset.getIsin().toLowerCase() + "eur";
			}
		} else {
			pair = asset.getIsin().toLowerCase() + dependence.getIsin().toLowerCase();
		}
		
		String uri = "https://api.cryptowat.ch/markets/" + asset.getMarket() + "/" + pair + "/ohlc";
		
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    parameters.add(new BasicNameValuePair("periods","86400"));
	    
	    String result = apiService.call(uri, parameters);
	    
	    ObjectMapper mapper = new ObjectMapper();
		JsonNode resultNode = mapper.readTree(result).findValue("86400");
		
		List<Ohlc> ohlcList = new ArrayList<Ohlc>();
		
		Iterator<JsonNode> iterator = resultNode.elements();
		
		while(iterator.hasNext()) {
			JsonNode line = iterator.next();
			
			Ohlc ohlc = new Ohlc(asset.getIsin());
			ohlc.setDate(line.get(0).asLong());
			
			if (dependence == null) {
				
				Double openPrice = line.get(1).asDouble();
				Double highPrice = line.get(2).asDouble();
				Double lowPrice = line.get(3).asDouble();
				Double closePrice = line.get(4).asDouble();
				
				// Le bnb est "a l'envers"
				if (asset.getIsin().equals("BUSD")) {
					openPrice = openPrice == 0 ? 0 : 1/openPrice;
					highPrice = highPrice == 0 ? 0 : 1/highPrice;
					lowPrice = lowPrice == 0 ? 0 : 1/lowPrice;
					closePrice = closePrice == 0 ? 0 : 1/closePrice;
				}
				ohlc.setOpenPrice(openPrice);
				ohlc.setHighPrice(highPrice);
				ohlc.setLowPrice(lowPrice);
				ohlc.setClosePrice(closePrice);
				
			} else {
				// Si l'asset est dépendant, il faut prendre le prix de la dépendence en compte
				Ohlc dependantOhlc = ohlcService.getOhlc(datas, dependence.getIsin(), ohlc.getDate());
				
				if (dependantOhlc != null) {
					ohlc.setOpenPrice(line.get(1).asDouble() * dependantOhlc.getOpenPrice());
					ohlc.setHighPrice(line.get(2).asDouble() * dependantOhlc.getHighPrice());
					ohlc.setLowPrice(line.get(3).asDouble() * dependantOhlc.getLowPrice());
					ohlc.setClosePrice(line.get(4).asDouble() * dependantOhlc.getClosePrice());
				}
			}
			
			ohlcList.add(ohlc);
		}
		
		return ohlcList;
	}
	
}
