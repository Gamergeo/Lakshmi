package com.project.lakshmi.business.operation.importer.binance.extractor;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.Asset;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.InvestmentType;

@Service
public interface OperationImporterBinanceExtractorService {

	InvestmentType getInvestmentType(String line);

	/**
	 * @param line
	 * @return true s'il s'agit de la main (left) part d'un trade
	 */
	boolean isTradeMain(String line);
	
	/**
	 * @param line
	 * @return true s'il s'agit de la main (left) part d'un multi trade
	 */
	boolean isMultiTradeMain(String line);
	
	/**
	 * @param line
	 * @return true s'il s'agit de la balancing (right) part d'un trade 
	 */
	boolean isTradeBalancing(String line);
	
	/**
	 * @param line
	 * @return true s'il s'agit de la balancing (right) part d'un multitrade
	 */
	boolean isMultiTradeBalancing(String line);
	
	/**
	 * @param line
	 * @return true s'il s'agit du fee part d'un trade
	 */
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
