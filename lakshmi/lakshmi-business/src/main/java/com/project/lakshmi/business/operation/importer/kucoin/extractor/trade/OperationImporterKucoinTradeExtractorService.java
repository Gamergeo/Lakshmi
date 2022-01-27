package com.project.lakshmi.business.operation.importer.kucoin.extractor.trade;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;

@Service
public interface OperationImporterKucoinTradeExtractorService {

	OperationInvestmentTrade getOperation(String line);

	Instant getDate(String line);

	Asset getBalancingAsset(String line);

	Asset getAsset(String line);

	Investment getMainInvestment(String line);

	Investment getBalancingInvestment(String line);

	Investment getFeeInvestment(String line);

}
