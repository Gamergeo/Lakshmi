package com.project.lakshmi.business.operation.importer.kucoin.extractor.fee;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;

@Service
public interface OperationImporterKucoinFeeExtractorService {

	OperationInvestmentTrade getFeeOperation(String line);

}
