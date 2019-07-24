package com.next.digip.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.next.digip.enums.UnidadMedida;

public class ArticuloUnidadMedida {
	
	private UnidadMedida unidadMedida;
	private int unidades;
	private boolean esUnidadDeVenta;
	private boolean esUnidadMenor;
	private boolean esUnidadConversion;
	private float alto;
	private float ancho;
	private float profundo;
	private float peso;
	private boolean activo;
	private List<ArticuloUnidadMedidaCodigo> unidadMedidaCodigos;
	
	public ArticuloUnidadMedida() {
		super();
		this.unidadMedidaCodigos = new ArrayList<ArticuloUnidadMedidaCodigo>();
		// TODO Auto-generated constructor stub
	}

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public boolean isEsUnidadDeVenta() {
		return esUnidadDeVenta;
	}

	public void setEsUnidadDeVenta(boolean esUnidadDeVenta) {
		this.esUnidadDeVenta = esUnidadDeVenta;
	}

	public boolean isEsUnidadMenor() {
		return esUnidadMenor;
	}

	public void setEsUnidadMenor(boolean esUnidadMenor) {
		this.esUnidadMenor = esUnidadMenor;
	}

	public boolean isEsUnidadConversion() {
		return esUnidadConversion;
	}

	public void setEsUnidadConversion(boolean esUnidadConversion) {
		this.esUnidadConversion = esUnidadConversion;
	}

	public float getAlto() {
		return alto;
	}

	public void setAlto(float alto) {
		this.alto = alto;
	}

	public float getAncho() {
		return ancho;
	}

	public void setAncho(float ancho) {
		this.ancho = ancho;
	}

	public float getProfundo() {
		return profundo;
	}

	public void setProfundo(float profundo) {
		this.profundo = profundo;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<ArticuloUnidadMedidaCodigo> getUnidadMedidaCodigos() {
		return unidadMedidaCodigos;
	}

	public void setUnidadMedidaCodigos(List<ArticuloUnidadMedidaCodigo> unidadMedidaCodigos) {
		this.unidadMedidaCodigos = unidadMedidaCodigos;
	}
	
	public void addUnidadMedidaCodigo(ArticuloUnidadMedidaCodigo unidadMedidaCodigo) {
		this.unidadMedidaCodigos.add(unidadMedidaCodigo);
	}
	
}
