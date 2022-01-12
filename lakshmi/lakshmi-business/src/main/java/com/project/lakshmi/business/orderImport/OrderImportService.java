package com.project.lakshmi.business.orderImport;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public interface OrderImportService {

	void importOrder(String origin, InputStream file) throws IOException;


}
