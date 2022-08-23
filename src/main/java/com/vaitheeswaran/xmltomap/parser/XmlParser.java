package com.vaitheeswaran.xmltomap.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {

	private String filename;
	private String rootElementName;
	private Document xmlDoc;

	
	public XmlParser(String filename,String rootElementName) {
		this.filename = filename;
		this.rootElementName = rootElementName;
		this.xmlDoc = getXMLDocument(filename);
		
	}
	
	private Document getXMLDocument(String filename) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(filename));
			doc.getDocumentElement().normalize();
			return doc;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public NodeList getElementNodeList() {
		NodeList nList = xmlDoc.getElementsByTagName(rootElementName);
		return nList;
	}
	public NodeList getElementNodeList(String elementName) {
		NodeList nList = xmlDoc.getElementsByTagName(elementName);
		return nList;
	}


	public Map<String, Object> toMap(NodeList nList) {
		Map<String, Object> data = new HashMap<>();
		for (int nodeIndex = 0; nodeIndex < nList.getLength(); nodeIndex++) {
			Node nNode = nList.item(nodeIndex);
			
			/*
			 * 1.loop all the nodes.
			 * 2.if the child node is a text node put the data.
			 * 3. else do the recursion.
			 */

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element element = (Element) nNode;
				Node firstChild = nNode.getChildNodes().item(0);
					if(firstChild!=null && 
							firstChild.getNodeType()==Node.TEXT_NODE &&
							!(StringUtils.isBlank(firstChild.getTextContent()))) {
						if(!StringUtils.isBlank(firstChild.getTextContent())) {
							if(StringUtils.isEmpty(element.getAttribute("name"))||element.getAttribute("name")==null) {
								data.put(nNode.getNodeName(), nNode.getTextContent());
							}else {
								data.put(element.getAttribute("name"), nNode.getTextContent());
							}
						}
					}else {
						String elementName = nNode.getNodeName();
						NodeList subList = nNode.getChildNodes();
						data.put(elementName, toMap(subList));
					}
				
			}
		}
		return data;
	}
}
