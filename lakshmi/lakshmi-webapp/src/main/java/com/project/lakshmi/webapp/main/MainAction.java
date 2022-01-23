package com.project.lakshmi.webapp.main;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.api.cryptowatch.CryptoWatchApiService;
import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.webapp.AbstractAction;

/**
 * General action (header, menu,...)
 */
@RequestMapping("main")
@Controller
public class MainAction extends AbstractAction {
	
	private final Logger LOGGER = LogManager.getLogger(MainAction.class);
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	CryptoWatchApiService cryptoWatchApiService;

	@GetMapping("main")
	public ModelAndView main() {
		initApplication();
		return new ModelAndView("main/main");
	}

	@PostMapping("startContent")
	public ModelAndView content() {
		return new ModelAndView("start/startView");
	}
	
	private void initApplication() {
		LOGGER.info("Application en cours de démarrage");
		getSession().setMaxInactiveInterval(100*60);
		
		// On met en place la liste des identifiants de cryptowatch
		@SuppressWarnings("unchecked")
		List<ApiIdentifier> cryptowatchIdentifiers = (List<ApiIdentifier>) getSession().getAttribute(SESSION_ATTRRIBUTE_PAIRLIST);
		
		// Elle n'est pas initialisé
		if (cryptowatchIdentifiers == null) {
			cryptowatchIdentifiers = cryptoWatchApiService.getAllIdentifiers();
			getSession().setAttribute(SESSION_ATTRRIBUTE_PAIRLIST, cryptowatchIdentifiers);
		}
	}
}
