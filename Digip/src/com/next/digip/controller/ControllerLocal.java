package com.next.digip.controller;

import java.io.IOException;
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
	
	public void postArticulos() throws IOException, ExceptionRestClient{
		System.out.println("<-- postArticulos");
		this.restClient.postArticulos();
		return;
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
		this.restClient.postPedidos();
		return;
	}

}
