package com.project.lakshmi.business.operation.importer.binance.creator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.operation.importer.binance.extractor.OperationImporterBinanceExtractorService;
import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.investment.Investment;
import com.project.lakshmi.model.operation.investment.OperationInvestmentTrade;
import com.project.lakshmi.technical.ApplicationException;

@Service("operationImporterBinanceTradeService")
public class OperationImporterBinanceTradeServiceImpl implements OperationImporterBinanceTradeService {
	
	@Autowired
	private OperationImporterBinanceExtractorService operationImporterBinanceExtractorService;
	
	@Override
	public Operation importNextOperation(RawTextFile rawFile) {
		OperationInvestmentTrade operation = new OperationInvestmentTrade();
		
		String firstLine = rawFile.getAndRemoveNext();
		Date operationDate = operationImporterBinanceExtractorService.getDate(firstLine);
		operation.setDate(operationDate);
		
		// On met en place les infos correspondantes à la ligne trouvée
		fillTradeInfos(operation, firstLine);
		
		// On cherche si la prochaine ligne concerne ce trade
		String line = rawFile.getNext();
		Date lineDate = operationImporterBinanceExtractorService.getDate(line);
		
		while (operationDate.equals(lineDate)) {
			
			//La ligne est prise en compte : on la supprime du fichier
			rawFile.removeNext();
			fillTradeInfos(operation, line);
			line = rawFile.getNext(); // On prends la prochaine ligne sans la supprimer
			
			if (line != null) {
				lineDate = operationImporterBinanceExtractorService.getDate(line);
			} else {
				lineDate = null;
			}
		}
		
		// Il manque une partie au trade, exception
		if (operation.getInvestment() == null || operation.getBalancingInvestment() == null) {
			throw new ApplicationException("Trade incorrect : " + firstLine);
		}
		
		return operation;
	}
	
	private void fillTradeInfos(OperationInvestmentTrade operation, String line) {
		
		Investment lineInvestment = operationImporterBinanceExtractorService.getInvestment(line);
		
		// On met en place les infos correspondantes à la ligne trouvée
		// Comme elles peuvent déja exister, on prends soin de merge si c'est le cas
		if (operationImporterBinanceExtractorService.isTradeMain(line)) {
			
			if (operation.getInvestment() == null) {
				operation.setInvestment(lineInvestment);
			} else {
				operation.getInvestment().addInvestment(lineInvestment);
			}
		} else if (operationImporterBinanceExtractorService.isTradeBalancing(line)) {
			if (operation.getBalancingInvestment() == null) {
				operation.setBalancingInvestment(lineInvestment);
			} else {
				operation.getBalancingInvestment().addInvestment(lineInvestment);
			}
		} else if (operationImporterBinanceExtractorService.isTradeFee(line)) {
			
			if (operation.getFeeInvestment() == null) {
				operation.setFeeInvestment(lineInvestment);
			} else {
				operation.getFeeInvestment().addInvestment(lineInvestment);
			}
		} else { // Ne devrait pas arriver
			throw new ApplicationException("importNextTradeOperation error");
		}
	}
}
