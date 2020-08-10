package com.next.digip.dbf.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.yasas.xbase4j.XBase;
import org.yasas.xbase4j.XBaseFile;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import com.next.digip.main.Application;
import com.next.digip.model.Pedido;
import com.next.digip.model.PedidoDetalle;

public class ReaderPedidos {

	public ReaderPedidos() {
		super();
	}

	public List<Pedido> readPedidos() throws IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		SimpleDateFormat formatEstadoPedido = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		DBFReader reader = null;
		
		List<Pedido> pedidos = new ArrayList<Pedido>();

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream(Application.DIR_LECTURA + "dtenvcab.dbf"));
			
			DBFRow row;

			int registro = 0;
			
			while ((row = reader.nextRow()) != null) {
				
				registro ++;
				
				if(row.getString("CABESTADO").trim().equals("Enviado"))
					continue;

				Pedido pedido = new Pedido();
				
				pedido.setCodigo(Integer.toString(row.getInt("CABCODIGO")));

				pedido.setCodigoClienteUbicacion(row.getString("CABCODUBI"));
				
				pedido.setPedidoEstado(row.getString("CABESTADO"));
				
				if (row.getDate("CABFECHA") != null) 
					pedido.setFecha(format.format(row.getDate("CABFECHA")));
				
				if (row.getDate("CABENTREGA") != null)
					pedido.setFechaEstimadaEntrega(format.format(row.getDate("CABENTREGA")));
				
				pedido.setObservacion(row.getString("CABOBSERV"));
				
				pedido.setImporte(row.getFloat("CABIMPORTE"));
				
				pedido.setCodigoDespacho(row.getString("CABCODDES"));
				
				pedido.setPedidoDetalle(getDetallePedido(pedido.getCodigo()));
				
				pedidos.add(pedido);
				
				cambiarEstadoPedido(registro - 1, "Enviado", "Fecha envio: " + formatEstadoPedido.format(new Date()));

			}		
			
			DBFUtils.close(reader);

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 

		return pedidos;

	}
	
	private List<PedidoDetalle> getDetallePedido(String codigo) throws IOException{
		
		List<PedidoDetalle> detalles = new ArrayList<PedidoDetalle>();
		
		try {

			// create a DBFReader object
			DBFReader readerDetalle = new DBFReader(new FileInputStream(Application.DIR_LECTURA + "dtenvcpo.dbf"));

			DBFRow row;
			
			while ((row = readerDetalle.nextRow()) != null) {
								
				PedidoDetalle detalle = new PedidoDetalle();
				
				if (!Integer.toString(row.getInt("CPOCODIGO")).equals(codigo))
					continue;

				detalle.setCodigoPedido(codigo);
				
				detalle.setCodigoArticulo(row.getString("CPOCODART"));
				
				detalle.setUnidades(row.getInt("CPOCANTIDA"));
				
				detalle.setUnidadesSatisfecha(row.getInt("CPOCANSAT"));
				
				detalle.setMinimoDiasVencimiento(row.getInt("CPOMINVENC"));
				
				detalles.add(detalle);

			}
			
			DBFUtils.close(readerDetalle);

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 		
		return detalles;
	}
	
	public void cambiarEstadoPedido(int registro, String estado, String observacion) {
		
		try {
			
			XBaseFile writer = new XBase().open(new File(Application.DIR_LECTURA + "dtenvcab.dbf"));
			
			writer.go(registro);	
							
			writer.setValue("CABESTADO", estado);
					
			writer.setValue("CABOBSERV", observacion);
						
			writer.closeQuietly();			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getNroRegistro(int codigoPedido) {
		
		DBFReader readerNroRegistro = null;
		
		int registro = 0;
		

		// create a DBFReader object
		try {
			readerNroRegistro = new DBFReader(new FileInputStream(Application.DIR_LECTURA + "dtenvcab.dbf"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBFRow row;

		while ((row = readerNroRegistro.nextRow()) != null) {
					
			if(row.getInt("CABCODIGO") == codigoPedido) {
				
				return registro;
				
			}
			
			registro ++;

		}		
		
		DBFUtils.close(readerNroRegistro);

		return registro;
		
	}

}
