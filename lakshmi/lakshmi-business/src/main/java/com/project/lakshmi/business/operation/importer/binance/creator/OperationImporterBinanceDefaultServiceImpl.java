package com.project.lakshmi.business.operation.importer.binance.creator;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.binance.extractor.OperationImporterBinanceExtractorService;
import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.investment.OperationInvestment;

@Service("operationImporterBinanceDefaultService")
public class OperationImporterBinanceDefaultServiceImpl implements OperationImporterBinanceDefaultService {
	
	@Autowired
	private OperationImporterBinanceExtractorService operationImporterBinanceExtractorService;
	
	@Override
	public Operation importNextOperation(RawTextFile rawFile) {
		OperationInvestment operation = new OperationInvestment();
		
		String line = rawFile.getAndRemoveNext();
		
		Instant operationDate = operationImporterBinanceExtractorService.getDate(line);
		operation.setDate(operationDate);
		
		// On met en place les infos correspondantes à la ligne trouvée
		operation.setInvestment(operationImporterBinanceExtractorService.getInvestment(line));
		operation.setInvestmentType(operationImporterBinanceExtractorService.getInvestmentType(line));
		
		return operation;
	}
}
