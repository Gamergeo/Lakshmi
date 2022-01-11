package com.project.lakshmi.persistance.checklist;

import org.springframework.stereotype.Repository;

import com.project.lakshmi.model.checklist.ChecklistStepInfos;
import com.project.lakshmi.persistance.AbstractDAO;

@Repository("checklistStepInfosDao")
public class ChecklistStepInfosDaoImpl extends AbstractDAO<ChecklistStepInfos> implements ChecklistStepInfosDao {
	
	@Override
	protected void setTypeParameterClass() {
		typeParameterClass = ChecklistStepInfos.class;
	}
}
