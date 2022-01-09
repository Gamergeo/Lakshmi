package com.project.mscomptes.business;

import java.util.List;

import javax.transaction.Transactional;

import com.project.mscomptes.persistance.IDao;

public abstract class AbstractDatabaseService<T> implements DatabaseService<T> {
	
	public abstract IDao<T> getDao();
	
	@Override
	@Transactional
    public void saveOrUpdate(T type) {
    	getDao().saveOrUpdate(type);
    }
	
	@Override
	@Transactional
    public T findById(Integer id) {
    	return getDao().findById(id);
    }
	
	@Override
	@Transactional
    public void delete(T type) {
		getDao().delete(type);
    }

	@Override
	@Transactional
	public List<T> findAll() {
		return getDao().findAll();
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		getDao().delete(id);
	}
}