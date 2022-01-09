package com.project.mscomptes.persistance;

import java.util.List;

import com.project.mscomptes.model.Asset;
import com.project.mscomptes.model.AssetType;

public interface AssetDao extends IDao<Asset> {

	List<Asset> findAll(AssetType type, List<Integer> dependencies);

	List<Asset> findAll(AssetType type, boolean managed);

}