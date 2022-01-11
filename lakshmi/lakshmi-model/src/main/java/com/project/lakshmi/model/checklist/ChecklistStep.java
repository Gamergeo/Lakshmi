package com.project.lakshmi.model.checklist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.project.lakshmi.model.DatabaseName;

@Entity(name = DatabaseName.CHECKLIST_STEP.TABLE)
@Table(name = DatabaseName.CHECKLIST_STEP.TABLE)
public class ChecklistStep implements Serializable {

	private static final long serialVersionUID = -1864542003746307771L;

	@Id
	@Column(name=DatabaseName.ID)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name=DatabaseName.CHECKLIST_STEP.CHECKLIST_ID, referencedColumnName = DatabaseName.ID)
	private Checklist checklist;
	
	@ManyToOne
	@JoinColumn(name=DatabaseName.CHECKLIST_STEP.CHECKLIST_STEP_INFOS_ID, referencedColumnName = DatabaseName.ID)
	private ChecklistStepInfos checklistStepInfos;

	@Column(name=DatabaseName.CHECKLIST_STEP.STARTED)
	protected boolean started;
	
	@Column(name=DatabaseName.CHECKLIST_STEP.ENDED)
	protected boolean ended;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Checklist getChecklist() {
		return checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}

	public ChecklistStepInfos getChecklistStepInfos() {
		return checklistStepInfos;
	}

	public void setChecklistStepInfos(ChecklistStepInfos checklistStepInfos) {
		this.checklistStepInfos = checklistStepInfos;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}
	
}
