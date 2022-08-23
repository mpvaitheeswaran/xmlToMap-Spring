package com.vaitheeswaran.xmltomap.tasklet;

import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;
import org.w3c.dom.NodeList;

import com.vaitheeswaran.xmltomap.parser.XmlParser;

public class XmlToMapTasklet implements Tasklet {

	private Resource directory;
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		String filePath = directory.getFile().toString();
		XmlParser parser = new XmlParser(filePath, "ir_tnx_record");
		NodeList nList = parser.getElementNodeList();
		Map<String, Object> data = parser.toMap(nList);
		System.out.println("XML to MAP");
		System.out.println(data);
		return RepeatStatus.FINISHED;
	}
	
	public Resource getDirectory() {
		return directory;
	}

	public void setDirectory(Resource directory) {
		this.directory = directory;
	}

}
