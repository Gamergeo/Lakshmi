package com.project.lakshmi.business.asset;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.business.asset.ohlc.OhlcService;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.persistance.asset.AssetDao;

@Service("assetService")
public class AssetServiceImpl extends AbstractDatabaseService<Asset> implements AssetService {
	
	@Autowired
	AssetDao assetDao;
	
	@Autowired
	OhlcService ohlcService;
	
	@Override
	public AssetDao getDao() {
		return assetDao;
	}
	
	/**
	 * @return price for asset and date
	 * Pour cela, on recupère le ohlc correspondant à la minute et l'open correspond au prix
	 */
	@Override
	@Transactional
	public Double getPrice(Asset asset, Date date) {
		Ohlc ohlc = ohlcService.find(asset, date);
		return ohlc.getOpen();
	}
	
}