package com.project.lakshmi.persistance.asset.ohlc;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.model.asset.price.Ohlc_;
import com.project.lakshmi.persistance.AbstractDAO;

@Repository("ohlcDao")
public class OhlcDaoImpl extends AbstractDAO<Ohlc> implements OhlcDao {

	@Override
	protected void setTypeParameterClass() {
		typeParameterClass = Ohlc.class;
	}
	
	@Override
	public Ohlc findOhlc(Integer assetId, Date date) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Ohlc> query = builder.createQuery(Ohlc.class);
		Root<Ohlc> root = query.from(Ohlc.class);
		query.select(root);
		
		Set<Predicate> restrictions = new HashSet<Predicate>();

		restrictions.add(builder.equal(root.get(Ohlc_.asset), assetId));
		restrictions.add(builder.equal(root.get(Ohlc_.date), date));
		
		// Un resultat ou aucun
//		try {
			return getCurrentSession().createQuery(query).getSingleResult();
//		} catch (NoResultException exception) {
//			return null;
//		}
	}
	
	/**
	 * @return the maximum of high price between two date
	 */
	@Override
	public Ohlc findMax(Integer assetId, Date beginDate, Date endDate) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Ohlc> query = builder.createQuery(Ohlc.class);
		Root<Ohlc> root = query.from(Ohlc.class);
		query.select(root);
		
		Set<Predicate> restrictions = new HashSet<Predicate>();

		restrictions.add(builder.equal(root.get(Ohlc_.asset), assetId));
		restrictions.add(builder.between(root.get(Ohlc_.date), beginDate, endDate));
		
		// On trie par high price desc
		query.orderBy(builder.desc(root.get(Ohlc_.high)));
		
		return getCurrentSession().createQuery(query).getSingleResult();
	}
	
	/**
	 * @return the minimum of low price between two date
	 */
	@Override
	public Ohlc findMin(Integer assetId, Date beginDate, Date endDate) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Ohlc> query = builder.createQuery(Ohlc.class);
		Root<Ohlc> root = query.from(Ohlc.class);
		query.select(root);
		
		Set<Predicate> restrictions = new HashSet<Predicate>();

		restrictions.add(builder.equal(root.get(Ohlc_.asset), assetId));
		restrictions.add(builder.between(root.get(Ohlc_.date), beginDate, endDate));
		
		// On trie par high price desc
		query.orderBy(builder.asc(root.get(Ohlc_.low)));
		
		return getCurrentSession().createQuery(query).getSingleResult();
	}
	
}
