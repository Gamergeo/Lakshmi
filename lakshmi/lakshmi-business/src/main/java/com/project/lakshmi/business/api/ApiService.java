package com.project.lakshmi.business.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;

/**
 * Generic call to api
 */
@Service
public interface ApiService {

	/**
	 * @return la liste des ohlc pour un asset
	 */
	List<Ohlc> getOhlc(Asset asset) throws URISyntaxException, IOException;

}
