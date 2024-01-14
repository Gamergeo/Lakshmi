package com.project.lakshmi.business.operation.importer.binance;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;

@Service
public interface OperationImporterBinanceService {
	
	Operation importNextOperation(RawTextFile rawFile);

	/**
	 * Valide le header, si nécéssaire
	 * Lance une exception si le header n'est pas valide
	 * @param origin
	 * @param rawFile
	 */
	void validateHeader(RawTextFile rawFile);

	/**
	 * Nettoie le fichier, en l'occurence, supprime les caractères en trop
	 */
	void cleanFile(RawTextFile rawFile);
}
