package com.project.lakshmi.persistance.checklist;

import org.springframework.stereotype.Repository;

import com.project.lakshmi.model.checklist.Checklist;
import com.project.lakshmi.persistance.AbstractDAO;

@Repository("checklistDao")
public class ChecklistDaoImpl extends AbstractDAO<Checklist> implements ChecklistDao {
	
	@Override
	protected void setTypeParameterClass() {
		typeParameterClass = Checklist.class;
	}
	
}
