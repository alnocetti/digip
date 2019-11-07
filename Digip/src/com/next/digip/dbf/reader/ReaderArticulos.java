package com.next.digip.dbf.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.yasas.xbase4j.XBase;
import org.yasas.xbase4j.XBaseFile;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import com.next.digip.enums.TipoRotacion;
import com.next.digip.model.Articulo;
import com.next.digip.model.ArticuloUnidadMedida;
import com.next.digip.model.ArticuloUnidadMedidaCodigo;


public class ReaderArticulos extends Observable{

	private DBFReader reader;
	public int cantidadLeida;

	public ReaderArticulos() {
		super();
		this.cantidadLeida = 0;
		this.reader = null;
	}

	public List<Articulo> readArticulos() {

		List<Articulo> articulos = new ArrayList<Articulo>();

		reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitems.dbf"));

			DBFRow row;

			while ((row = reader.nextRow()) != null) {
				
				// ITPEDIDO == "S" .AND. SUBSTR(CODIGO,1,1) # "C" .AND. GRUPO # 90
				boolean validacion1 = false;

				boolean validacion2 = false;

				boolean validacion3 = false;

				Articulo articulo = new Articulo();

				// Valida si se tiene que enviar el producto
				if (row.getString("ITPEDIDO").equals("S"))
					validacion1 = true;

				if (!row.getString("CODIGO").substring(0, 1).equals("C"))
					validacion2 = true;

				if (row.getInt("GRUPO") != 90)
					validacion3 = true;

				articulo.setCodigo(row.getString("CODIGO"));
				articulo.setDescripcion(row.getString("DESCRIPCIO"));
				articulo.setDiasVidaUtil(365);
				articulo.setUsaLote(false);
				articulo.setUsaSerie(false);
				articulo.setUsaVencimiento(false);
				articulo.setArticuloTipoRotacion(TipoRotacion.Alta);
				articulo.setActivo(true);

				if (validacion1 && validacion2 && validacion3)
					articulos.add(articulo);

			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			DBFUtils.close(reader);
		}

		return articulos;

	}

	public List<Articulo> readArticulosCompleto() {

		List<Articulo> articulos = new ArrayList<Articulo>();

		List<ArticuloUnidadMedida> unidadesMedida = new ArrayList<ArticuloUnidadMedida>();
		XBaseFile writer = null;
		DBFReader reader = null;
		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitems.dbf"));
			writer = new XBase().open(new File("//192.168.90.2/visual/dtitems.dbf"));

			
			DBFRow row;

			int registro = 0;
			int cantRegistros = reader.getRecordCount();

			while ((row = reader.nextRow()) != null && registro <= 4) {
				
				registro++;
				
				this.cantidadLeida = (registro / cantRegistros) * 100;
				setChanged();
				notifyObservers("Leyendo articulos: " + Integer.toString(registro) + " de " + Integer.toString(cantRegistros) + "\n");
			
				// ITPEDIDO == "S" .AND. SUBSTR(CODIGO,1,1) # "C" .AND. GRUPO # 90

				Articulo articulo = new Articulo();

				// Valida si se tiene que enviar el producto
				if (!row.getString("ITPEDIDO").equals("S"))
					continue;

				if (row.getString("CODIGO").substring(0, 1).equals("C"))
					continue;

				if (row.getInt("GRUPO") == 90)
					continue;
				
				if (!row.getString("ITNOVEDAD").equals("S"))
					continue;
				
				//actualizo novedad
				writer.go(registro - 1);
				writer.setValue("ITNOVEDAD", "N");
				
				articulo.setCodigo(row.getString("CODIGO"));
				articulo.setDescripcion(row.getString("DESCRIPCIO"));
				articulo.setDiasVidaUtil(365);
				articulo.setUsaLote(false);
				articulo.setUsaSerie(false);
				articulo.setUsaVencimiento(false);
				articulo.setArticuloTipoRotacion(TipoRotacion.Alta);
				articulo.setActivo(true);

				unidadesMedida = this.readUnidadesMedidaPorArticulo(row.getString("CODIGO"));

				for (ArticuloUnidadMedida aum : unidadesMedida) {

					ArticuloUnidadMedidaCodigo aumc = new ArticuloUnidadMedidaCodigo();

					switch (aum.getUnidadMedida_Id()) {
					case 1:
						aumc.setCodigo(row.getString("ITEAN13"));
						aum.setUnidades(this.getUnidad(row));

						break;
					case 2:
						aumc.setCodigo(row.getString("ITDUN14"));
						aum.setUnidades(this.getUnidadPorBulto(row));

						break;
					case 3:
						aumc.setCodigo(row.getString("CODIGO"));
						aum.setUnidades(this.getUnidadPorPallet(row));

						break;
					}
					aum.addUnidadMedidaCodigo(aumc);
				}

				articulo.setUnidadesMedida(unidadesMedida);

				articulos.add(articulo);
				
			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			DBFUtils.close(reader);
			writer.closeQuietly();
		}
		
		return articulos;

	}

	public List<ArticuloUnidadMedida> readUnidadesMedidaPorArticulo(String codigoArticulo) {

		List<ArticuloUnidadMedida> unidadesMedida = new ArrayList<ArticuloUnidadMedida>();

		DBFReader reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitmedida.dbf"));

			DBFRow row;

			while ((row = reader.nextRow()) != null) {

				if (!row.getString("MEDCODIGO").equals(codigoArticulo))
					continue;

				ArticuloUnidadMedida unidadMedida = new ArticuloUnidadMedida();

				unidadMedida.setAlto(row.getInt("MEDALTO"));
				unidadMedida.setAncho(row.getInt("MEDANCHO"));
				unidadMedida.setPeso(row.getInt("MEDPESO"));
				unidadMedida.setProfundo(row.getInt("MEDLARGO"));
				unidadMedida.setActivo(true);

				int aux_id = Integer.parseInt(codigoArticulo + Integer.toString(row.getInt("MEDTIPO")));
				unidadMedida.setUnidadMedida_Id(aux_id);

				unidadMedida.setUnidadMedida_Id(row.getInt("MEDTIPO"));

				unidadesMedida.add(unidadMedida);
			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			DBFUtils.close(reader);
		}

		return unidadesMedida;
	}

	public int getUnidad(DBFRow row) {

		try {

			int unidad = row.getInt("UNIDAD");

			return unidad == 0 ? 1 : unidad;

		} catch (DBFException e) {

			e.printStackTrace();
		}

		return 0;
	}

	public int getUnidadPorBulto(DBFRow row) {

		try {

			int unidad = row.getInt("UNIDAD");

			unidad = unidad == 0 ? 1 : unidad;

			int fraccion = row.getInt("ITFRACCION");

			fraccion = fraccion == 0 ? 1 : fraccion;

			return unidad * fraccion;

		} catch (DBFException e) {

			e.printStackTrace();

		}

		return 0;
	}

	public int getUnidadPorPallet(DBFRow row) {

		try {

			int unidad = row.getInt("UNIDAD");

			unidad = unidad == 0 ? 1 : unidad;

			int fraccion = row.getInt("ITFRACCION");

			fraccion = fraccion == 0 ? 1 : fraccion;

			int bltxpal = row.getInt("ITBLTXPAL");

			bltxpal = bltxpal == 0 ? 1 : bltxpal;

			return unidad * fraccion * bltxpal;

		} catch (DBFException e) {

			e.printStackTrace();
		}

		return 0;
	}

}
