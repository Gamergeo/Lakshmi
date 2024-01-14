package com.project.lakshmi.business.config;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.DatabaseService;
import com.project.lakshmi.model.config.Config;

@Service
public interface ConfigService extends DatabaseService<Config>{

	public Config find();

	public String findYahooKey();

}
