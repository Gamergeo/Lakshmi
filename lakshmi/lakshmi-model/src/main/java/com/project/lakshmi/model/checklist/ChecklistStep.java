package com.project.lakshmi.model.checklist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	@Column(name=DatabaseName.CHECKLIST_STEP.LABEL)
	private String label;

	@Column(name=DatabaseName.CHECKLIST_STEP.ADVICE)
	private String advice;
	
	@Column(name=DatabaseName.CHECKLIST_STEP.DONE)
	private boolean done;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
}
