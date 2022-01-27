package com.project.lakshmi.business.api.cryptowatch;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.lakshmi.business.api.ApiServiceImpl;
import com.project.lakshmi.business.asset.apiIdentifier.ApiIdentifierService;
import com.project.lakshmi.business.asset.ohlc.OhlcService;
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.technical.ApplicationException;

@Service("cryptoApiService")
public class CryptoWatchApiServiceImpl extends ApiServiceImpl implements CryptoWatchApiService {
	
	@Autowired
	OhlcService ohlcService;
	
	@Autowired
	ApiIdentifierService apiIdentifierService;
	
	/**
	 * @return all the day ohlc for asset
	 */
	@Override
	public List<Ohlc> getHistoricalOhlc(List<Asset> assets) {
		List<Ohlc> ohlc = new ArrayList<Ohlc>();
		
		// Pour cryptowatch, on fait les appels un par un
		for (Asset asset : assets) {
			ohlc.addAll(getOhlc(asset, CryptoWatchApiConstants.PARAMETER_PERIOD_DAY, null, null));
		}
		
		return ohlc;
	}
	
	private String getBestPeriod(Instant instant) {
		
		long diff = Instant.now().getEpochSecond() - instant.getEpochSecond();
		
		for(String period : CryptoWatchApiConstants.PARAMETER_PERIOD_POSSIBLE_VALUES) {
			
			if (diff / Long.valueOf(period) < CryptoWatchApiConstants.OHLC_LIMIT) {
				return period;
			}
		}
		
		throw new ApplicationException("Date trop lointaine " + instant);
	}
	
	/**
	 * Determine la date ant�rieure
	 * Celle-ci d�pends de la p�riode, on cherche � n'avoir qu'un seul ohlc
	 * Cette date est la premi�re ohlc trouv� pour la p�riode donn�e
	 */
	private String getAfterParameter(Instant instant, String period) {
		Long floor = instant.getEpochSecond() / Long.valueOf(period);
		Long result = floor * Long.valueOf(period);
		
		return result.toString();
	}
	
	/**
	 * Determine la date post�rieur
	 * Celle-ci d�pends de la p�riode, on cherche � n'avoir qu'un seul ohlc
	 * Cette date est JUSTE AVANT la seconde ohlc trouv� pour la p�riode donn�e
	 */
	private String getBeforeParameter(Instant instant, String period) {
		Long ceil = instant.getEpochSecond() / Long.valueOf(period) + 1;
		Long result = ceil * Long.valueOf(period) - 1;
		
		return result.toString();
	}
	
	/*
	 * @return the best ohlc for asset
	 * Cryptowatch limite � 6000 le nombre d'ohlc disponible
	 * Du coup, plus la date est lointaine, plus la p�riode doit �tre grande
	 */
	@Override
	public Ohlc getPriceOhlc(Asset asset, Instant instant) {
		String period = getBestPeriod(instant);
		String after = getAfterParameter(instant, period);
		String before = getBeforeParameter(instant, period);
		
		List<Ohlc> ohlcs = getOhlc(asset, period, after, before);
		
		// On ne doit en trouver qu'une
		
		if (ohlcs.size() > 1) {
			throw new ApplicationException("Plusieurs ohlc trouv� pour " + asset.getIsin() + " " + instant.toString());
		} else if (ohlcs.size() == 0) {
			throw new ApplicationException("Aucun ohlc trouv� pour " + asset.getIsin() + " " + instant.toString());
		}
		
		return ohlcs.get(0);
	}
	
	private List<Ohlc> getOhlc(Asset asset, String period, String after, String before) {
		String uri = CryptoWatchApiConstants.BASE_URI 
				+ asset.getApiIdentifier().getMarket() + "/" 
				+ asset.getApiIdentifier().getPair()
				+ "/ohlc";
		
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    parameters.add(new BasicNameValuePair(CryptoWatchApiConstants.PARAMETER_PERIOD, period));
	    
	    if (after != null) {
	    	parameters.add(new BasicNameValuePair(CryptoWatchApiConstants.PARAMETER_AFTER, after));
	    }
	    
	    if (before != null) {
	    	parameters.add(new BasicNameValuePair(CryptoWatchApiConstants.PARAMETER_BEFORE, before));
	    }
	    
	    CloseableHttpResponse response = call(uri, parameters);
	    String result = getContent(response);
	    
	    ObjectMapper mapper = new ObjectMapper();
		JsonNode resultNode;
		try {
			resultNode = mapper.readTree(result).findValue(period);
		} catch (JsonProcessingException exception) {
			throw new ApplicationException(exception.getMessage());
		}
		
		List<Ohlc> ohlcList = new ArrayList<Ohlc>();
		
		Iterator<JsonNode> iterator = resultNode.elements();
		
		while(iterator.hasNext()) {
			JsonNode line = iterator.next();

			Ohlc ohlc = new Ohlc(asset);
			ohlc.setDate(line.get(0).asLong());
			ohlc.setOpen(line.get(1).asDouble());
			ohlc.setHigh(line.get(2).asDouble());
			ohlc.setLow(line.get(3).asDouble());
			ohlc.setClose(line.get(4).asDouble());
			
			ohlcList.add(ohlc);
		}
		
		return ohlcList;
	}
	
	/**
	 * @return une liste de tous les identifiants pr�sents
	 */
	@Override
	public List<ApiIdentifier> getAllIdentifiers() {
		String uri = CryptoWatchApiConstants.MARKET_URI;
		
	    CloseableHttpResponse response = call(uri);
	    String result = getContent(response);
		    
	    ObjectMapper mapper = new ObjectMapper();
		JsonNode resultNode;
		try {
			resultNode = mapper.readTree(result).findValue(CryptoWatchApiConstants.RESULT);
		} catch (JsonProcessingException exception) {
			throw new ApplicationException(exception.getMessage());
		}
		
		List<ApiIdentifier> identifiers = new ArrayList<ApiIdentifier>();
			
		Iterator<JsonNode> iterator = resultNode.elements();
			
		while(iterator.hasNext()) {
			JsonNode line = iterator.next();
			
			// On v�rifie que la ligne est active
			if (line.findValue(CryptoWatchApiConstants.RESULT_ACTIVE).asBoolean()) {
				
				String market = line.findValue(CryptoWatchApiConstants.RESULT_EXCHANGE).asText();
				String pair = line.findValue(CryptoWatchApiConstants.RESULT_PAIR).asText();
				
				ApiIdentifier apiIdentifier = new ApiIdentifier();
				apiIdentifier.setRawSymbol(pair);
				apiIdentifier.setMarket(market);
				apiIdentifier.setApi(Api.CRYPTOWATCH);
				
				// Tout a �t� trouv�, on ajoute l'identifier
				if (apiIdentifier != null) {
					identifiers.add(apiIdentifier);
				}
			}
		}
		
		return identifiers;
	}
}
