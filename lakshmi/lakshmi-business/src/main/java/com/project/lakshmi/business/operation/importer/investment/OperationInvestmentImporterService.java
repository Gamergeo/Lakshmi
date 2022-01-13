package com.project.lakshmi.business.operation.importer.investment;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.Importer.OperationImporterOrigin;

@Service
public interface OperationInvestmentImporterService {

	Operation readLine(OperationImporterOrigin origin, String line);


}
