package com.project.lakshmi.business.asset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.persistance.AssetDao;

@Service("assetService")
public class AssetServiceImpl extends AbstractDatabaseService<Asset> implements AssetService {
	
	@Autowired
	AssetDao assetDao;
	
	@Override
	public AssetDao getDao() {
		return assetDao;
	}
	
}