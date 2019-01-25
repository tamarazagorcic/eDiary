package com.iktpreobuka.final_project.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.final_project.services.FileHandler;


@Controller
@RequestMapping(path = "/")
public class UploderController {

	@Autowired
	private FileHandler fileHandler;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "upload";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public boolean singleFileUpload(@RequestParam ("file") MultipartFile file) {
		
		logger.debug("This is a debug message"); 
		logger.info("This is an info message"); 
		logger.warn("This is a warn message");
		logger.error("This is an error message");

		
		@SuppressWarnings("null")
		boolean result = (Boolean) null;
		try {
			result = fileHandler.singleFileUpload(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
