package com.project.lakshmi.webapp.operation.importer;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.project.lakshmi.business.file.FileService;
import com.project.lakshmi.business.operation.exporter.OperationExporterService;
import com.project.lakshmi.business.operation.importer.OperationImporterService;
import com.project.lakshmi.model.file.RawTextFile;
import com.project.lakshmi.model.operation.Operation;
import com.project.lakshmi.model.operation.importer.OperationImporterOrigin;
import com.project.lakshmi.technical.FileUtils;
import com.project.lakshmi.webapp.AbstractAction;
import com.project.lakshmi.webapp.response.json.JsonResponse;
import com.project.lakshmi.webapp.response.json.StringResponse;

@RequestMapping("operationImporter")
@Controller
public class OperationImporterAction extends AbstractAction {
	
	@Autowired
	FileService fileService;
	
	@Autowired
	OperationImporterService operationImporterService;
	
	@Autowired
	OperationExporterService operationExporterService;
	
	@PostMapping("view")
	public ModelAndView view() {
		ModelAndView model = new ModelAndView("operation/importer/operationImporterView");
		return model;
	}
	
	@PostMapping("import")
	public @ResponseBody JsonResponse importOrder(
			@RequestParam("origin") OperationImporterOrigin origin, 
			@RequestParam("file") MultipartFile file) throws IOException {
		
		// Etape 1 : Création d'un TextFile
		RawTextFile textFile = fileService.readTextFile(file.getInputStream());
		
		List<Operation> operations = operationImporterService.importFile(origin, textFile);
		
		String fileName = operationExporterService.exportOperations(origin, operations);

		return new StringResponse("Fichier disponible à " + FileUtils.getUri(fileName));
	}
}
