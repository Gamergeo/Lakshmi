package com.project.lakshmi.business.ohlc.exporter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.asset.price.Ohlc;

/**
 * Cr�e un fichier secondaire pour les op�rations � v�rifier
 */
@Service
public interface OhlcExporterService {
	
	String exportOhlc(List<Ohlc> ohlcs);

}
