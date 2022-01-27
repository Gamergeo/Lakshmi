package com.project.lakshmi.business.operation.importer.kucoin.extractor.trade;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.business.operation.importer.kucoin.OperationImporterKucoinConstants;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;
import com.project.lakshmi.technical.ApplicationException;
import com.project.lakshmi.technical.DateUtil;

@Service("operationImporterKucoinTradeExtractorService")
public class OperationImporterKucoinTradeExtractorServiceImpl implements OperationImporterKucoinTradeExtractorService {
	
	@Autowired
	AssetService assetService;
	
	private List<String> getValues(String line) {
		return Arrays.asList(line.split(OperationImporterKucoinConstants.SEPARATOR));
	}
	
	@Override
	public Instant getDate(String line) {
		List<String> values = getValues(line);
		String rawDate = values.get(OperationImporterKucoinConstants.TRADE_FILE.DATE_INDEX);
		
		return DateUtil.extractDate(rawDate, OperationImporterKucoinConstants.TRADE_FILE.DATE_FORMAT, OperationImporterKucoinConstants.DATE_ZONE);
	}
	
	private String getPair(List<String> values) {
		return values.get(OperationImporterKucoinConstants.TRADE_FILE.PAIR_INDEX);
	}
	
	@Override
	public Asset getAsset(String line) {
		List<String> values = getValues(line);
		String isin = getPair(values).split(OperationImporterKucoinConstants.TRADE_FILE.PAIR_SEPARATOR)[0];
		
		Asset asset = assetService.findByIsinIfAny(isin);
		
		// On a pas trouvé d'isin, on renvoie en erreur
		if (asset == null) {
			throw new ApplicationException("Asset non trouvé " + isin);
		}
		
		return asset;
	}
	
	@Override
	public Asset getBalancingAsset(String line) {
		List<String> values = getValues(line);
		String isin = getPair(values).split(OperationImporterKucoinConstants.TRADE_FILE.PAIR_SEPARATOR)[1];
		
		Asset asset = assetService.findByIsinIfAny(isin);
		
		// On a pas trouvé d'isin, on renvoie en erreur
		if (asset == null) {
			throw new ApplicationException("Asset non trouvé " + isin);
		}
		
		return asset;
	}
	
	@Override
	public  Investment getMainInvestment(String line) {
		Asset asset = getAsset(line);
		Double quantity = getMainQuantity(line);
		
		Investment investment = new Investment(asset, quantity);
		
		return investment;
	}
	
	@Override
	public Investment getBalancingInvestment(String line) {
		Asset asset = getBalancingAsset(line);
		Double quantity = getBalancingQuantity(line);
		
		Investment investment = new Investment(asset, quantity);
		
		return investment;
	}
	
	private boolean isBuy(String line) {
		List<String> values = getValues(line);
		return OperationImporterKucoinConstants.TRADE_FILE.TYPE_BUY.equals(
				values.get(OperationImporterKucoinConstants.TRADE_FILE.TYPE_INDEX));
	}
	
	// La quantité est positive ou négative suivant si c'est un sell ou un buy
	private Double getMainQuantity(String line) {
		List<String> values = getValues(line);
		Double quantity = Double.parseDouble(values.get(OperationImporterKucoinConstants.TRADE_FILE.MAIN_QUANTITY_INDEX));
		
		if (isBuy(line)) {
			return quantity;
		} else {
			return -quantity;
		}
	}
	
	private Double getBalancingQuantity(String line) {
		List<String> values = getValues(line);
		Double quantity = Double.parseDouble(values.get(OperationImporterKucoinConstants.TRADE_FILE.BALANCING_QUANTITY_INDEX));
		
		if (isBuy(line)) {
			return -quantity;
		} else {
			return quantity;
		}
	}
	
	@Override
	public OperationInvestmentTrade getOperation(String line) {
		OperationInvestmentTrade operation = new OperationInvestmentTrade();
		
		operation.setDate(getDate(line));
		operation.setInvestment(getMainInvestment(line));
		operation.setBalancingInvestment(getBalancingInvestment(line));
		
		// Dans le cas du KCS, on init le fee directement sinon on utilisera le feeFile
		operation.setFeeInvestment(getFeeInvestment(line));
		
		return operation;
	}
	
	// Dans le cas ou les fees sont déja en KCS, on utilise pas le feeFile mais celui ci
	@Override
	public Investment getFeeInvestment(String line) {
		List<String> values = getValues(line);
		String feeAsset = values.get(OperationImporterKucoinConstants.TRADE_FILE.FEE_ASSET);
		
		if (!feeAsset.equals("KCS")) {
			return null;
		}
		
		Asset asset = assetService.findByIsin("KCS");
		Double quantity = Double.valueOf(values.get(OperationImporterKucoinConstants.TRADE_FILE.FEE_ASSET));
			
		Investment investment = new Investment(asset, quantity);
			
		return investment;
	}
}
