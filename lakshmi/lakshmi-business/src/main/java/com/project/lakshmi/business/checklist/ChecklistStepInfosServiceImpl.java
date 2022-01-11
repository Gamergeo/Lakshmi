package com.project.lakshmi.business.checklist;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.model.DatabaseName;
import com.project.lakshmi.model.checklist.ChecklistStepInfos;
import com.project.lakshmi.persistance.IDao;
import com.project.lakshmi.persistance.checklist.ChecklistStepInfosDao;

@Service("checklistServiceStepInfos")
public class ChecklistStepInfosServiceImpl extends AbstractDatabaseService<ChecklistStepInfos>
											implements ChecklistStepInfosService {
	
	@Autowired
	private ChecklistStepInfosDao checklistStepInfosDao;
	
	@Override
	@Transactional
	public List<ChecklistStepInfos> findAll() {
		return findAllOrderBy(DatabaseName.CHECKLIST_STEP_INFOS.ORDER);
	}

	@Override
	public IDao<ChecklistStepInfos> getDao() {
		return checklistStepInfosDao;
	}
}
