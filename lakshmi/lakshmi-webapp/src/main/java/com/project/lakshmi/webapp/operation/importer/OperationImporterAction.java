package com.project.lakshmi.webapp.operation.importer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.operation.importer.OperationImporterService;
import com.project.lakshmi.business.operation.importer.origin.OperationImporterOriginService;
import com.project.lakshmi.model.operation.Importer.OperationImporterOrigin;
import com.project.lakshmi.webapp.AbstractAction;
import com.project.lakshmi.webapp.response.json.EmptyResponse;
import com.project.lakshmi.webapp.response.json.JsonResponse;

import javassist.NotFoundException;

@RequestMapping("operationImporter")
@Controller
public class OperationImporterAction extends AbstractAction {
	
	@Autowired
	OperationImporterService operationImporterService;
	
	@PostMapping("view")
	public ModelAndView view() {
		ModelAndView model = new ModelAndView("operation/importer/operationImporterView");
		return model;
	}
	
	@PostMapping("import")
	public @ResponseBody JsonResponse importOrder(
			@RequestParam("origin") OperationImporterOrigin origin, 
			@RequestParam("file") MultipartFile file) throws IOException {
		
		operationImporterService.importOperations(origin, file.getInputStream());
		
		return new EmptyResponse();  
	}
}