package com.project.lakshmi.webapp.asset;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.api.ApiService;
import com.project.lakshmi.business.api.kucoin.KucoinApiService;
import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.technical.ApplicationException;
import com.project.lakshmi.webapp.AbstractAction;
import com.project.lakshmi.webapp.response.json.EmptyResponse;
import com.project.lakshmi.webapp.response.json.JsonResponse;
import com.project.lakshmi.webapp.response.json.ModelObjectResponse;

import javassist.NotFoundException;

@RequestMapping("asset")
@Controller
public class AssetAction extends AbstractAction {
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	KucoinApiService kucoinApiService;
	
	@PostMapping("list")
	public ModelAndView list() {
		ModelAndView model = new ModelAndView("asset/assetList");
		model.addObject("assets", assetService.findAll());
		
//		// A supprimer
//		Asset btc = assetService.findByIsin("ETH2");
//		Asset usdt = assetService.findByIsin("USDT");
//		ApiIdentifier identifier = new ApiIdentifier();
//		identifier.setApi(Api.KUCOIN);
//		identifier.setAsset(btc);
//		identifier.setCurrency(usdt);
//		btc.setApiIdentifier(identifier);
//		
//		List<Asset> assets = new ArrayList<Asset>();
//		assets.add(btc);
////		apiService.getHistoricalOhlc(assets);
//		
//		Instant date = Instant.now().minus(65, ChronoUnit.DAYS);
//		
//		Double price = assetService.getPrice(btc, date);
//		
//		System.out.println(price);
		List<ApiIdentifier> id =  kucoinApiService.getAllIdentifiers();
		
		System.out.println(id);
		
		
		return model;
	}
	
	@PostMapping("viewLine")
	public ModelAndView viewLine(@RequestParam Integer id) {
		ModelAndView model = new ModelAndView("/asset/assetViewLine");
		model.addObject("asset", assetService.findById(id));
		
		return model;
	}
	
	@PostMapping("editLine")
	public ModelAndView editLine(@RequestParam(required = false) Integer id) {
		ModelAndView model = new ModelAndView("/asset/assetEditLine");
		
		// Edit
		if (id != null) {
			model.addObject("asset", assetService.findById(id));
		} else {
			model.addObject("asset", new Asset());
		}

		return model;
	}
	
	@PostMapping("save")
//	public @ResponseBody Asset save(Asset asset) {
	public @ResponseBody JsonResponse save(Asset asset) {
		validate(asset);
		
		assetService.saveOrUpdate(asset);

		return new ModelObjectResponse(asset.getId());
	}
	
	@PostMapping("delete")
	public @ResponseBody JsonResponse delete(@RequestParam Integer id) throws NotFoundException {
		
		assetService.delete(id);
		
		return new EmptyResponse();  
	}
	
	public void validate(Asset asset) {
		
		ApiIdentifier apiIdentifier = asset.getApiIdentifier();
		
		// Il n'est pas d�fini
		if (apiIdentifier == null) {
			return;
		}
		
		// Si cryptowatch est l'api
		if (Api.CRYPTOWATCH.equals(apiIdentifier.getApi())) {
			
			// Dans ce cas, le march� et l'asset et la currency doivent �tre d�fini
			if (StringUtils.isEmpty(apiIdentifier.getMarket())) {
				throw new ApplicationException("Le market doit �tre d�fini");
			}
			
			if (apiIdentifier.getCurrency().getIsin() == null) {
				throw new ApplicationException("La currency doit �tre d�fini");
			}
			
			// Le symbol ne doit pas �tre d�fini
			if (!StringUtils.isEmpty(apiIdentifier.getSymbol())) {
				throw new ApplicationException("Le symbol ne doit pas �tre d�fini");
			}
			
		} else if (Api.YAHOO.equals(apiIdentifier.getApi())) {
			
			// Dans ce cas, le march� et l'asset et la currency doivent �tre d�fini
			if (StringUtils.isEmpty(apiIdentifier.getSymbol())) {
				throw new ApplicationException("Le symbol doit �tre d�fini");
			}
			
			// Le march� et l'asset et la currency ne doivent pas �tre d�fini
			if (!StringUtils.isEmpty(apiIdentifier.getMarket())) {
				throw new ApplicationException("Le market ne doit pas �tre d�fini");
			}
			
			if (!StringUtils.isEmpty(apiIdentifier.getCurrency().getIsin())) {
				throw new ApplicationException("La currency ne doit pas �tre d�fini");
			}
		}  else if (Api.KUCOIN.equals(apiIdentifier.getApi())) {
			
			// Dans ce cas, la currency doivent �tre d�fini
			if (apiIdentifier.getCurrency().getIsin() == null) {
				throw new ApplicationException("La currency doit �tre d�fini");
			}
			
			// Le march� et symbol ne doivent pas �tre d�fini
			if (!StringUtils.isEmpty(apiIdentifier.getMarket())) {
				throw new ApplicationException("Le market ne doit pas �tre d�fini");
			}			
			
			if (!StringUtils.isEmpty(apiIdentifier.getSymbol())) {
				throw new ApplicationException("Le symbol ne doit pas �tre d�fini");
			}
			
		} else if (Api.NONE.equals(apiIdentifier.getApi())) {
			
			// Dans ce cas, ni le symbol, ni le march�, ni la currency ne doivent �tre defini
			if (!StringUtils.isEmpty(apiIdentifier.getMarket())) {
				throw new ApplicationException("Le market ne doit pas �tre d�fini");
			}
			
			if (apiIdentifier.getCurrency().getId() != null) {
				throw new ApplicationException("La currency ne doit pas �tre d�fini");
			}
			
			if (!StringUtils.isEmpty(apiIdentifier.getSymbol())) {
				throw new ApplicationException("Le symbol ne doit pas �tre d�fini");
			}
		}
	}
}
