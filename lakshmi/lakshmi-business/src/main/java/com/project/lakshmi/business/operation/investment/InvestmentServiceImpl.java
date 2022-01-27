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
	 * @return le prix total de l'investissement (prix de l'asset * quantité)
	 */
	@Override
	public Double getTotal(Investment investment, Instant date) {
		// On cherche le prix de l'asset associé
		Double price = assetService.getPrice(investment.getAsset(), date);
		
		// Le montant total est le prix * quantité
		return Math.abs(price * investment.getQuantity());
	}

}
