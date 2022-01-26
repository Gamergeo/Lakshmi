package com.project.lakshmi.business.ohlc.exporter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.asset.price.Ohlc;

/**
 * Crée un fichier secondaire pour les opérations à vérifier
 */
@Service
public interface OhlcExporterService {
	
	String exportOhlc(List<Ohlc> ohlcs);

}
