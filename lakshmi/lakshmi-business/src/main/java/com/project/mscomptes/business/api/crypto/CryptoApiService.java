package com.project.mscomptes.business.api.crypto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.mscomptes.model.Asset;
import com.project.mscomptes.model.Ohlc;

@Service
public interface CryptoApiService {

	List<Ohlc> call(List<Asset> assets) throws URISyntaxException, IOException;

}
