package com.next.digip.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.next.digip.enums.EstadoPedido;

public class Pedido {
	private String Codigo;
	private String CodigoClienteUbicacion;
	private String PedidoEstado;
	private String Fecha;
	private String FechaEstimadaEntrega;
	private String Observacion;
	private float Importe;
	private String CodigoDespacho;
	private List<PedidoDetalle> PedidoDetalle;
	
	public Pedido() {
		super();
		this.PedidoDetalle = new ArrayList<PedidoDetalle>();
		// TODO Auto-generated constructor stub
	}

	public String getCodigo() {
		return Codigo;
	}

	public void setCodigo(String codigo) {
		this.Codigo = codigo;
	}

	public String getCodigoClienteUbicacion() {
		return CodigoClienteUbicacion;
	}

	public void setCodigoClienteUbicacion(String codigoClienteUbicacion) {
		this.CodigoClienteUbicacion = codigoClienteUbicacion;
	}

	public String getPedidoEstado() {
		return PedidoEstado;
	}

	public void setPedidoEstado(String pedidoEstado) {
		this.PedidoEstado = pedidoEstado;
	}

	public String getFecha() {
		return Fecha;
	}

	public void setFecha(String fechaPedido) {
		this.Fecha = fechaPedido;
	}

	public String getFechaEstimadaEntrega() {
		return FechaEstimadaEntrega;
	}

	public void setFechaEstimadaEntrega(String fechaPedido) {
		this.FechaEstimadaEntrega = fechaPedido;
	}

	public String getObservacion() {
		return Observacion;
	}

	public void setObservacion(String observacion) {
		this.Observacion = observacion;
	}

	public float getImporte() {
		return Importe;
	}

	public void setImporte(float importe) {
		this.Importe = importe;
	}

	public String getCodigoDespacho() {
		return CodigoDespacho;
	}

	public void setCodigoDespacho(String codigoDespacho) {
		this.CodigoDespacho = codigoDespacho;
	}

	public List<PedidoDetalle> getPedidoDetalle() {
		return PedidoDetalle;
	}

	public void setPedidoDetalle(List<PedidoDetalle> detallePedido) {
		this.PedidoDetalle = detallePedido;
	}
	
	public void addDetallePedido(PedidoDetalle pedidoDetalle) {
		this.PedidoDetalle.add(pedidoDetalle);
	}
	
}
