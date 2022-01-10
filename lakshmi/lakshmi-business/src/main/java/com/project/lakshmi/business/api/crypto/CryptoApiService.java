package com.project.lakshmi.business.api.crypto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.Asset;
import com.project.lakshmi.model.Ohlc;

@Service
public interface CryptoApiService {

	List<Ohlc> call(List<Asset> assets) throws URISyntaxException, IOException;

}
