package com.next.digip.model;

import java.util.ArrayList;
import java.util.List;

import com.next.digip.enums.TipoRotacion;

public class Articulo {
	
	private String CodigoArticulo;
	private String Descripcion;
	private int DiasVidaUtil;
	private boolean UsaLote;
	private boolean UsaSerie;
	private boolean UsaVencimiento;
	private TipoRotacion ArticuloTipoRotacion;
	private boolean Activo;
	private List<ArticuloUnidadMedida> ArticuloUnidadMedida;
	
	public Articulo() {
		super();
		this.ArticuloUnidadMedida = new ArrayList<ArticuloUnidadMedida>();
		// TODO Auto-generated constructor stub
	}

	public String getCodigo() {
		return CodigoArticulo;
	}

	public void setCodigo(String codigo) {
		this.CodigoArticulo = codigo;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.Descripcion = descripcion;
	}

	public int getDiasVidaUtil() {
		return DiasVidaUtil;
	}

	public void setDiasVidaUtil(int diasVidaUtil) {
		this.DiasVidaUtil = diasVidaUtil;
	}

	public boolean isUsaLote() {
		return UsaLote;
	}

	public void setUsaLote(boolean usaLote) {
		this.UsaLote = usaLote;
	}

	public boolean isUsaSerie() {
		return UsaSerie;
	}

	public void setUsaSerie(boolean usaSerie) {
		this.UsaSerie = usaSerie;
	}

	public boolean isUsaVencimiento() {
		return UsaVencimiento;
	}

	public void setUsaVencimiento(boolean usaVencimiento) {
		this.UsaVencimiento = usaVencimiento;
	}

	public TipoRotacion getArticuloTipoRotacion() {
		return ArticuloTipoRotacion;
	}

	public void setArticuloTipoRotacion(TipoRotacion articuloTipoRotacion) {
		this.ArticuloTipoRotacion = articuloTipoRotacion;
	}

	public boolean isActivo() {
		return Activo;
	}

	public void setActivo(boolean activo) {
		this.Activo = activo;
	}

	public List<ArticuloUnidadMedida> getUnidadesMedida() {
		return ArticuloUnidadMedida;
	}

	public void setUnidadesMedida(List<ArticuloUnidadMedida> articuloUnidadMedida) {
		this.ArticuloUnidadMedida = articuloUnidadMedida;
	}
	
	public void addUnidadMedida(ArticuloUnidadMedida unidadMedida) {
		this.ArticuloUnidadMedida.add(unidadMedida);
	}
	

}
