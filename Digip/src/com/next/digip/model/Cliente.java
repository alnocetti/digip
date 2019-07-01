package com.next.digip.model;

public class Cliente {
	private String codigo;
	private String descripcion;
	private String identificadorFiscal;
	private boolean activo;
	private ClienteUbicacion clienteUbicacion;
	
	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdentificadorFiscal() {
		return identificadorFiscal;
	}

	public void setIdentificadorFiscal(String identificadorFiscal) {
		this.identificadorFiscal = identificadorFiscal;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public ClienteUbicacion getClienteUbicacion() {
		return clienteUbicacion;
	}

	public void setClienteUbicacion(ClienteUbicacion clienteUbicacion) {
		this.clienteUbicacion = clienteUbicacion;
	}

	
}
