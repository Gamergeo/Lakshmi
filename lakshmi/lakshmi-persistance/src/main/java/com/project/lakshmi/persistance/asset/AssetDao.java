package com.project.lakshmi.persistance.asset;

import java.util.List;

import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.persistance.IDao;

public interface AssetDao extends IDao<Asset> {

	Asset findByIsin(String isin);

	/**
	 * @return la liste des assts géré par l'api donné
	 * SELECT * 
	 * FROM ASSET, API_IDENTIFIER 
	 * WHERE ASSET.ID = API_IDENTIFIER.ID
	 * AND API_IDENTIFIER.API IN "Apis" 
	 */
	List<Asset> findAllManagedByApi(List<Api> apis);


}