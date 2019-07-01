package com.next.digip.rest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.next.digip.model.Articulo;

public class RestClient {
	
	public URL url;//your url i.e fetch data from .
	public HttpURLConnection conn;
	
	public RestClient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<Articulo> getArticulos(){
		
		List<Articulo> articulos = new ArrayList<Articulo>();
		
		try {

			URL url = new URL("http://api.patagoniawms.com/v1/Articulos");//your url i.e fetch data from .
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : "
						+ conn.getResponseCode() + "-" + conn.getResponseMessage());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();

		} catch (Exception e) {
			System.out.println("Exception in NetClientGet:- " + e);
		}
		
		return articulos;
	}
	
	
	public WebResponse testWebService(String uri, String method) throws IOException, RuntimeException{
				
//		try {
			WebResponse webResponse = new WebResponse();
			
			url = new URL(uri);//your url i.e fetch data from .
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : "
						+ conn.getResponseCode() + "-" + conn.getResponseMessage());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();
			webResponse.setResponseCode(conn.getResponseMessage());
			webResponse.setResponseMessage(conn.getResponseMessage());
//		} catch (Exception e) {
//			System.out.println("Exception in NetClientGet:- " + e);
//		}
//		
		return webResponse;
	}

}
