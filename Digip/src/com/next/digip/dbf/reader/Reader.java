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
	private List<ArticuloUnidadMedida> articuloUnidadesMedida;

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
	
	public List<Articulo> readArticulosCompleto(){
		
		List<Articulo> articulos = new ArrayList<Articulo>();
		
		List<ArticuloUnidadMedida> unidadesMedida = new ArrayList<ArticuloUnidadMedida>();
				
		DBFReader reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitems.dbf"));
			
			DBFRow row;
			
			int i = 0;

			while ((row = reader.nextRow()) != null && i<20) {
				
				i++;
				//ITPEDIDO == "S" .AND. SUBSTR(CODIGO,1,1) # "C" .AND. GRUPO # 90
				boolean validacion1 = false;
				
				boolean validacion2 = false;
				
				boolean validacion3 = false;
								
				Articulo articulo = new Articulo();
																
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
				
				unidadesMedida = this.readUnidadesMedidaPorArticulo(row.getString("CODIGO"));
				
				for(ArticuloUnidadMedida aum : unidadesMedida) {
					
					ArticuloUnidadMedidaCodigo aumc = new ArticuloUnidadMedidaCodigo();
					
					switch(aum.getUnidadMedida_Id()) {
					case 1:
						aumc.setCodigo(row.getString("ITEAN13"));
						break;
					case 2:
						aumc.setCodigo(row.getString("ITDUN14"));
						break;
					case 3:
						aumc.setCodigo(row.getString("CODIGO"));
						break;
					}
					aum.addUnidadMedidaCodigo(aumc);
				}
				
				articulo.setUnidadesMedida(unidadesMedida);
				
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
	
	public List<ArticuloUnidadMedida> readUnidadesMedida(){
		
		List<ArticuloUnidadMedida> unidadesMedida = new ArrayList<ArticuloUnidadMedida>();
				
		DBFReader reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitmedida.dbf"));
			
			DBFRow row;
			
			int i = 0;
			while ((row = reader.nextRow()) != null && i <= 3) {
								
				ArticuloUnidadMedida unidadMedida = new ArticuloUnidadMedida();
					
				unidadMedida.setAlto(row.getInt("MEDALTO"));
				unidadMedida.setAncho(row.getInt("MEDANCHO"));
				unidadMedida.setPeso(row.getInt("MEDPESO"));
				unidadMedida.setProfundo(row.getInt("MEDLARGO"));
				
				int aux_id = Integer.parseInt(row.getString("MEDCODIGO") + Integer.toString(row.getInt("MEDTIPO")));
				unidadMedida.setUnidadMedida_Id(aux_id);
				
				unidadMedida.setUnidadMedida_Id(row.getInt("MEDTIPO"));

				int auxTipo = row.getInt("MEDTIPO");
				switch(auxTipo) {
				case 1:
					unidadMedida.setUnidades(this.getUnidad(row.getString("MEDCODIGO")));
					break;
				case 2:
					unidadMedida.setUnidades(this.getUnidadPorBulto(row.getString("MEDCODIGO")));
					break;
				case 3:
					unidadMedida.setUnidades(this.getUnidadPorPallet(row.getString("MEDCODIGO")));
					break;
				}
				
				unidadesMedida.add(unidadMedida);
				i++;
			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			DBFUtils.close(reader);
		}
		
		return unidadesMedida;
	}
	
	
	public List<ArticuloUnidadMedida> readUnidadesMedidaPorArticulo(String codigoArticulo){
		
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

				int auxTipo = row.getInt("MEDTIPO");
				switch(auxTipo) {
				case 1:
					unidadMedida.setUnidades(this.getUnidad(codigoArticulo));
					break;
				case 2:
					unidadMedida.setUnidades(this.getUnidadPorBulto(codigoArticulo));
					break;
				case 3:
					unidadMedida.setUnidades(this.getUnidadPorPallet(codigoArticulo));
					break;
				}
				
				unidadesMedida.add(unidadMedida);
			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			DBFUtils.close(reader);
		}
		
		return unidadesMedida;
	}
	
	
	public List<ArticuloUnidadMedidaCodigo> readUnidadesMedidaCodigo(){
		
		List<ArticuloUnidadMedidaCodigo> unidadesMedidaCodigo = new ArrayList<ArticuloUnidadMedidaCodigo>();
				
		DBFReader reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitems.dbf"));
			
			DBFRow row;

			while ((row = reader.nextRow()) != null) {
								
				ArticuloUnidadMedidaCodigo unidadMedidaCodigo = new ArticuloUnidadMedidaCodigo();
									
				unidadMedidaCodigo.setCodigo(row.getString("ITEAN13"));
				
				unidadesMedidaCodigo.add(unidadMedidaCodigo);
				
				unidadMedidaCodigo.setCodigo(row.getString("ITDUN14"));
				
				unidadesMedidaCodigo.add(unidadMedidaCodigo);
				
			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			DBFUtils.close(reader);
		}
		
		return unidadesMedidaCodigo;
	}
	
	
	public int getUnidad(String codigoArticulo){
		
		DBFReader reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitems.dbf"));
			
			DBFRow row;

			while ((row = reader.nextRow()) != null) {
				
				if (row.getString("CODIGO").equals(codigoArticulo)) {
					int unidad = row.getInt("UNIDAD");
					return unidad == 0 ? 1 : unidad;	
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
		
		return 0;
		
	}
	
	public int getUnidadPorBulto(String codigoArticulo){
		
		DBFReader reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitems.dbf"));
			
			DBFRow row;

			while ((row = reader.nextRow()) != null) {
				
				if (row.getString("CODIGO").equals(codigoArticulo)) {
					int unidad = row.getInt("UNIDAD");
					unidad = unidad == 0 ? 1 : unidad;
					
					int fraccion = row.getInt("ITFRACCION");
					fraccion = fraccion == 0 ? 1 : fraccion;
					
					return unidad * fraccion;	
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
		
		return 0;
		
	}
	
	public int getUnidadPorPallet(String codigoArticulo){
		
		DBFReader reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitems.dbf"));
			
			DBFRow row;

			while ((row = reader.nextRow()) != null) {
				
				if (row.getString("CODIGO").equals(codigoArticulo)) {
					int unidad = row.getInt("UNIDAD");
					unidad = unidad == 0 ? 1 : unidad;
					
					int fraccion = row.getInt("ITFRACCION");
					fraccion = fraccion == 0 ? 1 : fraccion;
					
					int bltxpal = row.getInt("ITBLTXPAL");
					bltxpal = bltxpal == 0 ? 1 : bltxpal;
					
					return unidad * fraccion * bltxpal;
					
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
		
		return 0;
		
	}
	
	public String getEAN13(String codigoArticulo){
		
		DBFReader reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitems.dbf"));
			
			DBFRow row;

			while ((row = reader.nextRow()) != null) {
				
				if (row.getString("CODIGO").equals(codigoArticulo)) {
					return row.getString("ITEAN13");
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
		
		return "";
		
	}
	
	public String getDUN14(String codigoArticulo){
		
		DBFReader reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream("//192.168.90.2/visual/dtitems.dbf"));
			
			DBFRow row;

			while ((row = reader.nextRow()) != null) {
				
				if (row.getString("CODIGO").equals(codigoArticulo)) {
					return row.getString("ITDUN14");
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
		
		return "";
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

