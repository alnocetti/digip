package com.next.digip.model;

import com.next.digip.enums.DiaEntrega;

public class ClienteUbicacion {
	
	private String Codigo;
	private String CodigoCliente;
	private String Descripcion;
	private String Direccion;
	private String Provincia;
	private String Localidad;
	private String Email;
	private String InformacionAdicional;
	private String InformacionEntrega;
	private String Latitud;
	private String Longitud;
	private DiaEntrega DiaEntrega;
	private String HorarioEntrega;
	private float HorasAEntrega;
	private boolean Activo;

	public ClienteUbicacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCodigo() {
		return Codigo;
	}

	public void setCodigo(String codigo) {
		this.Codigo = codigo;
	}

	public String getCodigoCliente() {
		return CodigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.CodigoCliente = codigoCliente;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.Descripcion = descripcion;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		this.Direccion = direccion;
	}

	public String getProvincia() {
		return Provincia;
	}

	public void setProvincia(String provincia) {
		this.Provincia = provincia;
	}

	public String getLocalidad() {
		return Localidad;
	}

	public void setLocalidad(String localidad) {
		this.Localidad = localidad;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		this.Email = email;
	}

	public String getInformacionAdicional() {
		return InformacionAdicional;
	}

	public void setInformacionAdicional(String informacionAdicional) {
		this.InformacionAdicional = informacionAdicional;
	}

	public String getInformacionEntrega() {
		return InformacionEntrega;
	}

	public void setInformacionEntrega(String informacionEntrega) {
		this.InformacionEntrega = informacionEntrega;
	}

	public String getLatitud() {
		return Latitud;
	}

	public void setLatitud(String latitud) {
		this.Latitud = latitud;
	}

	public String getLongitud() {
		return Longitud;
	}

	public void setLongitud(String longitud) {
		this.Longitud = longitud;
	}

	public DiaEntrega getDiaEntrega() {
		return DiaEntrega;
	}

	public void setDiaEntrega(DiaEntrega diaEntrega) {
		this.DiaEntrega = diaEntrega;
	}

	public String getHorarioEntrega() {
		return HorarioEntrega;
	}

	public void setHorarioEntrega(String horarioEntrega) {
		this.HorarioEntrega = horarioEntrega;
	}

	public float getHorasAEntrega() {
		return HorasAEntrega;
	}

	public void setHorasAEntrega(float horasAEntrega) {
		this.HorasAEntrega = horasAEntrega;
	}

	public boolean isActivo() {
		return Activo;
	}

	public void setActivo(boolean activo) {
		this.Activo = activo;
	}
	
	
}
