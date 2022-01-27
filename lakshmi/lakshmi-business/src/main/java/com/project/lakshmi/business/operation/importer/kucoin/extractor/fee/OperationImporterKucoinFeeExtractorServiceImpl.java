package com.project.lakshmi.business.operation.importer.kucoin.extractor.fee;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.business.operation.importer.kucoin.OperationImporterKucoinConstants;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.technical.ApplicationException;
import com.project.lakshmi.technical.DateUtil;

@Service("operationImporterKucoinFeeExtractorService")
public class OperationImporterKucoinFeeExtractorServiceImpl implements OperationImporterKucoinFeeExtractorService {
	
	@Autowired
	AssetService assetService;
	
	private List<String> getValues(String line) {
		return Arrays.asList(line.split(OperationImporterKucoinConstants.SEPARATOR));
	}
	
	@Override
	public Instant getDate(String line) {
		String rawDate = getValues(line).get(OperationImporterKucoinConstants.FEE_FILE.DATE_INDEX);
		
		return DateUtil.extractDate(rawDate, OperationImporterKucoinConstants.FEE_FILE.DATE_FORMAT, OperationImporterKucoinConstants.DATE_ZONE);
	}
	
	private Asset getAsset() {
		Asset asset = assetService.findByIsinIfAny("KCS");
		
		// On a pas trouvé d'isin, on renvoie en erreur
		if (asset == null) {
			throw new ApplicationException("Asset non trouvé KCS");
		}
		
		return asset;
	}
	
	@Override
	public Investment getInvestment(String line) {
		Asset asset = getAsset();
		Double quantity = getQuantity(line);
		
		Investment investment = new Investment(asset, quantity);
		
		return investment;
	}
	
	private Double getQuantity(String line) {
		return Double.parseDouble(getValues(line).get(OperationImporterKucoinConstants.FEE_FILE.QUANTITY_INDEX));
	}
	
	@Override
	public boolean isFee(String line) {
		String type = getValues(line).get(OperationImporterKucoinConstants.FEE_FILE.TYPE_INDEX);
		
		return OperationImporterKucoinConstants.FEE_FILE.TYPE_FEE.equals(type);
	}
	
}
