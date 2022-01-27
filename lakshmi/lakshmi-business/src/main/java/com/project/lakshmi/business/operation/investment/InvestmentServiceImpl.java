package com.project.lakshmi.business.operation.investment;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.model.operation.investment.Investment;

@Service("investmentService")
public class InvestmentServiceImpl implements InvestmentService {

	@Autowired
	AssetService assetService;
	
	/**
	 * @return le prix total de l'investissement (prix de l'asset * quantit�)
	 */
	@Override
	public Double getTotal(Investment investment, Instant date) {
		
		// Cas sp�cial des conversions sp�cials kucoin eth (ETH2)
		// L'eth2 n'existe pas, donc le prix (en ETH) est defini directement dans l'investissement
		if (investment.isSpecialConversion()) {
			
			// On cerche le prix de la currency
			Double price = assetService.getPrice(investment.getSpecialConversionCurrency(), date);
			
			// Le montant total est le prix * quantit� * rate
			return Math.abs(price * investment.getQuantity() * investment.getSpecialConversionRate());
			
		} else {
			
			// On cherche le prix de l'asset associ�
			Double price = assetService.getPrice(investment.getAsset(), date);
			
			// Le montant total est le prix * quantit�
			return Math.abs(price * investment.getQuantity());
		}
		
		

	}

}
