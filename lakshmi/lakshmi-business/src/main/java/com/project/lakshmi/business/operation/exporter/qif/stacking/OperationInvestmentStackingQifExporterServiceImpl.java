package com.project.lakshmi.business.operation.exporter.qif.stacking;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.exporter.qif.QifExporterConstants;
import com.project.lakshmi.business.operation.exporter.qif.OperationQifExporterServiceImpl;
import com.project.lakshmi.model.operation.investment.InvestmentType;
import com.project.lakshmi.model.operation.investment.OperationInvestment;
import com.project.lakshmi.technical.NumberUtil;

@Service("operationInvestmentStackingQifExporterService")
public class OperationInvestmentStackingQifExporterServiceImpl extends OperationQifExporterServiceImpl implements OperationInvestmentStackingQifExporterService {

	@Override
	public void writeOperation(OperationInvestment operation) {
		
		String memo;
		
		if (InvestmentType.STACKING.equals(operation.getInvestmentType())) {
			memo = "Stacking ";
		} else {
			memo = "Mining ";
		}
		memo += NumberUtil.toExactString(operation.getInvestment().getQuantity()) + " " + operation.getInvestment().getAsset().getIsin();
		
		write(operation.getDate(), operation.getInvestment(), QifExporterConstants.TYPE_ADD, 0d, 0d, memo);
	}
}
