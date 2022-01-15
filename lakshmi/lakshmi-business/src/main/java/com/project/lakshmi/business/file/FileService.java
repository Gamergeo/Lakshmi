package com.project.lakshmi.business.file;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.file.RawTextFile;

@Service
public interface FileService {

	/**
	 * Read a file with only text line (csv, txt) 
	 * @return a TextFile object
	 * @throws IOException 
	 */
	RawTextFile readTextFile(InputStream inputStream) throws IOException;

}
