package com.project.lakshmi.business.operation.importer.binance.extractor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.binance.OperationImporterBinanceConstants;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.OldAsset;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.InvestmentType;
import com.project.lakshmi.technical.DateUtil;

@Service("operationImporterBinanceExtractorService")
public class OperationImporterBinanceExtractorServiceImpl implements OperationImporterBinanceExtractorService {
	
	private List<String> getValues(String line) {
		return Arrays.asList(line.split(OperationImporterBinanceConstants.SEPARATOR));
	}
	
	private String getRawInvestmentType(String line) {
		List<String> values = getValues(line);
		
		return values.get(OperationImporterBinanceConstants.INVESTMENT_TYPE_INDEX);
	}
	
	@Override 
	public boolean isIgnored(String line) {
		String rawInvestmentType = getRawInvestmentType(line);
		if (OperationImporterBinanceConstants.INVESTMENT_TYPE_IGNORED.contains(rawInvestmentType)) {
			return true;
		} 
		
		return false;
	}
	
	@Override
	public InvestmentType getInvestmentType(String line) {
		String rawInvestmentType = getRawInvestmentType(line);
		
		if (OperationImporterBinanceConstants.INVESTMENT_TYPE_TRADE.contains(rawInvestmentType)) {
			return InvestmentType.TRADE;
		} else if (OperationImporterBinanceConstants.INVESTMENT_TYPE_MULTI_TRADE.equals(rawInvestmentType)) {
			return InvestmentType.MULTI_TRADE;
		} else if (OperationImporterBinanceConstants.INVESTMENT_TYPE_STACKING.contains(rawInvestmentType)) {
			return InvestmentType.STACKING;
		} else if (OperationImporterBinanceConstants.INVESTMENT_TYPE_WITHDRAW.equals(rawInvestmentType)) {
			return InvestmentType.WITHDRAW;
		} else if (OperationImporterBinanceConstants.INVESTMENT_TYPE_DEPOSIT.equals(rawInvestmentType)) {
			return InvestmentType.DEPOSIT;
		}
		
		throw new NotYetImplementedException("Pas encore implementé" + line);
	}
	
	@Override
	public Asset getAsset(String line) {
		List<String> values = getValues(line);
		
		String assetName = values.get(OperationImporterBinanceConstants.ASSET_INDEX);
		
		// TODO : properly create asset
		Asset asset = new Asset();
		asset.setIsin(assetName);
		
		return asset;
	}
	
	@Override
	public Investment getInvestment(String line) {
		Asset asset = getAsset(line);
		Double quantity = getQuantity(line);
		
		Investment investment = new Investment(asset, quantity);
		
		return investment;
	}
	
	@Override
	public Double getQuantity(String line) {
		List<String> values = getValues(line);
		
		return Double.parseDouble(values.get(OperationImporterBinanceConstants.QUANTITY_INDEX));
	}
	
	@Override
	public Date getDate(String line) {
		List<String> values = getValues(line);
		String rawDate = values.get(OperationImporterBinanceConstants.DATE_INDEX);
		
		return DateUtil.formatDate(rawDate, OperationImporterBinanceConstants.DATE_FORMAT);
	}

	/**
	 * Dans le cas d'un trade, deux cas : 
	 * Soit c'est un buy et la quantité est positive
	 * Soit c'est un sell et la quantité est négative
	 */
	@Override
	public boolean isTradeMain(String line) {
		
		if (!InvestmentType.TRADE.equals(getInvestmentType(line))) {
			return false;
		}
		
		String investmentType = getRawInvestmentType(line);
		Double quantity = getQuantity(line);
		
		if (OperationImporterBinanceConstants.INVESTMENT_TYPE_BUY.equals(investmentType) &&
				quantity >= 0) {
			return true;
		} else if (OperationImporterBinanceConstants.INVESTMENT_TYPE_SELL.equals(investmentType) &&
				quantity <= 0) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Dans le cas d'un multitrade, le BNB est toujours la left part
	 */
	@Override
	public boolean isMultiTradeMain(String line) {
		
		if (!InvestmentType.MULTI_TRADE.equals(getInvestmentType(line))) {
			return false;
		}
		
		// TODO : avoir un asset propre
		OldAsset asset = new OldAsset();
		asset.setName(OperationImporterBinanceConstants.ASSET_BNB);
		
		return asset.equals(getAsset(line));
	}	
	
	/**
	 *  Si c'est un trade, mais pas un main ou un fee, c'est un balancing
	 */
	@Override
	public boolean isTradeBalancing(String line) {
		
		if (!InvestmentType.TRADE.equals(getInvestmentType(line))) {
			return false;
		}
		
		if (!isTradeMain(line) && !isTradeFee(line)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 *  Si c'est un trade, mais pas un main c'est un balancing
	 */
	@Override
	public boolean isMultiTradeBalancing(String line) {
		
		if (!InvestmentType.MULTI_TRADE.equals(getInvestmentType(line))) {
			return false;
		}
		
		// Si c'est un trade, mais pas un main ou un fee, c'est un balancing
		if (!isMultiTradeMain(line)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param line
	 * @return true s'il s'agit du fee part d'un trade
	 */
	@Override
	public boolean isTradeFee(String line) {
		String investmentType = getRawInvestmentType(line);
		
		if (OperationImporterBinanceConstants.INVESTMENT_TYPE_FEE.equals(investmentType)) {
			return true;
		}
		
		return false;
	}
}
