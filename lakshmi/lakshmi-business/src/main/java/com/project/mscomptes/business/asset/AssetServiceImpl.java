package com.project.mscomptes.business.asset;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.mscomptes.business.AbstractDatabaseService;
import com.project.mscomptes.business.api.crypto.CryptoApiService;
import com.project.mscomptes.business.api.stock.StockApiService;
import com.project.mscomptes.model.Asset;
import com.project.mscomptes.model.AssetType;
import com.project.mscomptes.model.Ohlc;
import com.project.mscomptes.persistance.AssetDao;
import com.project.mscomptes.technical.FileUtils;

@Service("assetService")
public class AssetServiceImpl extends AbstractDatabaseService<Asset> implements AssetService {
	
	@Autowired
	AssetDao assetDao;
	
	@Autowired
	CryptoApiService cryptoApiService;
	
	@Autowired
	StockApiService stockApiService;
	
	@Override
	public AssetDao getDao() {
		return assetDao;
	}
	
	@Override
	@Transactional
	public List<Asset> findAllNotManaged() {
		return assetDao.findAll(AssetType.STOCK, false);
	}
	
	@Override
	@Transactional
	public List<Asset> findAll(AssetType type) {
		return assetDao.findAll(type, true);
	}
	
	
	@Override
	@Transactional
	public List<Asset> findAllSpecialOrder(AssetType type) {
		
		List<Asset> assets = new ArrayList<Asset>();
		List<Asset> foundAssets = new ArrayList<Asset>();
		List<Integer> foundIds = new ArrayList<Integer>();
		
		// D'abord on selectionne ceux qui n'ont pas de dépendance
		assets = assetDao.findAll(type, null);
		foundIds = assets.stream().map(Asset::getId).collect(Collectors.toList());

		// Ensuite on selectionne ceux qui sont dependants de ceux selectionnés, et ainsi de suite
		while(!CollectionUtils.isEmpty(foundIds)) {
			
			foundAssets = assetDao.findAll(type, foundIds);
			foundIds = foundAssets.stream().map(Asset::getId).collect(Collectors.toList());
			
			assets.addAll(foundAssets);
		}

		return assets;
	}
	
	@Override 
	@Transactional
	public void generateCsv() throws URISyntaxException, IOException {
		List<Ohlc> results = new ArrayList<Ohlc>();
		
		List<Asset> cryptoAssets = findAllSpecialOrder(AssetType.CRYPTO);
		
		// Get infos : crypto
		results.addAll(cryptoApiService.call(cryptoAssets));
		
		List<Asset> stockAssets = findAllSpecialOrder(AssetType.STOCK);
		
		results.addAll(stockApiService.call(stockAssets));
		
		// Write file
		writeFile(results);
	}
	
	private void writeFile(List<Ohlc> results) {
		// S'il existe déja on le supprime
		FileUtils.removeFile("export.csv");
		
		for (Ohlc ohlc : results) {
			FileUtils.writeOnFile("export.csv", ohlc.toString() + "\n");
		}
	}
}