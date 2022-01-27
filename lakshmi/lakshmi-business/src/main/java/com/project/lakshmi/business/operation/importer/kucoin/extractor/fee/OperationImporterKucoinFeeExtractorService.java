package com.project.lakshmi.business.operation.importer.kucoin.extractor.fee;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.investment.Investment;

@Service
public interface OperationImporterKucoinFeeExtractorService {

	Instant getDate(String line);

	Investment getInvestment(String line);

	boolean isFee(String line);

}
