package com.project.lakshmi.persistance.config;

import org.springframework.stereotype.Repository;

import com.project.lakshmi.model.config.Config;
import com.project.lakshmi.persistance.AbstractDAO;

@Repository("configDao")
public class ConfigDaoImpl extends AbstractDAO<Config> implements ConfigDao {
	
	@Override
	protected void setTypeParameterClass() {
		typeParameterClass = Config.class;
	}
}
