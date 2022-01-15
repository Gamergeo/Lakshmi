package com.project.lakshmi.business.operation.importer.binance.extractor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.binance.OperationImporterBinanceConstants;
import com.project.lakshmi.model.Asset;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.technical.DateUtils;

@Service("operationImporterBinanceExtractorService")
public class OperationImporterBinanceExtractorServiceImpl implements OperationImporterBinanceExtractorService {
	
	private List<String> getValues(String line) {
		return Arrays.asList(line.split(OperationImporterBinanceConstants.SEPARATOR));
	}
	
	@Override
	public String getInvestmentType(String line) {
		List<String> values = getValues(line);
		
		return values.get(OperationImporterBinanceConstants.INVESTMENT_TYPE_INDEX);
	}	
	
	@Override
	public Asset getAsset(String line) {
		List<String> values = getValues(line);
		
		String assetName = values.get(OperationImporterBinanceConstants.ASSET_INDEX);
		
		// TODO : properly create asset
		Asset asset = new Asset();
		asset.setName(assetName);
		
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
		
		return DateUtils.formatDate(rawDate, OperationImporterBinanceConstants.DATE_FORMAT);
	}

	@Override
	public boolean isTrade(String line) {
		String investmentType = getInvestmentType(line);
		
		if (OperationImporterBinanceConstants.INVESTMENT_TYPE_TRADE.contains(investmentType)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isTradeMain(String line) {
		
		String investmentType = getInvestmentType(line);
		Double quantity = getQuantity(line);
		
		// Deux cas : soit c'est un buy et la quantité est positive
		// Soit c'est un sell et la quantité est négative
		
		if (OperationImporterBinanceConstants.INVESTMENT_TYPE_BUY.equals(investmentType) &&
				quantity >= 0) {
			return true;
		} else if (OperationImporterBinanceConstants.INVESTMENT_TYPE_SELL.equals(investmentType) &&
				quantity <= 0) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isTradeBalancing(String line) {
		
		// Si c'est un trade, mais pas un main ou un fee, c'est un balancing
		if (isTrade(line) && !isTradeMain(line) && !isTradeFee(line)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isTradeFee(String line) {
		String investmentType = getInvestmentType(line);
		
		if (OperationImporterBinanceConstants.INVESTMENT_TYPE_FEE.equals(investmentType)) {
			return true;
		}
		
		return false;
	}
	
}
