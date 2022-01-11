package com.project.lakshmi.business.checklist;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.DatabaseService;
import com.project.lakshmi.model.checklist.ChecklistStepInfos;

@Service
public interface ChecklistStepInfosService extends DatabaseService<ChecklistStepInfos>{

	/** 
	 * Find all step infos ordered
	 */
	public List<ChecklistStepInfos> findAll();
}
