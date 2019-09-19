package com.next.digip.dbf.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import com.next.digip.enums.EstadoPedido;
import com.next.digip.enums.TipoRotacion;
import com.next.digip.model.Articulo;
import com.next.digip.model.ArticuloUnidadMedida;
import com.next.digip.model.ArticuloUnidadMedidaCodigo;
import com.next.digip.model.Pedido;
import com.next.digip.model.PedidoDetalle;

public class Reader {

	private DBFReader reader;

	public Reader() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public void readFile(String file) {

		reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream(file));

			// get the field count if you want for some reasons like the following

			int numberOfFields = reader.getFieldCount();


			// use this count to fetch all field information
			// if required

			for (int i = 0; i < numberOfFields; i++) {

				DBFField field = reader.getField(i);

				// do something with it if you want
				// refer the JavaDoc API reference for more details
				//
				System.out.println("field name:" + field.getName());
			}

			// Now, lets us start reading the rows

			Object[] rowObjects;

			while ((rowObjects = reader.nextRecord()) != null) {

				for (int i = 0; i < rowObjects.length; i++) {
					System.out.println(rowObjects[i]);
				}
				System.out.println("--------------------------------");

			}
			System.out.println("number of fields: " + numberOfFields);

			// By now, we have iterated through all of the rows

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			DBFUtils.close(reader);
		}
	}
	
	public List<Articulo> readArticulos(){
		
		List<Articulo> articulos = new ArrayList<Articulo>();
		
		reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitems.dbf"));

			// get the field count if you want for some reasons like the following

			int numberOfFields = reader.getFieldCount();

			Object[] rowObjects;

			while ((rowObjects = reader.nextRecord()) != null) {
								
				Articulo articulo = new Articulo();
				
				ArticuloUnidadMedida articuloUnidadMedida = new ArticuloUnidadMedida();
				
				ArticuloUnidadMedidaCodigo unidadMedidaCodigo = new ArticuloUnidadMedidaCodigo();
				
				for (int i = 0; i < numberOfFields; i++) {
					
					DBFField field = reader.getField(i);

					if (field.getName().equals("ITCODIGO")) {
						
						articulo.setCodigo((String) rowObjects[i]);
					}
					
					if (field.getName().equals("ITDESCRIP")) {
						
						articulo.setDescripcion((String) rowObjects[i]);
						
					}
										
					if (field.getName().equals("ITUNIDAD")) {
						
						articuloUnidadMedida.setUnidades(((BigDecimal) rowObjects[i]).intValue());
						
					}
					
				}
				

				articulo.setDiasVidaUtil(365);
				articulo.setUsaLote(false);
				articulo.setUsaSerie(false);
				articulo.setUsaVencimiento(false);
				articulo.setArticuloTipoRotacion(TipoRotacion.Alta);
				articulo.setActivo(true);
				
//				unidadMedidaCodigo.setCodigo("Unidad");
//				unidadMedida.setUnidadMedida(UnidadMedida.Unidad);
				articuloUnidadMedida.addUnidadMedidaCodigo(unidadMedidaCodigo);
//				unidadMedida.setEsUnidadDeVenta(true);
//				unidadMedida.setEsUnidadMenor(true);
//				unidadMedida.setEsUnidadConversion(true);
//				unidadMedida.setAlto(0);
//				unidadMedida.setAncho(0);
//				unidadMedida.setProfundo(0);
//				unidadMedida.setPeso(0);
//				unidadMedida.setActivo(true);
				
				articulo.addUnidadMedida(articuloUnidadMedida);
														
				articulos.add(articulo);

			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			DBFUtils.close(reader);
		}
		
		return articulos;
		
	}
	
	public List<Pedido> readPedidos(){
		
		List<Pedido> pedidos = new ArrayList<Pedido>();
		
		reader = null;
		
		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtpedido.dbf"));

			DBFRow row;
			Pedido pedido = null;
			String cliente = "";
			int numeroPedido = 0;
			
			while ((row = reader.nextRow()) != null) {
				
				PedidoDetalle pedidoDetalle = new PedidoDetalle();
				
				if (!row.getString("PDCLIENTE").equals(cliente)) {
					
					pedido = new Pedido();	
					
					numeroPedido += 1;
				} 
				
				cliente = row.getString("PDCLIENTE");
				
				pedidoDetalle.setCodigoPedido(Integer.toString(numeroPedido));
				
				pedidoDetalle.setCodigoArticulo(row.getString("PDITEM"));
				
				pedidoDetalle.setUnidades(row.getInt("PDUNIDAD"));
				
				pedidoDetalle.setUnidadesSatisfecha(0);
				
				pedidoDetalle.setMinimoDiasVencimiento(0);
								
				pedido.setCodigo(Integer.toString(numeroPedido));
				
				pedido.setCodigoClienteUbicacion(cliente);
				
				pedido.setCodigoDespacho(Integer.toString(numeroPedido));
				
				pedido.setFecha(new Date());
				
				pedido.setFechaEstimadaEntrega(new Date());
				
				pedido.setImporte(0);
				
				pedido.setObservacion("");
				
				pedido.setPedidoEstado(EstadoPedido.Completo);		
								
				if(pedidos.size() >= numeroPedido && pedidos.get(numeroPedido-1).getCodigoClienteUbicacion().equals(cliente)) {
					
					pedidos.get(numeroPedido - 1).addDetallePedido(pedidoDetalle);
					
				} else {
					
					if (pedido != null) {
						pedido.addDetallePedido(pedidoDetalle);
						pedidos.add(pedido);
					}
				}
								
			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			DBFUtils.close(reader);
		}
		
		return pedidos;
		
	}
	
}

