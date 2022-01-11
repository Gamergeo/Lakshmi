package com.project.lakshmi.business.checklist;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.DatabaseService;
import com.project.lakshmi.model.checklist.Checklist;

@Service
public interface ChecklistService extends DatabaseService<Checklist>  {

	/**
	 * @return the last checklist. If last
	 */
	Checklist getLastChecklist();

	Checklist createChecklist();

}
