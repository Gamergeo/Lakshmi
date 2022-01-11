package com.project.lakshmi.persistance.checklist;

import org.springframework.stereotype.Repository;

import com.project.lakshmi.model.checklist.ChecklistStep;
import com.project.lakshmi.persistance.AbstractDAO;

@Repository("checklistStepDao")
public class ChecklistStepDaoImpl extends AbstractDAO<ChecklistStep> implements ChecklistStepDao {
	
	@Override
	protected void setTypeParameterClass() {
		typeParameterClass = ChecklistStep.class;
	}
}
