package com.project.lakshmi.business.config;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.model.config.Config;
import com.project.lakshmi.persistance.IDao;
import com.project.lakshmi.persistance.config.ConfigDao;

@Service("configService")
public class ConfigServiceImpl extends AbstractDatabaseService<Config> implements ConfigService {
	
	@Autowired
	private ConfigDao configDao;

	@Override
	public IDao<Config> getDao() {
		return configDao;
	}

	@Override
	@Transactional
	public Config find() {
		return configDao.findById(1);
	}

	@Override
	@Transactional
	public String findYahooKey() {
		return configDao.findById(1).getYahooKey();
	}
}
