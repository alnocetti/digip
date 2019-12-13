package com.next.digip.dbf.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.next.digip.main.Application;

public class FileUpdater {

	@SuppressWarnings("unused")
	private List<String> files;
	public static FileUpdater instance;

	
	public FileUpdater() {
		super();
		this.files = new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}
	
	public static FileUpdater getInstance() {
		
		return (instance == null ? new FileUpdater() : instance);
		
	}
	
	
	@SuppressWarnings("resource")
	public void updateFiles() throws ParserConfigurationException, SAXException, IOException {
		

		// leer configuracion local
		File fXmlFile = new File("Configuration.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		// leo tags de archivos
		NodeList nList = doc.getElementsByTagName("FILE");

		// copio cada archivo con el tag anterior
		for (int i = 0; i < nList.getLength(); i++) {

			String file = doc.getElementsByTagName("FILE").item(i).getTextContent();

			File source = new File(Application.DIR_ORIGINAL + file);
			File dest = new File(Application.DIR_LECTURA + file);

			FileChannel sourceChannel = null;
			FileChannel destChannel = null;
			try {
				sourceChannel = new FileInputStream(source).getChannel();
				destChannel = new FileOutputStream(dest).getChannel();
				destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
			} finally {
				sourceChannel.close();
				destChannel.close();
			}
			System.out.println(file + " copied");

		}
	}

}
