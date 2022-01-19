package com.project.lakshmi.business.asset.ohlc;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.model.asset.price.Price;
import com.project.lakshmi.persistance.asset.ohlc.OhlcDao;
import com.project.lakshmi.technical.DateUtil;

@Service("ohlcService")
public class OhlcServiceImpl extends AbstractDatabaseService<Ohlc> implements OhlcService {
	
	@Autowired
	OhlcDao ohlcDao;
	
	@Autowired
	AssetService assetService;
	
	@Override
	public OhlcDao getDao() {
		return ohlcDao;
	}

	/**
	 * @return the ohlc corresponding to date and asset
	 */
	@Override
	@Transactional
	public Ohlc find(Asset asset, Date date) {
		
		// Les ohlc sont stockés par minute
		Date floorDate = DateUtil.truncateDay(date);
		
		return getDao().findOhlc(asset.getId(), floorDate);
	}
	
	/**
	 * @return the ohlc corresponding to date and asset
	 * Get the day ohlc
	 */
	@Override
	@Transactional
	public Ohlc findDayOhlc(Asset asset, Date date) {
		
		// Les ohlc sont stockés par minute
		Date floorDate = DateUtil.truncateDay(date);
		Date ceilDate  = DateUtil.ceilingDay(date);
		
		Ohlc floorOhlc = find(asset, floorDate);
		Ohlc ceilOhlc = find(asset, ceilDate);
		Ohlc minOhlc = getDao().findMin(asset.getId(), floorDate, ceilDate);
		Ohlc maxOhlc = getDao().findMax(asset.getId(), floorDate, ceilDate);
		
		Ohlc ohlc = new Ohlc();
		ohlc.setAsset(asset);
		
		// L'open corresponds à l'open du début de journée
		ohlc.setOpen(floorOhlc.getOpen());
		// Le close corresponds au close de fin de journée
		ohlc.setClose(ceilOhlc.getClose());
		// Le high corresponds au max des high sur la journée
		ohlc.setHigh(maxOhlc.getHigh());
		// Le low corresponds au min des lowsur la journée
		ohlc.setLow(minOhlc.getLow());
		
		return ohlc;
	}
	
	
	/**
	 * save an update a OHLC set asset currency
	 */
	@Override
	@Transactional
	public void saveOrUpdate(Ohlc ohlc, Asset currency) {
		convertToEuro(ohlc, currency);
		
		saveOrUpdate(ohlc);
	}
	
	
	/**
	 * modify the ohlc to convert to euroµ
	 * Ohlc is in currency asset
	 */
	@Override
	@Transactional
	public void convertToEuro(Ohlc ohlc, Asset currency) {
		
		// On est déja en euro, pas besoin de conversion
		if (currency.isEuro()) {
			return;
		}
		
		// Sinon on va chercher le prix correspondant à l'asset currency et on ajuste
		Ohlc currencyOhlc = find(currency, ohlc.getDate());
		
		ohlc.setOpen(ohlc.getOpen() * currencyOhlc.getOpen());
		ohlc.setHigh(ohlc.getHigh() * currencyOhlc.getHigh());
		ohlc.setLow(ohlc.getLow() * currencyOhlc.getLow());
		ohlc.setClose(ohlc.getClose() * currencyOhlc.getClose());
	}
}