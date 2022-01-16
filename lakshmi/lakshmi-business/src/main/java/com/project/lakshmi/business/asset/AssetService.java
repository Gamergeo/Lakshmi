package com.project.lakshmi.business.asset;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.DatabaseService;
import com.project.lakshmi.model.asset.Asset;

@Service
public interface AssetService extends DatabaseService<Asset> {
	
//	void generateCsv() throws URISyntaxException, IOException;
//
//	List<OldAsset> findAllSpecialOrder(AssetType type);
//
//	List<OldAsset> findAllNotManaged();
//
//	List<OldAsset> findAll(AssetType type);
}

