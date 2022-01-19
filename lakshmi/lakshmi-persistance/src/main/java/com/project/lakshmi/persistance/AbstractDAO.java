package com.project.lakshmi.persistance;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDAO<T> implements IDao<T>{

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	protected Class<T> typeParameterClass;
	
	public AbstractDAO() {
		setTypeParameterClass();
	}
	
//    protected Type getTypeParameterClass() {
//    	return ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//    }
	
    // Mandatory to set with T.class for proper initialisation of abstract lethod
    protected abstract void setTypeParameterClass();
	
	protected Connection getConnection() throws NamingException, SQLException {		
		return dataSource.getConnection();
	}
	
	protected DataSource getDataSource() {
		
		if (dataSource == null) {
			throw new NullPointerException("dataSource is not correctly setted");
		}
		
		return dataSource;
	}
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected CriteriaBuilder getBuilder() {
		return sessionFactory.getCurrentSession().getCriteriaBuilder();
	}
	
	@Override
    public void persist(T type) {
        getCurrentSession().persist(type);
    }
    
	@Override
    public void update(T type) {
        getCurrentSession().update(type);
    }
    
	@Override
    public void saveOrUpdate(T type) {
        getCurrentSession().saveOrUpdate(type);
    }

	@Override
    public T findById(Integer id) {
    	return getCurrentSession().get(typeParameterClass, id);
    }
 
	@Override
    public void delete(T type) {
        getCurrentSession().delete(type);
    }
    
	@Override
    public void delete(Integer id) {
        T type = (T) getCurrentSession().load(typeParameterClass, id);
        
	    getCurrentSession().delete(type);
    }
    
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(typeParameterClass);
		Root<T> root = query.from(typeParameterClass);
		query.select(root);

    	Query q = getCurrentSession().createQuery(query);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAllOrderedBy(String attributeName) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(typeParameterClass);
		Root<T> root = query.from(typeParameterClass);
		query.select(root);
		query.orderBy(builder.asc(root.get(attributeName)));

    	Query q = getCurrentSession().createQuery(query);
		return q.getResultList();
	}
	
	public Predicate[] getPredicateArray(Collection<Predicate> predicates) {
		return (Predicate[]) predicates.toArray(new Predicate[predicates.size()]);
	}
	
}
