package com.project.lakshmi.business.operation.importer.binance.extractor;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.Asset;
import com.project.lakshmi.model.operation.investment.Investment;

@Service
public interface OperationImporterBinanceExtractorService {

	String getInvestmentType(String line);

	boolean isTrade(String line);

	/**
	 * @param line
	 * @return true s'il s'agit de la main (left) part d'un trade
	 */
	boolean isTradeMain(String line);

	boolean isTradeBalancing(String line);

	boolean isTradeFee(String line);

	Double getQuantity(String line);

	/**
	 * TODO
	 */
	@Deprecated
	Asset getAsset(String line);

	Investment getInvestment(String line);

	Date getDate(String line);

}
