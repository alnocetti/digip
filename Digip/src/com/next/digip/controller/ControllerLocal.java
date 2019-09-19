package com.next.digip.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.next.digip.dbf.reader.Reader;
import com.next.digip.exceptions.ExceptionRestClient;
import com.next.digip.model.Articulo;
import com.next.digip.model.Pedido;
import com.next.digip.rest.RestClient;
import com.next.digip.rest.WebResponse;

public class ControllerLocal {
	
	public static ControllerLocal instance;
	public RestClient restClient;
	public Reader dbfReader;
	
   public static ControllerLocal getInstance() throws RuntimeException {
        
        if(instance == null) {
            
        	instance =  new ControllerLocal();
        }
        
        return instance;
    }

	public ControllerLocal() {
		super();
		this.restClient = new RestClient();
		this.dbfReader = new Reader();
		// TODO Auto-generated constructor stub
	}

	public List<Articulo> getArticulos(){
		System.out.println("<-- getArticulos()");
		return this.restClient.getArticulos();
	}
	
	public List<WebResponse> postArticulos() throws IOException, ExceptionRestClient{
		System.out.println("<-- postArticulos");
		
		List<Articulo> articulos = this.dbfReader.readArticulos();
		
		List<WebResponse> respuestas = new ArrayList<WebResponse>();
		
		int i = 0;
		
		for (Articulo articulo : articulos) {
			
			if(i <= 10) {
			
				WebResponse webResponse = this.restClient.postArticulos(articulo);
				
				respuestas.add(webResponse);
			}
			
			i = i + 1;
			
		}
		return respuestas;
	}
	
	public WebResponse testWebService(String uri, String method) throws IOException, RuntimeException, ExceptionRestClient{
		System.out.println("<-- testWebService()");
		return this.restClient.testWebService(uri, method);
	}
	
	public List<Pedido> getPedidos() {
		System.out.println("<-- getPedidos");
		return restClient.getPedidos();
	}
	
	public void postPedidos() throws IOException, ExceptionRestClient {
		System.out.println("<-- postPedidos()");
		 
		List<Pedido> pedidos = this.dbfReader.readPedidos();
		
		for(Pedido pedido : pedidos) {

			this.restClient.postPedidos(pedido);
			
		}
		
		return;
	}

}
