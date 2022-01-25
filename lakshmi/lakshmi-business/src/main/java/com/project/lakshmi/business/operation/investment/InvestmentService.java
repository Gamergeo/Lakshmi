package com.project.lakshmi.business.operation.investment;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.investment.Investment;

@Service
public interface InvestmentService {
	
	/**
	 * @return le prix total de l'investissement (prix de l'asset * quantité)
	 */
	public Double getTotal(Investment investment, Instant date);
}
