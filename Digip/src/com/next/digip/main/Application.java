package com.next.digip.main;

import java.io.IOException;

import com.next.digip.view.Main;

public class Application {

	public static void main(String[] args) throws IOException {
		
		
//		XBaseFile dbf = new XBase().open(new File("//192.168.90.2/visual/dtitems.dbf"));
//		for(int i = 0; i < dbf.rowCount(); i++) {
//			dbf.go(i);
//			String aux = dbf.getValue("FECSTANT");
//			dbf.setValue("ITNOVEDAD", "C");
//			System.out.println(aux);
//		}
//		dbf.closeQuietly();
		
		
		Main mainWindow = new Main();
		mainWindow.start();
		
//		try {
//			ControllerLocal.getInstance().postPedidos();
//		} catch (IOException | ExceptionRestClient | RuntimeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}

