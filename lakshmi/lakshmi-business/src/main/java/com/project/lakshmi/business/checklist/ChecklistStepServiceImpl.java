package com.project.lakshmi.business.checklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.model.checklist.ChecklistStep;
import com.project.lakshmi.persistance.IDao;
import com.project.lakshmi.persistance.checklist.ChecklistStepDao;

@Service("checklistStepService")
public class ChecklistStepServiceImpl extends AbstractDatabaseService<ChecklistStep>
											implements ChecklistStepService {
	
	@Autowired
	private ChecklistStepDao checklistStepDao;

	@Override
	public IDao<ChecklistStep> getDao() {
		return checklistStepDao;
	}
}
