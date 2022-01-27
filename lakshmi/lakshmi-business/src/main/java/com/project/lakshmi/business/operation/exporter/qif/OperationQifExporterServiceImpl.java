package com.project.lakshmi.business.operation.exporter.qif;

import java.time.Instant;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.exporter.OperationExporterService;
import com.project.lakshmi.business.operation.exporter.qif.financing.OperationInvestmentFinancingQifExporterService;
import com.project.lakshmi.business.operation.exporter.qif.multitrade.OperationInvestmentMultiTradeQifExporterService;
import com.project.lakshmi.business.operation.exporter.qif.stacking.OperationInvestmentStackingQifExporterService;
import com.project.lakshmi.business.operation.exporter.qif.trade.OperationInvestmentTradeQifExporterService;
import com.project.lakshmi.business.operation.investment.InvestmentService;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.InvestmentType;
import com.project.lakshmi.model.operation.investment.OperationInvestment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentMultiTrade;
import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;
import com.project.lakshmi.technical.DateUtil;
import com.project.lakshmi.technical.FileUtils;
import com.project.lakshmi.technical.NumberUtil;

@Service("operationQifExporterService")
public class OperationQifExporterServiceImpl implements OperationQifExporterService {
	
	@Autowired
	InvestmentService investmentService;
	
	@Autowired
	OperationExporterService operationExporterService;
	
	@Autowired
	OperationInvestmentTradeQifExporterService operationInvestmentTradeExporterService;
	
	@Autowired
	OperationInvestmentMultiTradeQifExporterService operationInvestmentMultiTradeExporterService;
	
	@Autowired
	OperationInvestmentStackingQifExporterService operationInvestmentStackingExporterService;
	
	@Autowired
	OperationInvestmentFinancingQifExporterService operationInvestmentFinancingExporterService;

	@Override
	public String createFile() {
		FileUtils.removeFile(getFileName());
		writeOnFile(QifExporterConstants.HEADER);
		
		return getFileName();
	}
	
	@Override
	public void writeOperation(OperationInvestment operation) {
		// Si c'est un trade, il faut ecrire plusieurs opérations
		if (InvestmentType.TRADE.equals(operation.getInvestmentType())) {
			OperationInvestmentTrade trade = (OperationInvestmentTrade) operation;
			operationInvestmentTradeExporterService.writeOperation(trade);
		} else if (InvestmentType.MULTI_TRADE.equals(operation.getInvestmentType())) {
			OperationInvestmentMultiTrade multiTrade = (OperationInvestmentMultiTrade) operation;
			operationInvestmentMultiTradeExporterService.writeOperation(multiTrade);
		} else if (InvestmentType.STACKING.equals(operation.getInvestmentType()) ||
				InvestmentType.MINING.equals(operation.getInvestmentType())) {
			operationInvestmentStackingExporterService.writeOperation(operation);
		} else if (InvestmentType.WITHDRAW.equals(operation.getInvestmentType()) ||
				InvestmentType.DEPOSIT.equals(operation.getInvestmentType())) {
			operationInvestmentFinancingExporterService.writeOperation(operation);
		} else {
			throw new NotYetImplementedException("export pas encore implémenté " + operation.getInvestmentType());
		}
	}
	
	protected void write(Instant date, Investment investment, String type, Double totalPrice, Double feePrice, String memo) {
		// Date
		String formattedDate = DateUtil.formatDate(date, QifExporterConstants.DATE_FORMAT);
//		String formattedDate = DateUtil.formatDate(Instant.now(), QifExporterConstants.DATE_FORMAT);
		writeAttribute(formattedDate, QifExporterConstants.PREFIX_DATE);
		
		// Quantité (toujours positif)
		Double.toString(Math.abs(investment.getQuantity()));
		Double quantity = Math.abs(investment.getQuantity());
		writeAttribute(NumberUtil.toExactString(quantity), QifExporterConstants.PREFIX_QUANTITY);
		
		// Asset
		String asset = investment.getAsset().getIsin();
		writeAttribute(asset, QifExporterConstants.PREFIX_SECURITY);
		
		// Type
		writeAttribute(type, QifExporterConstants.PREFIX_ACTION);
		
		// Prix total de l'opération
		writeAttribute(NumberUtil.toExactString(totalPrice), QifExporterConstants.PREFIX_TOTAL);
		
		// fee (toujours positif : peut etre un bug de l'import ms comptes)
		writeAttribute(NumberUtil.toExactString(feePrice), QifExporterConstants.PREFIX_FEE); 
		
		// Le prix de l'asset est le total - fee / divisé par abs(quantité)
		Double price = (totalPrice - Math.abs(feePrice)) / Math.abs(investment.getQuantity());
		writeAttribute(NumberUtil.toExactString(price), QifExporterConstants.PREFIX_PRICE);
		
		// Memo
		writeAttribute(memo, QifExporterConstants.PREFIX_MEMO);
		
		writeOnFile(QifExporterConstants.END_OPERATION);
	}
	
	protected void writeAttribute(String attribute, String prefix) {
		writeOnFile(prefix + attribute);
	}
	
	protected void writeOnFile(String message) {
		FileUtils.writeOnFileAndEndLine(getFileName(), message);
	}
	
	protected String getFileName() {
		return operationExporterService.getOrigin().getFileName() + QifExporterConstants.FILE_NAME;
	}
}
