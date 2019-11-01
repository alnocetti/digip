package com.next.digip.dbf.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import com.next.digip.enums.EstadoPedido;
import com.next.digip.model.Pedido;
import com.next.digip.model.PedidoDetalle;

public class ReaderPedidos {

	private DBFReader reader;

	public ReaderPedidos() {
		super();
	}

	public List<Pedido> readPedidos() {

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

				if (pedidos.size() >= numeroPedido
						&& pedidos.get(numeroPedido - 1).getCodigoClienteUbicacion().equals(cliente)) {

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
		} finally {
			DBFUtils.close(reader);
		}

		return pedidos;

	}

}
