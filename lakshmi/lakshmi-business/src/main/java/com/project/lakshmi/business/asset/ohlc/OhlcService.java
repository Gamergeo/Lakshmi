package com.project.lakshmi.business.asset.ohlc;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;

@Service
public interface OhlcService {

	/**
	 * modify the ohlc to convert to euroµ
	 * Ohlc is in currency asset
	 * @return 
	 */
	Ohlc convertToEuro(Ohlc ohlc);
	
	/**
	 * @return the best ohlc for asset and date, converted to euro
	 */
	Ohlc getOhlc(Asset asset, Instant date);

	/**
	 * Convert a list of ohlc to euro
	 * On ne fait pas appel à l'api ici, on utilise uniquement les données déja trouvées
	 * @return 
	 */
	List<Ohlc> convertToEuro(List<Ohlc> ohlcList);

	/**
	 * @return historical ohlc list for asset, converted to euro
	 */
	List<Ohlc> getHistoricalOhlc(List<Asset> asset);


}

