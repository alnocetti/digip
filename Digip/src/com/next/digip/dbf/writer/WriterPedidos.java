package com.next.digip.dbf.writer;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Observable;

import com.linuxense.javadbf.DBFWriter;
import com.next.digip.main.Application;
import com.next.digip.model.Pedido;
import com.next.digip.model.PedidoDetalle;

public class WriterPedidos extends Observable{
	
	public WriterPedidos() {
		super();
	}
	
	public void writePedido(Pedido pedido) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		DBFWriter writer = null;
			
		writer = new DBFWriter(new File(Application.DIR_LECTURA + "dtreccab.dbf"));
		
//		inicializarCabeza(writer);
		
		Object rowData[] = new Object[9];
		
		rowData[0] = Integer.valueOf(pedido.getCodigo());
		rowData[1] = pedido.getCodigoClienteUbicacion();
		rowData[2] = pedido.getPedidoEstado();
		try {
			rowData[3] = format.parse(pedido.getFecha().substring(0, 10));
			rowData[4] = format.parse(pedido.getFechaEstimadaEntrega().substring(0, 10));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		rowData[5] = pedido.getObservacion();
		rowData[6] = pedido.getImporte();
		rowData[7] = pedido.getCodigoDespacho();
		
		writer.addRecord(rowData);
		
		writer.close();
		
		return;
		
	}
	
	public void writeDetalle(PedidoDetalle pedidoDetalle) {
				
		DBFWriter writer = null;
		
		writer = new DBFWriter(new File(Application.DIR_LECTURA + "dtreccpo.dbf"));
		
//		inicializarCuerpo(writer);
		
		Object rowData[] = new Object[5];
		
		rowData[0] = Integer.valueOf(pedidoDetalle.getCodigoPedido());
		rowData[1] = pedidoDetalle.getCodigoArticulo();
		rowData[2] = pedidoDetalle.getUnidades();
		rowData[3] = pedidoDetalle.getUnidadesSatisfecha();
		rowData[4] = pedidoDetalle.getMinimoDiasVencimiento();
		
		writer.addRecord(rowData);
		
		writer.close();
		
		return;
		
	}
	
}
