package com.project.lakshmi.persistance.asset.apiidentifier;

import org.springframework.stereotype.Repository;

import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.persistance.AbstractDAO;

@Repository("apiIdentifierDao")
public class ApiIdentifierDaoImpl extends AbstractDAO<ApiIdentifier> implements ApiIdentifierDao {

	@Override
	protected void setTypeParameterClass() {
		typeParameterClass = ApiIdentifier.class;
	}
	
}
