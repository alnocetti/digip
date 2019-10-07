package com.next.digip.main;

import com.next.digip.view.Main;

public class Application {

	public static void main(String[] args) {
		
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

