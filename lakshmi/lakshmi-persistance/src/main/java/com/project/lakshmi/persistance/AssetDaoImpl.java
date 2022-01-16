package com.project.lakshmi.persistance;

import org.springframework.stereotype.Repository;

import com.project.lakshmi.model.asset.Asset;

@Repository("assetDao")
public class AssetDaoImpl extends AbstractDAO<Asset> implements AssetDao {

	@Override
	protected void setTypeParameterClass() {
		typeParameterClass = Asset.class;
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
