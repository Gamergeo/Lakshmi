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
	public void createFile() {
		FileUtils.removeFile(getFileName());
	}
	
	@Override
	public void writeOperation(OperationInvestment operation) {
		
		if (InvestmentType.WITHDRAW.equals(operation.getInvestmentType()) ||
				InvestmentType.DEPOSIT.equals(operation.getInvestmentType())) {
			write(operation);
		} else {
			throw new NotYetImplementedException("export pas encore implémenté " + operation.getInvestmentType());
		}
	}
	
	/**
	 * Ligne simple 
	 * Date / Type / Quantité Asset
	 */
	private void write(OperationInvestment operation) {
		
		// Date
		String formattedDate = DateUtil.formatDate(operation.getDate(), TextExporterConstants.DATE_FORMAT);
		FileUtils.writeOnFile(getFileName(), formattedDate + " / ");
		
		// Type
		FileUtils.writeOnFile(getFileName(), operation.getInvestmentType() + " / ");
		
		// Quantité
		FileUtils.writeOnFile(getFileName(), operation.getInvestment().getQuantity() + " ");
		
		// Asset
		FileUtils.writeOnFileAndEndLine(getFileName(), operation.getInvestment().getAsset().getIsin() + " ");
	}
	
	protected String getFileName() {
		return operationExporterService.getOrigin().getLabel() + TextExporterConstants.FILE_NAME;
	}
}
