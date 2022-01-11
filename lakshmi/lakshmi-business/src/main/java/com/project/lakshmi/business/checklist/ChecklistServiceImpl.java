package com.project.lakshmi.business.checklist;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.model.checklist.Checklist;
import com.project.lakshmi.model.checklist.ChecklistStep;
import com.project.lakshmi.persistance.IDao;
import com.project.lakshmi.persistance.checklist.ChecklistDao;

@Service("checklistService")
public class ChecklistServiceImpl extends AbstractDatabaseService<Checklist> implements ChecklistService {
	
	@Autowired
	private ChecklistDao checklistDao;
	
	@Autowired
	private ChecklistStepService checklistStepService;
	
	@Override
	@Transactional
	public Checklist createChecklist() {
		
		Checklist checklist = new Checklist();
		checklist.setCreationDate(LocalDateTime.now());
		
		checklist.setChecklistSteps(checklistStepService.createSteps());
		
		saveOrUpdate(checklist);
		
		return checklist;
	}
	
	@Override
	@Transactional
	public void saveOrUpdate(Checklist checklist) {
		
		for (ChecklistStep checklistStep : checklist.getChecklistSteps()) {

			if (checklistStep.getId() == null) {
				checklistStep.setChecklist(checklist);

//				checklistStepService.saveOrUpdate(checklistStep);
			}
		}
		
		super.saveOrUpdate(checklist);
	}
	
	@Override
	public Checklist getLastChecklist() {
		return null;
		
	}

	@Override
	public IDao<Checklist> getDao() {
		return checklistDao;
	}
}
