package com.project.lakshmi.business.asset.ohlc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.api.ApiService;
import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;

@Service("ohlcService")
public class OhlcServiceImpl implements OhlcService {
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	ApiService apiService;
	
	/**
	 * @return historical ohlc list for asset, converted to euro
	 */
	@Override
	public List<Ohlc> getHistoricalOhlc(List<Asset> assets) {
		List<Ohlc> results = new ArrayList<Ohlc>();
		results.addAll(apiService.getHistoricalOhlc(assets));
		return convertToEuro(results);
	}
	
	/**
	 * @return the best ohlc for asset and date, converted to euro
	 */
	@Override
	public Ohlc getOhlc(Asset asset, Instant date) {
		
		// Dans le cas d'un euro, l'ohlc est simple
		if (asset.isEuro()) {
			Ohlc ohlc = new Ohlc(asset);
			ohlc.setOpen(1d);
			ohlc.setHigh(1d);
			ohlc.setLow(1d);
			ohlc.setClose(1d);
			ohlc.setDate(date);
			ohlc.setCurrency(asset);
			
			return ohlc;
		}
		
		Ohlc ohlc = apiService.getPriceOhlc(asset, date);
		
		return convertToEuro(ohlc);
	}
	
	/**
	 * modify the ohlc to convert to euroµ
	 * Ohlc is in currency asset
	 */
	@Override
	public Ohlc convertToEuro(Ohlc ohlc) {
		
		Ohlc result = ohlc;
		Asset currency = result.getCurrency();
		
		// On continue tant que l'on est pas en euro
		while(result.getCurrency() != null && !result.getCurrency().isEuro()) {
			// Sinon on va chercher le prix correspondant à l'asset currency et on ajuste
			Ohlc currencyOhlc = getOhlc(currency, result.getDate());
			result = updateOhlc(result, currencyOhlc);
		}
		
		return result;
	}
	
	/**
	 * Update une ohlc avec celle de la currency
	 */
	private Ohlc updateOhlc(Ohlc ohlc, Ohlc currencyOhlc) {
		Ohlc result = ohlc;
		
		result.setOpen(ohlc.getOpen() * currencyOhlc.getOpen());
		result.setHigh(ohlc.getHigh() * currencyOhlc.getHigh());
		result.setLow(ohlc.getLow() * currencyOhlc.getLow());
		result.setClose(ohlc.getClose() * currencyOhlc.getClose());
		
		result.setCurrency(currencyOhlc.getAsset().getApiIdentifier().getCurrency());
		
		return result;
	}
	
	/**
	 * Convert a list of ohlc to euro
	 * On ne fait pas appel à l'api ici, on utilise uniquement les données déja trouvées
	 */
	@Override
	public List<Ohlc> convertToEuro(List<Ohlc> ohlcList) {
		List<Ohlc> results = new ArrayList<Ohlc>();
		
		for (Ohlc ohlc : ohlcList) {
			
			Ohlc result = ohlc;
			
			// On continue tant que l'on est pas en euro ou que l'on a trouvé une correspondance
			while(result != null && !result.getCurrency().isEuro()) {
				
				// On récupère l'ohlc de la monnaie
				Asset currency = result.getCurrency();
				Ohlc ohlcCurrency = getOhlcForAssetAndDate(ohlcList, currency, result.getDate());
				
				// L'ohlc de la monnaie est introuvable, on supprime l'ohlc principal
				if (ohlcCurrency == null) {
					result = null;
				} else {
					// On mets à jour l'ohlc correspondante
					result = updateOhlc(result, ohlcCurrency);
				}
			}
			
			// On a trouvé une correspondance, elle est bien dans les results;
			if (result != null) {
				results.add(result);
			}
		}
		
		return results;
	}
	
	private Ohlc getOhlcForAssetAndDate(List<Ohlc> ohlcList, Asset asset, Instant date) {
		
		for (Ohlc ohlc : ohlcList) {
			
			if (asset.equals(ohlc.getAsset()) && date.equals(ohlc.getDate())) {
				return ohlc;
			}
		}
		
		return null;
		
	}
}