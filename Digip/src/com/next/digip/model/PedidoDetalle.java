package com.next.digip.model;

import com.google.gson.annotations.Expose;

public class PedidoDetalle {
	
	@Expose
	private String CodigoPedido;
	
	@Expose
	private String CodigoArticulo;
	
	@Expose
	private int Unidades;
	
	private int UnidadesSatisfecha;
	
	@Expose
	private int MinimoDiasVencimiento;
	
	public PedidoDetalle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCodigoPedido() {
		return CodigoPedido;
	}

	public void setCodigoPedido(String codigoPedido) {
		this.CodigoPedido = codigoPedido;
	}

	public String getCodigoArticulo() {
		return CodigoArticulo;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		this.CodigoArticulo = codigoArticulo;
	}

	public int getUnidades() {
		return Unidades;
	}

	public void setUnidades(int unidades) {
		this.Unidades = unidades;
	}

	public int getUnidadesSatisfecha() {
		return UnidadesSatisfecha;
	}

	public void setUnidadesSatisfecha(int unidadesSatisfecha) {
		this.UnidadesSatisfecha = unidadesSatisfecha;
	}

	public int getMinimoDiasVencimiento() {
		return MinimoDiasVencimiento;
	}

	public void setMinimoDiasVencimiento(int minimoDiasVencimiento) {
		this.MinimoDiasVencimiento = minimoDiasVencimiento;
	}
	
	
	

}
