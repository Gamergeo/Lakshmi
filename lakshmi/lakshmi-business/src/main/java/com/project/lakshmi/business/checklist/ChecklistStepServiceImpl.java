package com.project.lakshmi.business.checklist;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.model.checklist.ChecklistStep;
import com.project.lakshmi.model.checklist.ChecklistStepInfos;
import com.project.lakshmi.persistance.IDao;
import com.project.lakshmi.persistance.checklist.ChecklistStepDao;

@Service("checklistStepService")
public class ChecklistStepServiceImpl extends AbstractDatabaseService<ChecklistStep>
											implements ChecklistStepService {
	
	@Autowired
	private ChecklistStepDao checklistStepDao;
	
	@Autowired
	private ChecklistStepInfosService checklistStepInfosService;
	
	@Override
	@Transactional
	public List<ChecklistStep> createSteps() {
		
		List<ChecklistStepInfos> checklistStepInfosList = checklistStepInfosService.findAll();
		
		List<ChecklistStep> checklistStepList = new ArrayList<ChecklistStep>();
		ChecklistStep checklistStep;
		
		for (ChecklistStepInfos checklistStepInfos : checklistStepInfosList) {
			
			checklistStep = new ChecklistStep();
			checklistStep.setChecklistStepInfos(checklistStepInfos);
			
			checklistStepList.add(checklistStep);
		}
		
		return checklistStepList;
	}

	@Override
	public IDao<ChecklistStep> getDao() {
		return checklistStepDao;
	}
	
	
}
