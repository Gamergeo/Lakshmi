package com.project.lakshmi.model.checklist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.project.lakshmi.model.DatabaseName;

@Entity(name = DatabaseName.CHECKLIST_STEP_INFOS.TABLE)
@Table(name = DatabaseName.CHECKLIST_STEP_INFOS.TABLE)
public class ChecklistStepInfos implements Serializable {

	private static final long serialVersionUID = -1864542003746307771L;

	@Id
	@Column(name=DatabaseName.ID)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;

	@Column(name=DatabaseName.CHECKLIST_STEP_INFOS.ORDER)
	private Integer order;

	@Column(name=DatabaseName.CHECKLIST_STEP_INFOS.LABEL)
	private String label;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
