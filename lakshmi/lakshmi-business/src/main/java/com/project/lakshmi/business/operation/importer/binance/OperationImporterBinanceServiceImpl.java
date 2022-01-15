package com.project.lakshmi.business.operation.importer.binance;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.binance.creator.OperationImporterBinanceMultiTradeService;
import com.project.lakshmi.business.operation.importer.binance.creator.OperationImporterBinanceTradeService;
import com.project.lakshmi.business.operation.importer.binance.extractor.OperationImporterBinanceExtractorService;
import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.investment.InvestmentType;
import com.project.lakshmi.technical.ApplicationException;

@Service("operationImporterBinanceService")
public class OperationImporterBinanceServiceImpl implements OperationImporterBinanceService {
	
	@Autowired
	private OperationImporterBinanceExtractorService operationImporterBinanceExtractorService;
	
	@Autowired
	private OperationImporterBinanceTradeService operationImporterBinanceTradeService;
	
	@Autowired
	private OperationImporterBinanceMultiTradeService operationImporterBinanceMultiTradeService;
	
	/**
	 * Valide le header, si nécéssaire
	 * Lance une exception si le header n'est pas valide
	 * @param origin
	 * @param rawFile
	 */
	@Override
	public void validateHeader(RawTextFile rawFile) {
		
		// Un header est nécéssaire
		String expectedHeader = OperationImporterBinanceConstants.HEADER; 
		String fileHeader = rawFile.getAndRemoveNext();
		
		if (!expectedHeader.equals(fileHeader)) {
			throw new ApplicationException("Header non valide : " + fileHeader 
					+ " (attendu : " + expectedHeader + ")");
		}
	}

	@Override
	public Operation importNextOperation(RawTextFile rawFile) {
		
		// On ne supprime pas encore la ligne concerné
		String firstLine = rawFile.getNext();
		
		// Le fichier est terminé
		if (firstLine == null) {
			return null;
		}
		
		Operation operation = null;
		
		InvestmentType investmentType = operationImporterBinanceExtractorService.getInvestmentType(firstLine);
		
		if (InvestmentType.TRADE.equals(investmentType)) {
			operation = operationImporterBinanceTradeService.importNextOperation(rawFile);
		} else if (InvestmentType.MULTI_TRADE.equals(investmentType)) {  
			operation = operationImporterBinanceMultiTradeService.importNextOperation(rawFile);
		} else {
			throw new NotYetImplementedException();
		}
		
		return operation;
	}
}
