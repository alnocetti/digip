package com.next.digip.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.digip.enums.TipoRotacion;

public class Articulo {
	
	private String codigo;
	private String descripcion;
	private int diasVidaUtil;
	private boolean usaLote;
	private boolean usaSerie;
	private boolean usaVencimiento;
	private TipoRotacion articuloTipoRotacion;
	private boolean activo;
	private List<ArticuloUnidadMedida> unidadesMedida;
	
	public Articulo() {
		super();
		this.unidadesMedida = new ArrayList<ArticuloUnidadMedida>();
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

	public int getDiasVidaUtil() {
		return diasVidaUtil;
	}

	public void setDiasVidaUtil(int diasVidaUtil) {
		this.diasVidaUtil = diasVidaUtil;
	}

	public boolean isUsaLote() {
		return usaLote;
	}

	public void setUsaLote(boolean usaLote) {
		this.usaLote = usaLote;
	}

	public boolean isUsaSerie() {
		return usaSerie;
	}

	public void setUsaSerie(boolean usaSerie) {
		this.usaSerie = usaSerie;
	}

	public boolean isUsaVencimiento() {
		return usaVencimiento;
	}

	public void setUsaVencimiento(boolean usaVencimiento) {
		this.usaVencimiento = usaVencimiento;
	}

	public TipoRotacion getArticuloTipoRotacion() {
		return articuloTipoRotacion;
	}

	public void setArticuloTipoRotacion(TipoRotacion articuloTipoRotacion) {
		this.articuloTipoRotacion = articuloTipoRotacion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<ArticuloUnidadMedida> getUnidadesMedida() {
		return unidadesMedida;
	}

	public void setUnidadesMedida(List<ArticuloUnidadMedida> unidadesMedida) {
		this.unidadesMedida = unidadesMedida;
	}
	
	public void addUnidadMedida(ArticuloUnidadMedida unidadMedida) {
		this.unidadesMedida.add(unidadMedida);
	}
	
	public String toJson() {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(this);
            System.out.println("JSON = " + json);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
		return null;
	}
	

}
