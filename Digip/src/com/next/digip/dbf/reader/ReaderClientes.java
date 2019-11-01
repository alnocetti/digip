package com.next.digip.dbf.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import com.next.digip.model.Cliente;
import com.next.digip.model.ClienteUbicacion;

public class ReaderClientes {

	private DBFReader reader;

	public ReaderClientes() {
		super();
	}

	public List<Cliente> readClientes() {

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
				} else {
					cliente.setActivo(false);
				}

				clientes.add(cliente);

			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			DBFUtils.close(reader);
		}

		return clientes;

	}

	public List<ClienteUbicacion> readClientesUbicacion() {

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
				} else {
					clienteUbicacion.setActivo(false);
				}

				clienteUbicacion.setCodigoCliente(row.getString("CLICODIGO"));

				clienteUbicacion.setDireccion(row.getString("CLIDOMICI"));

				clienteUbicacion.setProvincia(Integer.toString(row.getInt("CLIPROVIN")));

				clienteUbicacion.setLocalidad(row.getString("CLILOCALI"));

				clienteUbicacion.setInformacionAdicional(row.getString("CLIOBSERVA"));

				clienteUbicacion.setLatitud(row.getString("CLILATI"));

				clienteUbicacion.setLongitud(row.getString("CLILONG"));

				// clienteUbicacion.setHorarioEntrega(row.getString("CLIHORA"));

				clientesUbicacion.add(clienteUbicacion);

			}

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			DBFUtils.close(reader);
		}

		return clientesUbicacion;

	}

}
