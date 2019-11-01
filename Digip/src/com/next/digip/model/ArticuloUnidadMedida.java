package com.next.digip.model;

import java.util.ArrayList;
import java.util.List;

public class ArticuloUnidadMedida {
	
	private int UnidadMedida_Id;
	private int Unidades;
	private boolean EsUnidadDeVenta;
	private boolean EsUnidadMenor;
	private boolean EsUnidadConversion;
	private int Alto;
	private int Ancho;
	private int Profundo;
	private int Peso;
	private boolean Activo;
	private List<ArticuloUnidadMedidaCodigo> UnidadMedidaCodigos;
	
	public ArticuloUnidadMedida() {
		super();
		this.UnidadMedidaCodigos = new ArrayList<ArticuloUnidadMedidaCodigo>();
		// TODO Auto-generated constructor stub
	}

	public int getUnidades() {
		return Unidades;
	}

	public void setUnidades(int unidades) {
		this.Unidades = unidades;
	}

	public boolean isEsUnidadDeVenta() {
		return EsUnidadDeVenta;
	}

	public void setEsUnidadDeVenta(boolean esUnidadDeVenta) {
		this.EsUnidadDeVenta = esUnidadDeVenta;
	}

	public boolean isEsUnidadMenor() {
		return EsUnidadMenor;
	}

	public void setEsUnidadMenor(boolean esUnidadMenor) {
		this.EsUnidadMenor = esUnidadMenor;
	}

	public boolean isEsUnidadConversion() {
		return EsUnidadConversion;
	}

	public void setEsUnidadConversion(boolean esUnidadConversion) {
		this.EsUnidadConversion = esUnidadConversion;
	}

	public float getAlto() {
		return Alto;
	}

	public void setAlto(int alto) {
		this.Alto = alto;
	}

	public int getAncho() {
		return Ancho;
	}

	public void setAncho(int ancho) {
		this.Ancho = ancho;
	}

	public int getProfundo() {
		return Profundo;
	}

	public void setProfundo(int profundo) {
		this.Profundo = profundo;
	}

	public int getPeso() {
		return Peso;
	}

	public void setPeso(int peso) {
		this.Peso = peso;
	}

	public boolean isActivo() {
		return Activo;
	}

	public void setActivo(boolean activo) {
		this.Activo = activo;
	}

	public List<ArticuloUnidadMedidaCodigo> getUnidadMedidaCodigos() {
		return UnidadMedidaCodigos;
	}

	public void setUnidadMedidaCodigos(List<ArticuloUnidadMedidaCodigo> unidadMedidaCodigos) {
		this.UnidadMedidaCodigos = unidadMedidaCodigos;
	}
	
	public void addUnidadMedidaCodigo(ArticuloUnidadMedidaCodigo unidadMedidaCodigo) {
		this.UnidadMedidaCodigos.add(unidadMedidaCodigo);
	}

	public int getUnidadMedida_Id() {
		return UnidadMedida_Id;
	}

	public void setUnidadMedida_Id(int unidadMedida_Id) {
		UnidadMedida_Id = unidadMedida_Id;
	}
	
}
