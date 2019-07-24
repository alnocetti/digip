package com.next.digip.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.digip.enums.EstadoPedido;

public class Pedido {
	private String codigo;
	private String codigoClienteUbicacion;
	private EstadoPedido pedidoEstado;
	private Date fecha;
	private Date fechaEstimadaEntrega;
	private String observacion;
	private float importe;
	private String codigoDespacho;
	private List<PedidoDetalle> pedidoDetalle;
	
	public Pedido() {
		super();
		this.pedidoDetalle = new ArrayList<PedidoDetalle>();
		// TODO Auto-generated constructor stub
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoClienteUbicacion() {
		return codigoClienteUbicacion;
	}

	public void setCodigoClienteUbicacion(String codigoClienteUbicacion) {
		this.codigoClienteUbicacion = codigoClienteUbicacion;
	}

	public EstadoPedido getPedidoEstado() {
		return pedidoEstado;
	}

	public void setPedidoEstado(EstadoPedido pedidoEstado) {
		this.pedidoEstado = pedidoEstado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fechaPedido) {
		this.fecha = fechaPedido;
	}

	public Date getFechaEstimadaEntrega() {
		return fechaEstimadaEntrega;
	}

	public void setFechaEstimadaEntrega(Date fechaPedido) {
		this.fechaEstimadaEntrega = fechaPedido;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public String getCodigoDespacho() {
		return codigoDespacho;
	}

	public void setCodigoDespacho(String codigoDespacho) {
		this.codigoDespacho = codigoDespacho;
	}

	public List<PedidoDetalle> getPedidoDetalle() {
		return pedidoDetalle;
	}

	public void setPedidoDetalle(List<PedidoDetalle> detallePedido) {
		this.pedidoDetalle = detallePedido;
	}
	
	public void addDetallePedido(PedidoDetalle pedidoDetalle) {
		this.pedidoDetalle.add(pedidoDetalle);
	}
	
	public String toJson() {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(this);
            System.out.println("JSON = " + json);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
		return null;
	}
	
}
