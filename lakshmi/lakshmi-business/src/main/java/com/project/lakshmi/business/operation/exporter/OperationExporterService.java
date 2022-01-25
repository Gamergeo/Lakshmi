package com.project.lakshmi.business.operation.exporter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.Operation;

@Service
public interface OperationExporterService {
	
	void exportOperations(List<Operation> operations);
	

}
