package com.project.lakshmi.business.operation.exporter.text;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.exporter.OperationExporterService;
import com.project.lakshmi.model.operation.investment.InvestmentType;
import com.project.lakshmi.model.operation.investment.OperationInvestment;
import com.project.lakshmi.technical.DateUtil;
import com.project.lakshmi.technical.FileUtils;

@Service("operationTextExporterService")
public class OperationTextExporterServiceImpl implements OperationTextExporterService {
	
	@Autowired 
	OperationExporterService operationExporterService;
	
	@Override
	public String createFile() {
		FileUtils.removeFile(getFileName());
		return getFileName();
	}
	
	@Override
	public void writeOperation(OperationInvestment operation) {
		
		if (InvestmentType.WITHDRAW.equals(operation.getInvestmentType())) {
			write(operation, "Retrait a verifier");
		} else if (InvestmentType.DEPOSIT.equals(operation.getInvestmentType())) {
			write(operation, "Depot a verifier");
		} else if (InvestmentType.TRADE.equals(operation.getInvestmentType())) {
			write(operation, "Trade sans fees");
		} else {
			throw new NotYetImplementedException("export pas encore implémenté " + operation.getInvestmentType());
		}
	}
	
	/**
	 * Ligne simple 
	 * Date / Type / Quantité Asset
	 */
	private void write(OperationInvestment operation, String comment) {
		
		// Date
		String formattedDate = DateUtil.formatDate(operation.getDate(), TextExporterConstants.DATE_FORMAT);
		FileUtils.writeOnFile(getFileName(), formattedDate + " / ");
		
		// Type
		FileUtils.writeOnFile(getFileName(), operation.getInvestmentType() + " / ");
		
		// Quantité
		FileUtils.writeOnFile(getFileName(), operation.getInvestment().getQuantity() + " ");
		
		// Asset
		FileUtils.writeOnFile(getFileName(), operation.getInvestment().getAsset().getIsin() + " / ");
		
		// Comment
		FileUtils.writeOnFileAndEndLine(getFileName(), comment);
	}
	
	protected String getFileName() {
		return operationExporterService.getOrigin().getFileName() + TextExporterConstants.FILE_NAME;
	}
}
