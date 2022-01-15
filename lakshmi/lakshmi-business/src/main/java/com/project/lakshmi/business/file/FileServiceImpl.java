package com.project.lakshmi.business.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.file.RawTextFile;

@Service("fileService")
public class FileServiceImpl implements FileService {
	
	@Override
	public RawTextFile readTextFile(InputStream inputStream) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		RawTextFile textFile = new RawTextFile();
		
		String line = reader.readLine();
		
		while (line != null) {
			textFile.addLine(line);
			line = reader.readLine();
		}
		
		return textFile;
	}

}
