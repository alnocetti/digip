package com.next.digip.controller;

import java.io.IOException;
import java.util.List;

import com.next.digip.model.Articulo;
import com.next.digip.rest.RestClient;
import com.next.digip.rest.WebResponse;

public class ControllerLocal {
	
	public static ControllerLocal instance;
	public RestClient restClient;
	
   public static ControllerLocal getInstance() throws RuntimeException {
        
        if(instance == null) {
            
        	instance =  new ControllerLocal();
        }
        
        return instance;
    }

	public ControllerLocal() {
		super();
		this.restClient = new RestClient();
		// TODO Auto-generated constructor stub
	}

	public List<Articulo> getArticulos(){
		System.out.println("<-- getArticulos()");
		return this.restClient.getArticulos();
	}
	
	public WebResponse testWebService(String uri, String method) throws IOException, RuntimeException{
		System.out.println("<-- testWebService()");
		return this.restClient.testWebService(uri, method);
	}

}
