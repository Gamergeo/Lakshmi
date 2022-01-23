package com.project.lakshmi.webapp.asset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.webapp.AbstractAction;
import com.project.lakshmi.webapp.response.json.JsonResponse;
import com.project.lakshmi.webapp.response.json.ModelObjectResponse;

@RequestMapping("asset")
@Controller
public class AssetAction extends AbstractAction {
	
	@Autowired
	AssetService assetService;
	
	@PostMapping("view")
	public ModelAndView view() {
		ModelAndView model = new ModelAndView("asset/assetList");
		model.addObject("assets", assetService.findAll());	
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
	
	public void validate(Asset asset) {
		
//		if (StringUtils.isEmpty(account.getName()) || account.getName().length() > TechnicalConstants.DEFAULT_MAX_TEXT_SIZE) {
//			throw new ApplicationException("Le nom doit etre entre 0 et 50 caractères");
//		}
	}
}
