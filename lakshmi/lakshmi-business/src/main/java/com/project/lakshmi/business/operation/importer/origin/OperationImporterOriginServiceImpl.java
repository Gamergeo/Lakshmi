package com.project.lakshmi.business.operation.importer.origin;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import com.project.lakshmi.model.operation.OperationType;
import com.project.lakshmi.model.operation.Importer.OperationImporterOrigin;

@Service("operationImporterOriginService")
public class OperationImporterOriginServiceImpl implements OperationImporterOriginService {

	@Override
	public String getHeader(OperationImporterOrigin origin) {
		
		if (OperationImporterOrigin.BINANCE.equals(origin)) {
			return OperationImporterOriginConstants.BINANCE.HEADER;
		}
		
		throw new NotYetImplementedException("origin header not yet implemented " + origin);
	}

	@Override
	public OperationType getOperationType(OperationImporterOrigin origin) {
		
		if (OperationImporterOrigin.BINANCE.equals(origin)) {
			return OperationType.INVESTMENT;
		}

		throw new NotYetImplementedException("origin type not yet implemented " + origin);
	}

	@Override
	public String getSeparator(OperationImporterOrigin origin) {
		
		if (OperationImporterOrigin.BINANCE.equals(origin)) {
			return OperationImporterOriginConstants.BINANCE.SEPARATOR;
		}
		
		throw new NotYetImplementedException("origin separator not yet implemented " + origin);
	}

	@Override
	public int getItemAmountIndex(OperationImporterOrigin origin) {
		
		if (OperationImporterOrigin.BINANCE.equals(origin)) {
			return OperationImporterOriginConstants.BINANCE.ITEM_AMOUNT_INDEX;
		}
		
		throw new NotYetImplementedException("origin item amount index not yet implemented " + origin);
	}
}
