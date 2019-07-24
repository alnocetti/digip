package com.next.digip.rest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.next.digip.dbf.reader.Reader;
import com.next.digip.exceptions.ExceptionRestClient;
import com.next.digip.model.Articulo;
import com.next.digip.model.Pedido;

public class RestClient {
	
	public URL url;//your url i.e fetch data from .
	public HttpURLConnection conn;
	public Reader reader;
	
	public RestClient() {
		super();
		this.reader = new Reader();
		// TODO Auto-generated constructor stub
	}
		
	public WebResponse testWebService(String uri, String method) throws IOException, RuntimeException, ExceptionRestClient{
				
//		try {
			WebResponse webResponse = new WebResponse();
			
			url = new URL(uri);//your url i.e fetch data from .
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
						
			conn.setRequestProperty("Accept", "application/json");
			
			InputStreamReader _is;
			
			if (conn.getResponseCode() != 200) {
				
				if (conn.getResponseCode() < 500 && conn.getResponseCode() >= 300) {
					
					_is = new InputStreamReader(conn.getErrorStream());
					BufferedReader br = new BufferedReader(_is);
					
					String response = "";
					String line;
					
					while ((line = br.readLine()) != null) {
						response += line;
					}
					throw new ExceptionRestClient(response);
				}
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
	
	public void postArticulos() throws IOException, ExceptionRestClient {
		
		List<Articulo> articulos = new ArrayList<Articulo>();
		
		articulos = reader.readArticulos();
		
		for(Articulo articulo : articulos) {
				
			WebResponse webResponse = new WebResponse();
			
			url = new URL("http://api.patagoniawms.com/v1/Articulos");//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.setDoOutput(true);
			
			OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
			
			wr.write(articulo.toJson());
						
			InputStreamReader _is;
			
			if (conn.getResponseCode() != 200) {
				
				if (conn.getResponseCode() < 500 && conn.getResponseCode() >= 300) {
					
					_is = new InputStreamReader(conn.getErrorStream());
					BufferedReader br = new BufferedReader(_is);
					
					String response = "";
					String line;
					
					while ((line = br.readLine()) != null) {
						response += line;
					}
					throw new ExceptionRestClient(response);
				}
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
	
		}
		// TODO Auto-generated method stub
	}
	
	public List<Pedido> getPedidos(){
		
		List<Pedido> pedidos = new ArrayList<Pedido>();
		
		try {

			URL url = new URL("http://api.patagoniawms.com/v1/Pedidos");//your url i.e fetch data from .
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
		
		return pedidos;
	}
	
	
	public void postPedidos() throws IOException, ExceptionRestClient {
		
		List<Pedido> pedidos = new ArrayList<Pedido>();
		
		pedidos = reader.readPedidos();
		
		for(Pedido pedido : pedidos) {
				
			WebResponse webResponse = new WebResponse();
			
			url = new URL("http://api.patagoniawms.com/v1/Pedidos");//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.setDoOutput(true);
			
			OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
			
			wr.write(pedido.toJson());
						
			InputStreamReader _is;
			
			if (conn.getResponseCode() != 200) {
				
				if (conn.getResponseCode() < 500 && conn.getResponseCode() >= 300) {
					
					_is = new InputStreamReader(conn.getErrorStream());
					BufferedReader br = new BufferedReader(_is);
					
					String response = "";
					String line;
					
					while ((line = br.readLine()) != null) {
						response += line;
					}
					throw new ExceptionRestClient(response);
				}
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
	
		}
		// TODO Auto-generated method stub
	}

}
