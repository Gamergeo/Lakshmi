package com.project.lakshmi.business.operation.exporter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.exporter.multitrade.OperationInvestmentMultiTradeExporterService;
import com.project.lakshmi.business.operation.exporter.trade.OperationInvestmentTradeExporterService;
import com.project.lakshmi.business.operation.investment.InvestmentService;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.InvestmentType;
import com.project.lakshmi.model.operation.investment.OperationInvestment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentMultiTrade;
import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;
import com.project.lakshmi.technical.DateUtil;
import com.project.lakshmi.technical.FileUtils;
import com.project.lakshmi.technical.TechnicalConstants;

@Service("operationExporterService")
public class OperationExporterServiceImpl implements OperationExporterService {
	
	@Autowired
	InvestmentService investmentService;
	
	@Autowired
	OperationInvestmentTradeExporterService operationInvestmentTradeExporterService;
	
	@Autowired
	OperationInvestmentMultiTradeExporterService operationInvestmentMultiTradeExporterService;

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
			operationInvestmentTradeExporterService.writeOperation(trade);
		} else if (InvestmentType.MULTI_TRADE.equals(investment.getInvestmentType())) {
			OperationInvestmentMultiTrade multiTrade = (OperationInvestmentMultiTrade) investment;
			operationInvestmentMultiTradeExporterService.writeOperation(multiTrade);
		} else {
			throw new NotYetImplementedException("export pas encore implémenté " + investment.getInvestmentType());
		}
	}
	
	protected void write(Instant date, Investment investment, String type, Double totalPrice, Double feePrice, String memo) {
		// Date
		String formattedDate = DateUtil.formatDate(date, OperationExporterConstants.DATE_FORMAT);
//		String formattedDate = DateUtil.formatDate(Instant.now(), OperationExporterConstants.DATE_FORMAT);
		writeAttribute(formattedDate, OperationExporterConstants.PREFIX_DATE);
		
		// Quantité (toujours positif)
		Double.toString(Math.abs(investment.getQuantity()));
		Double quantity = Math.abs(investment.getQuantity());
		writeAttribute(toExactString(quantity), OperationExporterConstants.PREFIX_QUANTITY);
		
		// Asset
		String asset = investment.getAsset().getIsin();
		writeAttribute(asset, OperationExporterConstants.PREFIX_SECURITY);
		
		// Type
		writeAttribute(type, OperationExporterConstants.PREFIX_ACTION);
		
		// Prix total de l'opération
		writeAttribute(toExactString(totalPrice), OperationExporterConstants.PREFIX_TOTAL);
		
		// fee (toujours positif : peut etre un bug de l'import ms comptes)
		writeAttribute(toExactString(feePrice), OperationExporterConstants.PREFIX_FEE); 
		
		// Le prix de l'asset est le total - fee / divisé par abs(quantité)
		Double price = (totalPrice - Math.abs(feePrice)) / Math.abs(investment.getQuantity());
		writeAttribute(toExactString(price), OperationExporterConstants.PREFIX_PRICE);
		
		// Memo
		writeAttribute(memo, OperationExporterConstants.PREFIX_MEMO);
		
		writeOnFile(OperationExporterConstants.END_OPERATION);
	}
	
	protected void writeAttribute(String attribute, String prefix) {
		writeOnFile(prefix + attribute);
	}
	
	protected void writeOnFile(String message) {
		FileUtils.writeOnFileAndEndLine(TechnicalConstants.FILE_OPERATION_EXPORT, message);
	}
	
	protected String toExactString(double d) {
		DecimalFormat format = new DecimalFormat("#.############");
		format.setRoundingMode(RoundingMode.HALF_DOWN);
		
		return format.format(d);
	}
	
	protected String toRoundedString(double d) {
		DecimalFormat format = new DecimalFormat("#.##");
		format.setRoundingMode(RoundingMode.HALF_DOWN);
		
		return format.format(d);
	}
}
