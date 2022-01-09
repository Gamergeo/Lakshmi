package com.project.mscomptes.business.asset;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.mscomptes.business.DatabaseService;
import com.project.mscomptes.model.Asset;
import com.project.mscomptes.model.AssetType;

@Service
public interface AssetService extends DatabaseService<Asset> {

	void generateCsv() throws URISyntaxException, IOException;

	List<Asset> findAllSpecialOrder(AssetType type);

	List<Asset> findAllNotManaged();

	List<Asset> findAll(AssetType type);
}

