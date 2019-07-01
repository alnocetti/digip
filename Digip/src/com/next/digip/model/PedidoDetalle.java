package com.next.digip.model;

public class PedidoDetalle {
	
	private String codigoPedido;
	private String codigoArticulo;
	private int unidades;
	private int unidadesSatisfecha;
	private int minimoDiasVencimiento;
	
	public PedidoDetalle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(String codigoPedido) {
		this.codigoPedido = codigoPedido;
	}

	public String getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public int getUnidadesSatisfecha() {
		return unidadesSatisfecha;
	}

	public void setUnidadesSatisfecha(int unidadesSatisfecha) {
		this.unidadesSatisfecha = unidadesSatisfecha;
	}

	public int getMinimoDiasVencimiento() {
		return minimoDiasVencimiento;
	}

	public void setMinimoDiasVencimiento(int minimoDiasVencimiento) {
		this.minimoDiasVencimiento = minimoDiasVencimiento;
	}
	
	
	

}
