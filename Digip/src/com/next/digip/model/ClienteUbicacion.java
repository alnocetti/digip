package com.next.digip.model;

import com.next.digip.enums.DiaEntrega;

public class ClienteUbicacion {
	
	private String codigo;
	private String codigoCliente;
	private String descripcion;
	private String direccion;
	private String provincia;
	private String localidad;
	private String email;
	private String informacionAdicional;
	private String informacionEntrega;
	private String latitud;
	private String longitud;
	private DiaEntrega diaEntrega;
	private String horarioEntrega;
	private float horasAEntrega;
	private boolean activo;

	public ClienteUbicacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInformacionAdicional() {
		return informacionAdicional;
	}

	public void setInformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}

	public String getInformacionEntrega() {
		return informacionEntrega;
	}

	public void setInformacionEntrega(String informacionEntrega) {
		this.informacionEntrega = informacionEntrega;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public DiaEntrega getDiaEntrega() {
		return diaEntrega;
	}

	public void setDiaEntrega(DiaEntrega diaEntrega) {
		this.diaEntrega = diaEntrega;
	}

	public String getHorarioEntrega() {
		return horarioEntrega;
	}

	public void setHorarioEntrega(String horarioEntrega) {
		this.horarioEntrega = horarioEntrega;
	}

	public float getHorasAEntrega() {
		return horasAEntrega;
	}

	public void setHorasAEntrega(float horasAEntrega) {
		this.horasAEntrega = horasAEntrega;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
}
