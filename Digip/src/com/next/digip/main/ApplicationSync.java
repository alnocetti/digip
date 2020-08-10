package com.next.digip.main;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.next.digip.controller.ControllerLocal;
import com.next.digip.dbf.reader.FileUpdater;

public class ApplicationSync {

	public static void main(String[] args) throws IOException, RuntimeException, ParserConfigurationException, SAXException {
		
		
		//leer configuracion local
		File fXmlFile = new File("Configuration.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		Application.API_KEY = doc.getElementsByTagName("API_KEY").item(0).getTextContent();
		Application.DIR_LECTURA = doc.getElementsByTagName("DIR_LECTURA").item(0).getTextContent();
		Application.DIR_ORIGINAL = doc.getElementsByTagName("DIR_ORIGINAL").item(0).getTextContent();
		
		//FileUpdater.getInstance().updateFiles();		

		ControllerLocal.getInstance().sincronizarClientes();
		
		ControllerLocal.getInstance().sincronizarArticulos();
		
		ControllerLocal.getInstance().sincronizarPedidos();
		
		ControllerLocal.getInstance().bajarPedidos();
		
		System.exit(0);

	}

}
