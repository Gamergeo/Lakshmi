package com.project.lakshmi.persistance;

import java.util.List;

public interface IDao<T> {

	public void persist(T type);
    
    public void update(T type);   
    
    public void saveOrUpdate(T type);

    public T findById(Integer id);
 
    public void delete(T type);

	public List<T> findAll();
	
	public void delete(Integer id);

	public List<T> findAllOrderedBy(String attributeName);
}
