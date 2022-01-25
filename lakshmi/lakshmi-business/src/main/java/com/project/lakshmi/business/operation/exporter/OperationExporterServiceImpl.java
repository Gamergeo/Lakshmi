package com.project.lakshmi.business.operation.exporter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.investment.InvestmentService;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.InvestmentType;
import com.project.lakshmi.model.operation.investment.OperationInvestment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;
import com.project.lakshmi.technical.DateUtil;
import com.project.lakshmi.technical.FileUtils;
import com.project.lakshmi.technical.TechnicalConstants;

@Service("operationExporterService")
public class OperationExporterServiceImpl implements OperationExporterService {
	
	@Autowired
	InvestmentService investmentService;

	@Override
	public void exportOperations(List<Operation> operations) {
		FileUtils.removeFile(TechnicalConstants.FILE_OPERATION_EXPORT);
		
		writeHeader();
		
		for (Operation operation : operations) {
			// On ne peut ecrire que des investissements pour l'instant
			writeOperation(operation.asOperationInvestment());
		}
	}
	
	private void writeHeader() {
		writeOnFile(OperationExporterConstants.HEADER);
	}
	
	private void writeOperation(OperationInvestment investment) {
		// Si c'est un trade, il faut ecrire plusieurs opérations
		if (InvestmentType.TRADE.equals(investment.getInvestmentType())) {
			OperationInvestmentTrade trade = (OperationInvestmentTrade) investment;
			writeTrade(trade);
		}
	}
	
	/**
	 * Un trade se compose de trois opérations distinctes :
	 * 		Le principal
	 * 		L'équilibrage
	 * 		Les fees
	 * @param investment
	 */
	private void writeTrade(OperationInvestmentTrade trade) {
		
		// On récupère le montant total de l'opération
		Double totalPrice = investmentService.getTotal(trade.getInvestment(), trade.getDate());
		Double feePrice = 0d;
		
		if (trade.getFeeInvestment() != null) {
			feePrice = investmentService.getTotal(trade.getFeeInvestment(), trade.getDate());
		}
		
		// Opération principale
		writeTradePrincipal(trade, totalPrice, feePrice);
		
		// Opération d'equilibrage (attention, le prix total est avec les fees)
		writeTradeBalancing(trade, totalPrice + feePrice);
		
		// Fee
		writeTradeFee(trade, feePrice);
	}
	
	private void writeDate(OperationInvestment investment) {
		String date = DateUtil.formatDate(investment.getDate(), OperationExporterConstants.DATE_FORMAT);
		writeAttribute(date, OperationExporterConstants.PREFIX_DATE);
	}
	
	/**
	 * Champs en commun à tous les investissements
	 */
	private void writeInvestmentDefault(Investment investment) {
		// Quantité (toujours positif)
		Double.toString(Math.abs(investment.getQuantity()));
		Double quantity = Math.abs(investment.getQuantity());
		writeAttribute(toExactString(quantity), OperationExporterConstants.PREFIX_QUANTITY);
		
		// Asset
		String asset = investment.getAsset().getIsin();
		writeAttribute(asset, OperationExporterConstants.PREFIX_SECURITY);
	}
	
	/**
	 * Principal d'un trade
	 */
	private void writeTradePrincipal(OperationInvestmentTrade trade, Double totalPrice, Double feePrice) {
		writeDate(trade); // Date 
		writeInvestmentDefault(trade.getInvestment()); // quantité / asset

		// type (buy / sell)
		String type = getTradeType(trade.getInvestment()); 
		writeAttribute(type, OperationExporterConstants.PREFIX_ACTION); 
		
		writeAttribute(toExactString(totalPrice), OperationExporterConstants.PREFIX_TOTAL); // Prix total
		writeAttribute(toExactString(-feePrice), OperationExporterConstants.PREFIX_FEE); // fee (toujours négatif)
		
		// Le prix de l'asset est le total - fee / divisé par abs(quantité)
		Double price = (totalPrice - feePrice) / Math.abs(trade.getInvestment().getQuantity());
		writeAttribute(toExactString(price), OperationExporterConstants.PREFIX_PRICE);
		
		// Commentaire
		String memo = "";
		Double quantity = trade.getInvestment().getQuantity();
		
		if (quantity >= 0) {
			memo += "Achat " + toRoundedString(quantity);
		} else {
			memo += "Vente " + toRoundedString(-quantity);
		}
		
		memo += " " + trade.getInvestment().getAsset().getIsin();
		
		writeAttribute(memo, OperationExporterConstants.PREFIX_MEMO);
		
		writeOnFile(OperationExporterConstants.END_OPERATION);
	}
	
	/**
	 * Equilibrage d'un trade
	 */
	private void writeTradeBalancing(OperationInvestmentTrade trade, Double totalPrice) {
		writeDate(trade); // Date 
		writeInvestmentDefault(trade.getBalancingInvestment()); // quantité / asset

		// type (buy / sell)
		String type = getTradeType(trade.getBalancingInvestment()); 
		writeAttribute(type, OperationExporterConstants.PREFIX_ACTION); 
		
		writeAttribute(toExactString(totalPrice), OperationExporterConstants.PREFIX_TOTAL); // Prix total
		writeAttribute("0", OperationExporterConstants.PREFIX_FEE); // Les fees sont toujours nul dans le balancing
		
		// Le prix de l'asset est le total / divisé par quantité
		Double price = totalPrice / Math.abs(trade.getBalancingInvestment().getQuantity());
		writeAttribute(toExactString(price), OperationExporterConstants.PREFIX_PRICE);
		
		// Commentaire
		String memo = "Equilibrage " + toRoundedString(trade.getBalancingInvestment().getQuantity()) + " " 
				+ trade.getBalancingInvestment().getAsset().getIsin();
		writeAttribute(memo, OperationExporterConstants.PREFIX_MEMO);
		
		writeOnFile(OperationExporterConstants.END_OPERATION);
	}
	
	/**
	 * fee d'un trade
	 */
	private void writeTradeFee(OperationInvestmentTrade trade, Double feePrice) {
		
		// Pa sde fee, on ne fait rien
		if (trade.getFeeInvestment() == null) {
			return;
		}
		
		writeDate(trade); // Date 
		writeInvestmentDefault(trade.getFeeInvestment()); // quantité / asset

		// Un fee est toujours un sell
		writeAttribute(OperationExporterConstants.TYPE_SELL, OperationExporterConstants.PREFIX_ACTION); 
		
		writeAttribute(toExactString(feePrice), OperationExporterConstants.PREFIX_TOTAL); // Prix total
		writeAttribute("0", OperationExporterConstants.PREFIX_FEE); // Les fees sont toujours nul dans le fee
		
		// Le prix de l'asset est le fee / divisé par quantité
		Double price = feePrice / Math.abs(trade.getFeeInvestment().getQuantity());
		writeAttribute(toExactString(price), OperationExporterConstants.PREFIX_PRICE);
		
		// Commentaire
		String memo = "Frais " 
				+ trade.getFeeInvestment().getAsset().getIsin();
		writeAttribute(memo, OperationExporterConstants.PREFIX_MEMO);
		
		writeOnFile(OperationExporterConstants.END_OPERATION);
	}
	
	/**
	 * Cout des fee, attention, ils sont toujours négatif
	 */
//	private void writeFees(Double feePrice) {
//		Double costFee = -feePrice;
//	}

	/**
	 * @return le type d'investissement dans le cas d'un TRADE uniquement
	 */
	private String getTradeType(Investment invesment) {
		// Dans le cas d'un trade, on ecrit sell ou buy suivant la quantité
		if (invesment.getQuantity() >= 0) {
			return OperationExporterConstants.TYPE_BUY;
		} else {
			return OperationExporterConstants.TYPE_SELL;
		}
	}
	
	private void writeAttribute(String attribute, String prefix) {
		writeOnFile(prefix + attribute);
	}
	
	private void writeOnFile(String message) {
		FileUtils.writeOnFileAndEndLine(TechnicalConstants.FILE_OPERATION_EXPORT, message);
	}
	
	private String toExactString(double d) {
		DecimalFormat format = new DecimalFormat("#.############");
		format.setRoundingMode(RoundingMode.HALF_DOWN);
		
		return format.format(d);
	}
	
	private String toRoundedString(double d) {
		DecimalFormat format = new DecimalFormat("#.##");
		format.setRoundingMode(RoundingMode.HALF_DOWN);
		
		return format.format(d);
	}
}
