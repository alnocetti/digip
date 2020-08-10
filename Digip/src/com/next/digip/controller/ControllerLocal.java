package com.next.digip.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.next.digip.dbf.reader.ReaderArticulos;
import com.next.digip.dbf.reader.ReaderClientes;
import com.next.digip.dbf.reader.ReaderPedidos;
import com.next.digip.dbf.writer.WriterPedidos;
import com.next.digip.model.Articulo;
import com.next.digip.model.ArticuloUnidadMedida;
import com.next.digip.model.ArticuloUnidadMedidaCodigo;
import com.next.digip.model.Cliente;
import com.next.digip.model.ClienteUbicacion;
import com.next.digip.model.Despacho;
import com.next.digip.model.Pedido;
import com.next.digip.model.PedidoDetalle;
import com.next.digip.rest.RestClient;
import com.next.digip.rest.WebResponse;

public class ControllerLocal extends Observable implements Observer{
	
	public static ControllerLocal instance;
	public RestClient restClient;
	public ReaderArticulos readerArticulos;
	public ReaderClientes readerClientes;
	public ReaderPedidos readerPedidos;
	public WriterPedidos writerPedidos;
	public String estado;
	
   public static ControllerLocal getInstance() throws RuntimeException {
        
	   instance = instance == null ? new ControllerLocal() : instance;
//       if(instance == null) {
//           
//       	instance =  new ControllerLocal();
//       }
//       
       return instance;
   }
	public ControllerLocal() {
		super();
		this.restClient = new RestClient();
		this.readerArticulos = new ReaderArticulos();
		this.readerClientes = new ReaderClientes();
		this.readerPedidos = new ReaderPedidos();
		this.writerPedidos = new WriterPedidos();
		readerArticulos.addObserver(this);
		readerClientes.addObserver(this);
		writerPedidos.addObserver(this);

		// TODO Auto-generated constructor stub
	}

	public List<Articulo> getArticulos(){
		System.out.println("<-- getArticulos()");
		return this.restClient.getArticulos();
	}
	
	public List<WebResponse> postArticulos() throws IOException{
		System.out.println("<-- postArticulos");
		
		List<Articulo> articulos = this.readerArticulos.readArticulos();
				
		List<WebResponse> respuestas = new ArrayList<WebResponse>();
				
		for (Articulo articulo : articulos) {
				
				WebResponse webResponse = this.restClient.postArticulo(articulo);
				
				respuestas.add(webResponse);
		}
		
		return respuestas;
	}
	
	public List<WebResponse> sincronizarArticulos() throws IOException { 
		
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
	
	public List<WebResponse> sincronizarClientes() throws IOException {
		
		System.out.println("<-- Sincronizando clientes");
		
		this.estado = "***Sincronizando clientes***\n";
		setChanged();
		notifyObservers(estado);

		this.estado = "Descargando clientes\n";
		setChanged();
		notifyObservers(estado);
		
		List<Cliente> clientesPatagonia = this.restClient.getClientes();
		
		this.estado = "Leyendo clientes\n";
		setChanged();
		notifyObservers(estado);
		
		List<Cliente> clientes = this.readerClientes.readClientesCompleto();
		
		List<WebResponse> respuestas = new ArrayList<WebResponse>();
		
		boolean existe = false;
				
		for (Cliente cliente : clientes) {
			
			existe = false;
			
			this.estado = "Enviando cliente: " + cliente.getCodigo() + "-" + cliente.getDescripcion() + "\n";
			setChanged();
			notifyObservers(estado);
			
			for(Cliente clientePatagonia : clientesPatagonia) {
				
				if (cliente.getCodigo().equals(clientePatagonia.getCodigo())) {
					
					existe = true;
					break;

				}
					
			}
			
			if (existe) {
				
				WebResponse webResponse = this.restClient.putCliente(cliente);
				respuestas.add(webResponse);
				
			}else {
				
				WebResponse webResponse = this.restClient.postCliente(cliente);
				respuestas.add(webResponse);
			}
			
			// envia ubicacion del cliente
			
			List<ClienteUbicacion> ubicacion = cliente.getClienteUbicacion();
			
			List<ClienteUbicacion> ubicacionPatagonia = this.restClient.getClienteUbicacion(cliente.getCodigo());
						
			for (ClienteUbicacion ub : ubicacion) {
				
				existe = false;
				
				this.estado = "    Enviando ubicacion " + ub.getCodigo() + "\n";
				setChanged();
				notifyObservers(estado);
				
				for(ClienteUbicacion ubPatagonia : ubicacionPatagonia) {

					if (ub.getCodigo().equals(ubPatagonia.getCodigo())) {
						
						existe = true;
						break;
						
					}
				}
				
				if (existe) {

					WebResponse webResponse = this.restClient.putClienteUbicacion(cliente.getCodigo(), ub);

					respuestas.add(webResponse);
					
				}else {
					
					WebResponse webResponse = this.restClient.postClienteUbicacion(cliente.getCodigo(), ub);

					respuestas.add(webResponse);
				}
				

				
			}
		}
			
			this.estado = "_______________________________________________________________________\n\n";
			setChanged();
			notifyObservers(estado);
		
		return respuestas;
	}
	
	
	public List<WebResponse> sincronizarPedidos() throws IOException {
		
		System.out.println("<-- Sincronizando pedidos");
		
		this.estado = "***Sincronizando pedidos***\n";
		setChanged();
		notifyObservers(estado);

		this.estado = "Descargando pedidos\n";
		setChanged();
		notifyObservers(estado);
		
		List<Pedido> pedidosPatagonia = this.restClient.getPedidos("");
		
		this.estado = "Leyendo pedidos\n";
		setChanged();
		notifyObservers(estado);
		
		List<Pedido> pedidos = this.readerPedidos.readPedidos();
		
		List<WebResponse> respuestas = new ArrayList<WebResponse>();
		
		boolean existe = false;
				
		for (Pedido pedido : pedidos) {
			
			existe = false;
			
			this.estado = "Enviando pedido: " + pedido.getCodigo() + "\n";
			setChanged();
			notifyObservers(estado);
			
			for(Pedido pedidoPatagonia : pedidosPatagonia) {
				
				if (pedido.getCodigo().equals(pedidoPatagonia.getCodigo())) {
					
					existe = true;
					
					this.estado = "Pedido: " + pedido.getCodigo() + " EXISTE PEDIDO, NO SE ENVIA.\n";
					setChanged();
					notifyObservers(estado);
					
					break;

				}
					
			}
			
			if (existe) {
				
				continue;
				//WebResponse webResponse = this.restClient.putPedido(pedido);
				//respuestas.add(webResponse);
				
			}else {
				
				WebResponse webResponse = this.restClient.postPedido(pedido);
				respuestas.add(webResponse);
			}
			
			// envia detalle de pedido
			
			List<PedidoDetalle> detalles = pedido.getPedidoDetalle();
			
			List<PedidoDetalle> detallesPatagonia = this.restClient.getDetallePedido(pedido.getCodigo());
						
			for (PedidoDetalle pd : detalles) {
				
				existe = false;
				
				this.estado = "    Enviando detalle del pedido - Articulo: " + pd.getCodigoArticulo() + "\n";
				setChanged();
				notifyObservers(estado);
				
				for(PedidoDetalle dpPatagonia : detallesPatagonia) {

					if (pd.getCodigoArticulo().equals(dpPatagonia.getCodigoArticulo())) {
						
						existe = true;
						break;
						
					}
				}
				
				if (existe) {

					WebResponse webResponse = this.restClient.putPedidoDetalle(pd);

					respuestas.add(webResponse);
					
				}else {
					
					WebResponse webResponse = this.restClient.postPedidoDetalle(pd);

					respuestas.add(webResponse);
				}
				
			}
		}
			
			this.estado = "_______________________________________________________________________\n\n";
			setChanged();
			notifyObservers(estado);
		
		return respuestas;
		
		
	}
	
	
	
	public WebResponse testWebService(String uri, String method) throws IOException, RuntimeException {
		System.out.println("<-- testWebService()");
		return this.restClient.testWebService(uri, method);
	}
	
	public List<Pedido> getPedidos() {
		System.out.println("<-- getPedidos");
		return restClient.getPedidos("");
	}
	
	public void bajarPedidos() throws IOException {
		
		this.estado = "***Descargando pedidos para facturar***\n";
		setChanged();
		notifyObservers(estado);
		
		this.estado = "Descargando pedidos \n";
		setChanged();
		notifyObservers(estado);
		
		List<Pedido> pedidos = this.restClient.getPedidos("completo");
		//pedidos.addAll(this.restClient.getPedidos("eliminado"));
		
		for(Pedido pedido : pedidos) {
			
			if( (pedido.getPedidoEstado().trim().equals("4")) && pedido.getCodigo() != null) {
				this.estado = "Guardando pedido: " + pedido.getCodigo() + "\n";
				setChanged();
				notifyObservers(estado);
				
				this.writerPedidos.writePedido(pedido);
				
				for(PedidoDetalle pedidoDetalle : this.restClient.getDetallePedido(pedido.getCodigo())) {
					
					this.writerPedidos.writeDetalle(pedidoDetalle);
				}
				
				Despacho despacho = this.restClient.getDespacho(Integer.valueOf(pedido.getCodigo()));
				
				this.writerPedidos.writeDespacho(despacho, Integer.valueOf(pedido.getCodigo()));
				
				this.restClient.remitirPedido(Integer.valueOf(pedido.getCodigo()));
				
			}
		}
		
		return;
		
	}
	
	public void pedidoPendiente(int codigoPedido, String error) {
		
		int aux = this.readerPedidos.getNroRegistro(codigoPedido);
		
		this.readerPedidos.cambiarEstadoPedido(aux, "Pendiente", error);
		
		return;
	}
	
	public void errorEnvioArticulo(int codigoArticulo, String error) {
		
		int aux = this.readerArticulos.getNroRegistro(codigoArticulo);
		
		this.readerArticulos.cambiarEstadoArticulo(aux, error);
		
	}
	

	@Override
	public void update(Observable o, Object arg) {

		this.estado = (String)arg;
		setChanged();
		notifyObservers(estado);
		
	}

}
