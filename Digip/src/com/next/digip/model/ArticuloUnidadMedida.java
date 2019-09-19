package com.next.digip.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.next.digip.enums.UnidadMedida;

public class ArticuloUnidadMedida {
	
	private UnidadMedida UnidadMedida;
	private int Unidades;
	private boolean EsUnidadDeVenta;
	private boolean EsUnidadMenor;
	private boolean EsUnidadConversion;
	private float Alto;
	private float Ancho;
	private float Profundo;
	private float Peso;
	private boolean Activo;
	private List<ArticuloUnidadMedidaCodigo> UnidadMedidaCodigos;
	
	public ArticuloUnidadMedida() {
		super();
		this.UnidadMedidaCodigos = new ArrayList<ArticuloUnidadMedidaCodigo>();
		// TODO Auto-generated constructor stub
	}

	public UnidadMedida getUnidadMedida() {
		return UnidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.UnidadMedida = unidadMedida;
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

	public void setAlto(float alto) {
		this.Alto = alto;
	}

	public float getAncho() {
		return Ancho;
	}

	public void setAncho(float ancho) {
		this.Ancho = ancho;
	}

	public float getProfundo() {
		return Profundo;
	}

	public void setProfundo(float profundo) {
		this.Profundo = profundo;
	}

	public float getPeso() {
		return Peso;
	}

	public void setPeso(float peso) {
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
	
}
