package com.project.lakshmi.persistance.asset;

import java.util.List;

import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.persistance.IDao;

public interface AssetDao extends IDao<Asset> {

	Asset findByIsin(String isin);

	/**
	 * @return La liste des assets qui n'ont pas d'api identifier
	 * SELECT * FROM ASSET WHERE ID NOT IN (SELECT DISTINCT ID FROM API_IDENTIFIER)
	 */
	List<Asset> findAllNotManaged();

	/**
	 * @return la liste des assts géré par l'api donné
	 * SELECT * 
	 * FROM ASSET, API_IDENTIFIER 
	 * WHERE ASSET.ID = API_IDENTIFIER.ID
	 * AND API_IDENTIFIER.API = "Api" 
	 */
	List<Asset> findAllManagedByApi(Api api);

//	List<OldAsset> findAll(AssetType type, List<Integer> dependencies);
//
//	List<OldAsset> findAll(AssetType type, boolean managed);

}