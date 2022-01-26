package com.project.lakshmi.persistance.asset;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.model.api.ApiIdentifier_;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.Asset_;
import com.project.lakshmi.persistance.AbstractDAO;

@Repository("assetDao")
public class AssetDaoImpl extends AbstractDAO<Asset> implements AssetDao {

	@Override
	protected void setTypeParameterClass() {
		typeParameterClass = Asset.class;
	}
	
	@Override
	public Asset findByIsin(String isin) {
		String upperIsin = isin.toUpperCase();
		
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Asset> query = builder.createQuery(Asset.class);
		Root<Asset> root = query.from(Asset.class);
		query.select(root);
		
		Set<Predicate> restrictions = new HashSet<Predicate>();

		restrictions.add(builder.equal(root.get(Asset_.isin), upperIsin));
		
		query.where(getPredicateArray(restrictions));
		
		try {
			return getCurrentSession().createQuery(query).getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
//	/**
//	 * @return La liste des assets qui n'ont pas d'api identifier
//	 * SELECT * 
//	 * FROM ASSET 
//	 * WHERE NOT EXISTS 
//	 * (SELECT * FROM API_IDENTIFIER WHERE ASSET.ID = API_IDENTIFIER.ID)
//	 */
//	@Override
//	public List<Asset> findAllNotManaged() {
//		
//		// SELECT PRINCIPAL
//		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
//		CriteriaQuery<Asset> query = builder.createQuery(Asset.class);
//		Root<Asset> root = query.from(Asset.class);
//		
//		Subquery<ApiIdentifier> subquery = query.subquery(ApiIdentifier.class);
//		Root<ApiIdentifier> subRoot = subquery.from(ApiIdentifier.class);
//		subquery.select(subRoot);
//		subquery.where(builder.equal(root.get(Asset_.id), subRoot.get(ApiIdentifier_.id)));
//		
//		query.select(root);
//		
//		Set<Predicate> restrictions = new HashSet<Predicate>();
//		
//		restrictions.add(builder.not(builder.exists(subquery)));
//
//		query.where(getPredicateArray(restrictions));
//		query.orderBy(builder.asc(root.get(Asset_.label)));
//		return getCurrentSession().createQuery(query).getResultList();
//	}
	
	/**
	 * @return la liste des assts géré par l'api donné
	 * SELECT * 
	 * FROM ASSET, API_IDENTIFIER 
	 * WHERE ASSET.ID = API_IDENTIFIER.ID
	 * AND API_IDENTIFIER.API IN "Apis" 
	 */
	@Override
	public List<Asset> findAllManagedByApi(List<Api> apis) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Asset> query = builder.createQuery(Asset.class);
		Root<Asset> root = query.from(Asset.class);
		Join<Asset, ApiIdentifier> join = root.join(Asset_.apiIdentifier);
		query.select(root);
		
		Set<Predicate> restrictions = new HashSet<Predicate>();
		
		restrictions.add(join.get(ApiIdentifier_.api).in(apis));
		
		query.where(getPredicateArray(restrictions));
		query.orderBy(builder.asc(root.get(Asset_.label)));
		return getCurrentSession().createQuery(query).getResultList();
	}
}
