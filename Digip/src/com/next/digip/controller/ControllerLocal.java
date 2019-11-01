package com.next.digip.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.next.digip.dbf.reader.ReaderArticulos;
import com.next.digip.dbf.reader.ReaderClientes;
import com.next.digip.dbf.reader.ReaderPedidos;
import com.next.digip.exceptions.ExceptionRestClient;
import com.next.digip.model.Articulo;
import com.next.digip.model.ArticuloUnidadMedida;
import com.next.digip.model.ArticuloUnidadMedidaCodigo;
import com.next.digip.model.Pedido;
import com.next.digip.rest.RestClient;
import com.next.digip.rest.WebResponse;

public class ControllerLocal extends Observable implements Observer{
	
	public static ControllerLocal instance;
	public RestClient restClient;
	public ReaderArticulos readerArticulos;
	public ReaderClientes readerClientes;
	public ReaderPedidos readerPedidos;
	public String estado;
	
   public static ControllerLocal getInstance() throws RuntimeException {
        
        if(instance == null) {
            
        	instance =  new ControllerLocal();
        }
        
        return instance;
    }

	public ControllerLocal() {
		super();
		this.restClient = new RestClient();
		this.readerArticulos = new ReaderArticulos();
		this.readerClientes = new ReaderClientes();
		this.readerPedidos = new ReaderPedidos();
		readerArticulos.addObserver(this);

		// TODO Auto-generated constructor stub
	}

	public List<Articulo> getArticulos(){
		System.out.println("<-- getArticulos()");
		return this.restClient.getArticulos();
	}
	
	public List<WebResponse> postArticulos() throws IOException, ExceptionRestClient{
		System.out.println("<-- postArticulos");
		
		List<Articulo> articulos = this.readerArticulos.readArticulos();
				
		List<WebResponse> respuestas = new ArrayList<WebResponse>();
				
		for (Articulo articulo : articulos) {
				
				WebResponse webResponse = this.restClient.postArticulo(articulo);
				
				respuestas.add(webResponse);
		}
		
		return respuestas;
	}
	
	public List<WebResponse> sincronizarArticulos() throws IOException, ExceptionRestClient { 
		
		System.out.println("<-- Sincronizando articulos");
		
		this.estado = "***Sincronizando articulos***\n";
		setChanged();
		notifyObservers(estado);

		this.estado = "Descargando articulos\n";
		setChanged();
		notifyObservers(estado);
		
		List<Articulo> articulosPatagonia = this.restClient.getArticulos();
		
		this.estado = "Leyendo articulos\n";
		setChanged();
		notifyObservers(estado);
		
		List<Articulo> articulos = this.readerArticulos.readArticulosCompleto();
		
		List<WebResponse> respuestas = new ArrayList<WebResponse>();
		
		boolean existe = false;
				
		for (Articulo articulo : articulos) {
			
			existe = false;
			
			this.estado = "Enviando articulo: " + articulo.getCodigo() + "-" + articulo.getDescripcion() + "\n";
			setChanged();
			notifyObservers(estado);
			
			for(Articulo articuloPatagonia : articulosPatagonia) {
				
				if (articulo.getCodigo().equals(articuloPatagonia.getCodigo())) {
					
					existe = true;
					break;

				}
					
			}
			
			if (existe) {
				
				WebResponse webResponse = this.restClient.putArticulo(articulo);
				respuestas.add(webResponse);
				
			}else {
				
				WebResponse webResponse = this.restClient.postArticulo(articulo);
				respuestas.add(webResponse);
			}
			
			// envia unidades de medida de articulo
			
			List<ArticuloUnidadMedida>unidadesMedida = articulo.getUnidadesMedida();
			
			List<ArticuloUnidadMedida>unidadesMedidaPatagonia = this.restClient.getUnidadesMedida(articulo.getCodigo());
			
			for (ArticuloUnidadMedida um : unidadesMedida) {
				
				existe = false;
				
				this.estado = "    Enviando unidad medida " + um.getUnidadMedida_Id() + "\n";
				setChanged();
				notifyObservers(estado);
				
				for(ArticuloUnidadMedida umPatagonia : unidadesMedidaPatagonia) {

					if (um.getUnidadMedida_Id() == umPatagonia.getUnidadMedida_Id()) {
						
						existe = true;
						break;
						
					}
				}
				
				if (existe) {

					WebResponse webResponse = this.restClient.putArticuloUnidadMedida(articulo.getCodigo(), um);

					respuestas.add(webResponse);
					
				}else {
					
					WebResponse webResponse = this.restClient.postArticuloUnidadMedida(articulo.getCodigo(), um);

					respuestas.add(webResponse);
				}
				
				// envia codigos de unidades de medida del articulo
				
				List<ArticuloUnidadMedidaCodigo> unidadesMedidaCodigo = um.getUnidadMedidaCodigos();
				
				List<ArticuloUnidadMedidaCodigo> unidadesMedidaCodigoPatagonia = this.restClient.getUnidadesMedidaCodigo(articulo.getCodigo(), um.getUnidadMedida_Id());
				
				for (ArticuloUnidadMedidaCodigo umc : unidadesMedidaCodigo) {
					
					existe = false;
					
					this.estado = "        Enviando codigo unidad medida \n";
					setChanged();
					notifyObservers(estado);
					
					for(ArticuloUnidadMedidaCodigo umcPatagonia : unidadesMedidaCodigoPatagonia) {

						if (umc.getCodigo().equals(umcPatagonia.getCodigo())) {
							
							existe = true;
							break;
							
						}
					}
					
					if (existe) {
						// si existe no hacer nada, no volver a enviar
//						WebResponse webResponse = this.restClient.putArticuloUnidadMedidaCodigo(articulo.getCodigo(), um.getUnidadMedida_Id(), umc);
//						this.textAreaResponse.append("Respuesta:" + webResponse.getResponseMessage() + "\n");
//
//						responses.add(webResponse);						
					}else {
						
						WebResponse webResponse = this.restClient.postArticuloUnidadMedidaCodigo(articulo.getCodigo(), um.getUnidadMedida_Id(), umc);

						respuestas.add(webResponse);
					}
				}
				
			}
			
			this.estado = "_______________________________________________________________________\n\n";
			setChanged();
			notifyObservers(estado);
				
		}
		
		this.estado = "Finalizo";
		setChanged();
		notifyObservers(estado);
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
		 
		List<Pedido> pedidos = this.readerPedidos.readPedidos();
		
		for(Pedido pedido : pedidos) {

			this.restClient.postPedidos(pedido);
			
		}
		
		return;
	}

	@Override
	public void update(Observable o, Object arg) {

		this.estado = (String)arg;
		setChanged();
		notifyObservers(estado);
		
	}

}
