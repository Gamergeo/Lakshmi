package com.project.lakshmi.model.checklist;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.project.lakshmi.model.DatabaseName;

@Entity(name = DatabaseName.CHECKLIST.TABLE)
@Table(name = DatabaseName.CHECKLIST.TABLE)
public class Checklist implements Serializable {

	private static final long serialVersionUID = -1864542003746307771L;

	@Id
	@Column(name=DatabaseName.ID)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;

	@Column(name=DatabaseName.CHECKLIST.CREATION_DATE)
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime creationDate;

	@Column(name=DatabaseName.CHECKLIST.END_DATE)
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime endDate;
	
//	@OneToMany(cascade = { CascadeType.ALL})
//	@JoinColumn(name=DatabaseName.CHECKLIST_STEP.CHECKLIST_STEP_INFOS_ID, referencedColumnName = DatabaseName.ID)
	@OneToMany(mappedBy="checklist", cascade = CascadeType.ALL)
	private List<ChecklistStep> checklistSteps = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public List<ChecklistStep> getChecklistSteps() {
		return checklistSteps;
	}

	public void setChecklistSteps(List<ChecklistStep> checklistSteps) {
		this.checklistSteps = checklistSteps;
	}
}
