package com.next.digip.model;

import java.util.List;

public class Cliente {
	private String Codigo;
	private String Descripcion;
	private String IdentificadorFiscal;
	private boolean Activo;
	private List<ClienteUbicacion> ClienteUbicacion;
	
	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCodigo() {
		return Codigo;
	}

	public void setCodigo(String codigo) {
		this.Codigo = codigo;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.Descripcion = descripcion;
	}

	public String getIdentificadorFiscal() {
		return IdentificadorFiscal;
	}

	public void setIdentificadorFiscal(String identificadorFiscal) {
		this.IdentificadorFiscal = identificadorFiscal;
	}

	public boolean isActivo() {
		return Activo;
	}

	public void setActivo(boolean activo) {
		this.Activo = activo;
	}

	public List<ClienteUbicacion> getClienteUbicacion() {
		return ClienteUbicacion;
	}

	public void setClienteUbicacion(List<ClienteUbicacion> clienteUbicacion) {
		this.ClienteUbicacion = clienteUbicacion;
	}

	
}
