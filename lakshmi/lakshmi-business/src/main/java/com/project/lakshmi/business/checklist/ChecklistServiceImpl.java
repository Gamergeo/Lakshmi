package com.project.lakshmi.business.checklist;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.model.checklist.Checklist;
import com.project.lakshmi.model.checklist.ChecklistStep;
import com.project.lakshmi.model.checklist.Checklist_;
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
	public Checklist getLastChecklist() {
		
		List<Checklist> allChecklists = checklistDao.findAllOrderedBy(Checklist_.END_DATE);
		
		// Pas de checklist trouvé, il faut en créé une
		if (allChecklists.isEmpty()) {
			return createChecklist();
		}
		
		return allChecklists.get(allChecklists.size() - 1);
	}
	
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
			}
		}
		
		super.saveOrUpdate(checklist);
	}

	@Override
	public IDao<Checklist> getDao() {
		return checklistDao;
	}
}
