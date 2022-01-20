package com.project.lakshmi.persistance.asset;

import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.persistance.IDao;

public interface AssetDao extends IDao<Asset> {

	Asset findByIsin(String isin);

//	List<OldAsset> findAll(AssetType type, List<Integer> dependencies);
//
//	List<OldAsset> findAll(AssetType type, boolean managed);

}