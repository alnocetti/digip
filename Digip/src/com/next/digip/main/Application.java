package com.next.digip.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.next.digip.controller.ControllerLocal;
import com.next.digip.dbf.reader.FileUpdater;
import com.next.digip.dbf.reader.ReaderPedidos;
import com.next.digip.model.Pedido;
import com.next.digip.view.Main;

public class Application {

	public static String API_KEY;
	public static String DIR_LECTURA;
	public static String DIR_ORIGINAL;

	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		
		//leer configuracion local
		File fXmlFile = new File("Configuration.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		API_KEY = doc.getElementsByTagName("API_KEY").item(0).getTextContent();
		DIR_LECTURA = doc.getElementsByTagName("DIR_LECTURA").item(0).getTextContent();
		DIR_ORIGINAL = doc.getElementsByTagName("DIR_ORIGINAL").item(0).getTextContent();

		//FileUpdater.getInstance().updateFiles();		

		Main mainWindow = new Main();
		mainWindow.start();		
		
	}
}

