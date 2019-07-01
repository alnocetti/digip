package com.next.digip.model;

import java.util.Set;

import com.next.digip.enums.EstadoPedido;

public class Pedido {
	private String codigo;
	private String codigoClienteUbicacion;
	private EstadoPedido pedidoEstado;
	private String fecha;
	private String fechaEstimadaEntrega;
	private String observacion;
	private float importe;
	private String codigoDespacho;
	private Set<PedidoDetalle> pedidoDetalle;
	
	public Pedido() {
		super();
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFechaEstimadaEntrega() {
		return fechaEstimadaEntrega;
	}

	public void setFechaEstimadaEntrega(String fechaEstimadaEntrega) {
		this.fechaEstimadaEntrega = fechaEstimadaEntrega;
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

	public Set<PedidoDetalle> getPedidoDetalle() {
		return pedidoDetalle;
	}

	public void setPedidoDetalle(Set<PedidoDetalle> pedidoDetalle) {
		this.pedidoDetalle = pedidoDetalle;
	}
	
	
}
