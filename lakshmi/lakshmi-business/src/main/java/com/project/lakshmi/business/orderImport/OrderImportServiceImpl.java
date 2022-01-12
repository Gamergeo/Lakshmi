package com.project.lakshmi.business.orderImport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service("orderImportService")
public class OrderImportServiceImpl implements OrderImportService {
	
	@Override
	public void importOrder(String origin, InputStream file) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		
		System.out.println(reader.readLine());
				
	}
}
