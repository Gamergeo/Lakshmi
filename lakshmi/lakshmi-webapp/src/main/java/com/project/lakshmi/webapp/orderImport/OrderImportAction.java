package com.project.lakshmi.webapp.orderImport;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.orderImport.OrderImportService;
import com.project.lakshmi.webapp.AbstractAction;
import com.project.lakshmi.webapp.response.json.EmptyResponse;
import com.project.lakshmi.webapp.response.json.JsonResponse;

@RequestMapping("orderImport")
@Controller
public class OrderImportAction extends AbstractAction {
	
	@Autowired
	OrderImportService orderImportService;
	
	@PostMapping("view")
	public ModelAndView view() {
		ModelAndView model = new ModelAndView("orderImport/orderImportView");
		return model;
	}
	
	@PostMapping("save")
	public @ResponseBody JsonResponse save(
			@RequestParam("origin") String origin, 
			@RequestParam("file") MultipartFile file) throws IOException {
		
		orderImportService.importOrder(origin, file.getInputStream());
		
		return new EmptyResponse();  
	}
}
