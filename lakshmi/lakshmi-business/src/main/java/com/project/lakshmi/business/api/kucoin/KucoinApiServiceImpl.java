package com.project.lakshmi.business.api.kucoin;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import com.project.lakshmi.business.hash.HashService;
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.technical.ApplicationException;
import com.project.lakshmi.technical.DateUtil;
import com.project.lakshmi.technical.NumberUtil;

@Service("kucoinApiService")
public class KucoinApiServiceImpl extends ApiServiceImpl implements KucoinApiService {
	
	@Autowired
	HashService hashService;
	
	@Autowired
	ApiIdentifierService apiIdentifierService;
	
	@Override
	public List<Ohlc> getHistoricalOhlc(List<Asset> assets) {
		List<Ohlc> ohlc = new ArrayList<Ohlc>();
		
		// Pour cryptowatch, on fait les appels un par un
		for (Asset asset : assets) {
			ohlc.addAll(getOhlc(asset, KucoinApiConstants.PARAMETER_PERIOD_DAY, null, null));
		}
		
		return ohlc;
	}
	
	@Override
	public Ohlc getPriceOhlc(Asset asset, Instant instant) {
		String period = KucoinApiConstants.PARAMETER_PERIOD_MINUTE;
		String startAt = getStartParameter(instant);
		String endAt = getEndParameter(instant);
		
		List<Ohlc> ohlcs = getOhlc(asset, period, startAt, endAt);
		
		// On ne doit en trouver qu'une
		
		if (ohlcs.size() != 1) {
			throw new ApplicationException("Plusieurs ohlc trouvé pour " + asset.getIsin() + " " + instant.toString());
		}
		
		return ohlcs.get(0);
	}
	
	private String hashPassphrase() {
		return hashService.encode(KucoinApiConstants.API_SECRET, KucoinApiConstants.API_KEY);
	}
	
	private String sign(String timestamp, String baseUri, List<NameValuePair> parameters) {
		String toEncode = timestamp; // timestamp
		toEncode += "GET"; // method
		toEncode += getCompleteUri(baseUri, parameters); // endpoint
		toEncode += ""; // body;
		
		return hashService.encode(KucoinApiConstants.API_SECRET, toEncode);
	}
	
	private String getCompleteUri(String baseUri, List<NameValuePair> parameters) {
		String uri = baseUri;
		boolean first = true;
		
		for (NameValuePair parameter : parameters) {
			
			if (first) {
				uri += "?";
				first = false;
			} else {
				uri += "&";
			}
			
			uri += parameter.getName() + "=" + parameter.getValue();
		}
		
		return uri;
	}
	
	private List<NameValuePair> getHeaders(String uri) {
		return getHeaders(uri, new ArrayList<NameValuePair>());
	}
	
	private List<NameValuePair> getHeaders(String uri, List<NameValuePair> parameters) {
		List<NameValuePair> headers = new ArrayList<NameValuePair>();
	    String timeStamp = NumberUtil.toExactString(Instant.now().getEpochSecond());
	    headers.add(new BasicNameValuePair(KucoinApiConstants.HEADER_API_KEY, KucoinApiConstants.API_KEY));
	    String signature = sign(timeStamp, uri, parameters);
	    headers.add(new BasicNameValuePair(KucoinApiConstants.HEADER_API_SIGN, signature));
	    headers.add(new BasicNameValuePair(KucoinApiConstants.HEADER_API_TIMESTAMP, timeStamp));
	    headers.add(new BasicNameValuePair(KucoinApiConstants.HEADER_API_PASSPHRASE, hashPassphrase()));
	    headers.add(new BasicNameValuePair(KucoinApiConstants.HEADER_API_KEY_VERSION, KucoinApiConstants.API_KEY_VERSION));
	    
	    return headers;
	}
	
	private List<Ohlc> getOhlc(Asset asset, String period, String startAt, String endAt) {
		String uri = KucoinApiConstants.BASE_URI + KucoinApiConstants.URI_OHLC;
	    
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    parameters.add(new BasicNameValuePair(KucoinApiConstants.PARAMETER_PERIOD, period));
	    
	    String symbol = asset.getIsin() + "-" + asset.getApiIdentifier().getCurrency().getIsin();
	    parameters.add(new BasicNameValuePair(KucoinApiConstants.PARAMETER_SYMBOL, symbol));
	    
	    if (startAt != null) {
	    	parameters.add(new BasicNameValuePair(KucoinApiConstants.PARAMETER_START, startAt));
	    }
	    
	    if (endAt != null) {
	    	parameters.add(new BasicNameValuePair(KucoinApiConstants.PARAMETER_END, endAt));
	    }
		
	    List<NameValuePair> headers = getHeaders(uri, parameters);
	    
	    String result = callOrWait(uri, headers, parameters);
	    
	    ObjectMapper mapper = new ObjectMapper();
		JsonNode resultNode;
		try {
			resultNode = mapper.readTree(result).findValue(KucoinApiConstants.RESULT_DATA);
		} catch (JsonProcessingException exception) {
			throw new ApplicationException(exception.getMessage());
		}
		
		List<Ohlc> ohlcList = new ArrayList<Ohlc>();
		
		Iterator<JsonNode> iterator = resultNode.elements();
		
		while(iterator.hasNext()) {
			JsonNode line = iterator.next();

			Ohlc ohlc = new Ohlc(asset);
			ohlc.setDate(line.get(KucoinApiConstants.RESULT_INDEX_DATE).asLong());
			ohlc.setOpen(line.get(KucoinApiConstants.RESULT_INDEX_OPEN).asDouble());
			ohlc.setHigh(line.get(KucoinApiConstants.RESULT_INDEX_HIGH).asDouble());
			ohlc.setLow(line.get(KucoinApiConstants.RESULT_INDEX_LOW).asDouble());
			ohlc.setClose(line.get(KucoinApiConstants.RESULT_INDEX_CLOSE).asDouble());
			
			ohlcList.add(ohlc);
		}
		
		return ohlcList;
	}
	
	/**
	 * Réalise l'appel
	 * Si on a une réponse de type too many request, on attends et on retente
	 */
	private String callOrWait(String uri, List<NameValuePair> headers, List<NameValuePair> parameters) {
	    CloseableHttpResponse response = call(uri, headers, parameters);
	    int attempts = 1;
	    
	    // Too many request, on wait 15s et on retente
	    while (attempts <= 10 && getStatus(response) == 429) {
	    	try {
	    		System.out.println("Wait 15s for kucoin api");
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				throw new ApplicationException(e.getMessage());
			}
	    	
	    	attempts++;
	    	response = call(uri, headers, parameters);
	    }
	    
	    return getContent(response);
	}
	
	/**
	 * Determine la date de départ (date trunqué de la minute)
	 */
	private String getStartParameter(Instant instant) {
		return String.valueOf(DateUtil.truncateMinute(instant).getEpochSecond());
	}
	
	/**
	 * Determine la date de fin (date trunqué de la minute + 59s)
	 */
	private String getEndParameter(Instant instant) {
		
		Instant startDate = DateUtil.truncateMinute(instant);
		Instant endDate = startDate.plus(59, ChronoUnit.SECONDS);
		
		return String.valueOf(endDate.getEpochSecond());
	}
	
	
	/**
	 * @return une liste de tous les identifiants présents
	 */
	@Override
	public List<ApiIdentifier> getAllIdentifiers() {
		String uri = KucoinApiConstants.BASE_URI + KucoinApiConstants.URI_ALL_TICKERS;
		
		List<NameValuePair> headers = getHeaders(uri);
		
		String result = callOrWait(uri, headers, new ArrayList<NameValuePair>());
		    
	    ObjectMapper mapper = new ObjectMapper();
		JsonNode resultNode;
		try {
			resultNode = mapper.readTree(result).findValue(KucoinApiConstants.RESULT_DATA).findValue(KucoinApiConstants.RESULT_DATA_TICKER);
		} catch (JsonProcessingException exception) {
			throw new ApplicationException(exception.getMessage());
		}
		
		List<ApiIdentifier> identifiers = new ArrayList<ApiIdentifier>();
			
		Iterator<JsonNode> iterator = resultNode.elements();
			
		while(iterator.hasNext()) {
			JsonNode line = iterator.next();
			
			String pair = line.findValue(KucoinApiConstants.RESULT_DATA_SYMBOL).asText();
			
			ApiIdentifier apiIdentifier = new ApiIdentifier();
			apiIdentifier.setRawSymbol(pair);
			apiIdentifier.setApi(Api.KUCOIN);
			
			// Tout a été trouvé, on ajoute l'identifier
			if (apiIdentifier != null) {
				identifiers.add(apiIdentifier);
			}
		}
			
		
		return identifiers;
	}
}
