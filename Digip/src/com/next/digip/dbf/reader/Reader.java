package com.next.digip.dbf.reader;

import java.io.FileInputStream;
import java.io.IOException;
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
import com.next.digip.model.Cliente;
import com.next.digip.model.ClienteUbicacion;
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
			
			DBFRow row;

			while ((row = reader.nextRow()) != null) {
				
				//ITPEDIDO == "S" .AND. SUBSTR(CODIGO,1,1) # "C" .AND. GRUPO # 90
				boolean validacion1 = false;
				
				boolean validacion2 = false;
				
				boolean validacion3 = false;
								
				Articulo articulo = new Articulo();
				
				ArticuloUnidadMedida articuloUnidadMedida = new ArticuloUnidadMedida();
				
				ArticuloUnidadMedidaCodigo unidadMedidaCodigo = new ArticuloUnidadMedidaCodigo();
								
				//Valida si se tiene que enviar el producto
				if (row.getString("ITPEDIDO").equals("S")) 
						validacion1 = true;
				
				if (!row.getString("CODIGO").substring(0,1).equals("C")) 
					validacion2 = true;
				
				if(row.getInt("GRUPO") != 90) 
					validacion3 = true;
				
	
				articulo.setCodigo(row.getString("CODIGO"));
				articulo.setDescripcion(row.getString("DESCRIPCIO"));
				articulo.setDiasVidaUtil(365);
				articulo.setUsaLote(false);
				articulo.setUsaSerie(false);
				articulo.setUsaVencimiento(false);
				articulo.setArticuloTipoRotacion(TipoRotacion.Alta);
				articulo.setActivo(true);
				
				articuloUnidadMedida.setUnidades(row.getInt("UNIDAD"));
				articuloUnidadMedida.addUnidadMedidaCodigo(unidadMedidaCodigo);
//				unidadMedidaCodigo.setCodigo("Unidad");
//				unidadMedida.setUnidadMedida(UnidadMedida.Unidad);
//				unidadMedida.setEsUnidadDeVenta(true);
//				unidadMedida.setEsUnidadMenor(true);
//				unidadMedida.setEsUnidadConversion(true);
//				unidadMedida.setAlto(0);
//				unidadMedida.setAncho(0);
//				unidadMedida.setProfundo(0);
//				unidadMedida.setPeso(0);
//				unidadMedida.setActivo(true);
				
				articulo.addUnidadMedida(articuloUnidadMedida);
							
				
				if(validacion1 && validacion2 && validacion3) 
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
	
	public List<Cliente> readClientes(){
		
		List<Cliente> clientes = new ArrayList<Cliente>();
				
		reader = null;
		
		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/cccuenta.dbf"));

			DBFRow row;
			
			while ((row = reader.nextRow()) != null) {
				
				Cliente cliente = new Cliente();
							
				cliente.setCodigo(row.getString("CLICODIGO"));
				
				cliente.setDescripcion(row.getString("CLIRAZSOC"));
				
				cliente.setIdentificadorFiscal(Integer.toString(row.getInt("CLICDIVA")));
				
				if (row.getString("CLIFECBAJA") == null) {
					cliente.setActivo(true);
				}else {
					cliente.setActivo(false);
				}
				
				clientes.add(cliente);
				
			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			DBFUtils.close(reader);
		}
		
		return clientes;
		
	}
	
	
	public List<ClienteUbicacion> readClientesUbicacion(){
		
		List<ClienteUbicacion> clientesUbicacion = new ArrayList<ClienteUbicacion>();
				
		reader = null;
		
		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/cccuenta.dbf"));

			DBFRow row;
			
			while ((row = reader.nextRow()) != null) {
								
				ClienteUbicacion clienteUbicacion = new ClienteUbicacion();
			
				clienteUbicacion.setCodigo(row.getString("CLICODIGO"));
				
				clienteUbicacion.setDescripcion("Direccion de " + row.getString("CLIRAZSOC"));
								
				if (row.getString("CLIFECBAJA") == null) {
					clienteUbicacion.setActivo(true);
				}else {
					clienteUbicacion.setActivo(false);
				}
								
				clienteUbicacion.setCodigoCliente(row.getString("CLICODIGO"));
								
				clienteUbicacion.setDireccion(row.getString("CLIDOMICI"));
				
				clienteUbicacion.setProvincia(Integer.toString(row.getInt("CLIPROVIN")));
				
				clienteUbicacion.setLocalidad(row.getString("CLILOCALI"));
				
				clienteUbicacion.setInformacionAdicional(row.getString("CLIOBSERVA"));
								
				clienteUbicacion.setLatitud(row.getString("CLILATI"));
				
				clienteUbicacion.setLongitud(row.getString("CLILONG"));
				
				//clienteUbicacion.setHorarioEntrega(row.getString("CLIHORA"));
							
				clientesUbicacion.add(clienteUbicacion);
				
			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			DBFUtils.close(reader);
		}
		
		return clientesUbicacion;
		
	}
	
}

