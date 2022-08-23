package com.vaitheeswaran.xmltomap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.springframework.batch.core.JobExecution;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.NodeList;

import com.vaitheeswaran.xmltomap.parser.XmlParser;

public class ParserMain {

	public static void main(String[] args) throws IOException {

		String filePath = "src/main/resources/xml/data.xml";
		XmlParser parser = new XmlParser(filePath, "ir_tnx_record");
		NodeList nList = parser.getElementNodeList();
		
		Map<String, Object> data = parser.toMap(nList);  
		//System.out.println(data);
		ParserMain obj = new ParserMain();
		String[] batchConfig = { "batch/batch-task.xml" , "batch/job-config.xml"};
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
	}

}
