package com.project.lakshmi.webapp.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.api.cryptowatch.CryptoWatchApiService;
import com.project.lakshmi.business.api.kucoin.KucoinApiService;
import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.webapp.AbstractAction;

/**
 * General action (header, menu,...)
 */
@RequestMapping("main")
@Controller
public class MainAction extends AbstractAction {
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	CryptoWatchApiService cryptoWatchApiService;
	
	@Autowired
	KucoinApiService kucoinApiService;

	@GetMapping("main")
	public ModelAndView main() {
//		initApplication();
		return new ModelAndView("main/main");
	}

	@PostMapping("startContent")
	public ModelAndView content() {
		return new ModelAndView("start/startView");
	}
	
//	private void initApplication() {
//		LOGGER.info("Application en cours de démarrage");
//		getSession().setMaxInactiveInterval(100*60);
//		
//		// On met en place la liste des identifiants de cryptowatch
//		@SuppressWarnings("unchecked")
//		List<ApiIdentifier> apiIdentifiers = (List<ApiIdentifier>) getSession().getAttribute(SESSION_ATTRRIBUTE_PAIRLIST);
//		
//		// Elle n'est pas initialisé
//		if (apiIdentifiers == null) {
//			apiIdentifiers = new ArrayList<ApiIdentifier>();
//			apiIdentifiers.addAll(cryptoWatchApiService.getAllIdentifiers());
//			apiIdentifiers.addAll(kucoinApiService.getAllIdentifiers());
//			
//			getSession().setAttribute(SESSION_ATTRRIBUTE_PAIRLIST, apiIdentifiers);
//		}
//	}
}
