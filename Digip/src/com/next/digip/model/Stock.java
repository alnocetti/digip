package com.next.digip.model;

public class Stock {
	private String codigoArticulo;
	private int unidadesDisponibles;
	private int unidadesReservadas;
	private int unidadesBloqueadas;
	private int unidadesADespachar;
	private int unidadesEnRecepcion;
	private int unidadesTransitoInterno;
	
	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public int getUnidadesDisponibles() {
		return unidadesDisponibles;
	}

	public void setUnidadesDisponibles(int unidadesDisponibles) {
		this.unidadesDisponibles = unidadesDisponibles;
	}

	public int getUnidadesReservadas() {
		return unidadesReservadas;
	}

	public void setUnidadesReservadas(int unidadesReservadas) {
		this.unidadesReservadas = unidadesReservadas;
	}

	public int getUnidadesBloqueadas() {
		return unidadesBloqueadas;
	}

	public void setUnidadesBloqueadas(int unidadesBloqueadas) {
		this.unidadesBloqueadas = unidadesBloqueadas;
	}

	public int getUnidadesADespachar() {
		return unidadesADespachar;
	}

	public void setUnidadesADespachar(int unidadesADespachar) {
		this.unidadesADespachar = unidadesADespachar;
	}

	public int getUnidadesEnRecepcion() {
		return unidadesEnRecepcion;
	}

	public void setUnidadesEnRecepcion(int unidadesEnRecepcion) {
		this.unidadesEnRecepcion = unidadesEnRecepcion;
	}

	public int getUnidadesTransitoInterno() {
		return unidadesTransitoInterno;
	}

	public void setUnidadesTransitoInterno(int unidadesTransitoInterno) {
		this.unidadesTransitoInterno = unidadesTransitoInterno;
	}
	
}
