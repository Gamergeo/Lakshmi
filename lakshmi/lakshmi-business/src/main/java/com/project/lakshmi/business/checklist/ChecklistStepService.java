package com.project.lakshmi.business.checklist;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.DatabaseService;
import com.project.lakshmi.model.checklist.ChecklistStep;

@Service
public interface ChecklistStepService extends DatabaseService<ChecklistStep>{

	List<ChecklistStep> createSteps();

}
