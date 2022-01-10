package com.project.lakshmi.persistance;

import java.util.List;

import com.project.lakshmi.model.Asset;
import com.project.lakshmi.model.AssetType;

public interface AssetDao extends IDao<Asset> {

	List<Asset> findAll(AssetType type, List<Integer> dependencies);

	List<Asset> findAll(AssetType type, boolean managed);

}