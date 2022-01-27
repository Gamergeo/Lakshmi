package com.project.lakshmi.business.hash;

import org.springframework.stereotype.Service;

/**
 * Hachage
 */
@Service
public interface HashService {

	String encode(String key, String data);
	
}
