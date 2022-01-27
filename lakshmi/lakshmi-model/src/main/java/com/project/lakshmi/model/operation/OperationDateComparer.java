package com.project.lakshmi.model.operation;

import java.util.Comparator;

public class OperationDateComparer implements Comparator<Operation> {
	
	@Override
	public int compare(Operation o1, Operation o2) {
		
	    int comparison = o1.getDate().compareTo(o2.getDate());
	    
	    return comparison;
	}
}