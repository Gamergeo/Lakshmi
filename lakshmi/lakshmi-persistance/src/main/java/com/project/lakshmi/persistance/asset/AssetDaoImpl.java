package com.project.lakshmi.persistance.asset;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

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
		
		return getCurrentSession().createQuery(query).getSingleResult();
	}
	
//	@Override
//	public List<OldAsset> findAll(AssetType type, boolean managed) {
//		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
//		CriteriaQuery<OldAsset> query = builder.createQuery(OldAsset.class);
//		Root<OldAsset> root = query.from(OldAsset.class);
//		query.select(root);
//		
//		Set<Predicate> restrictions = new HashSet<Predicate>();
//
//		restrictions.add(builder.equal(root.get(Asset_.managed), managed));
//		restrictions.add(builder.equal(root.get(Asset_.type), type));
//		
//		query.where(getPredicateArray(restrictions));
//		query.orderBy(builder.asc(root.get(Asset_.name)));
//		return getCurrentSession().createQuery(query).getResultList();
//	}
//	
//	@Override
//	public List<OldAsset> findAll(AssetType type, List<Integer> dependencies) {
//
//		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
//		CriteriaQuery<OldAsset> query = builder.createQuery(OldAsset.class);
//		Root<OldAsset> root = query.from(OldAsset.class);
//		query.select(root);
//		
//		Set<Predicate> restrictions = new HashSet<Predicate>();
//
//		restrictions.add(builder.equal(root.get(Asset_.managed), true));
//		restrictions.add(builder.equal(root.get(Asset_.type), type));
//
//		if (CollectionUtils.isEmpty(dependencies)) {
//			restrictions.add(builder.isNull(root.get(Asset_.dependence)));
//		} else {
//			restrictions.add(root.get(Asset_.dependence).in(dependencies));
//		}
//		
//		query.where(getPredicateArray(restrictions));
//		return getCurrentSession().createQuery(query).getResultList();
//	}
}
