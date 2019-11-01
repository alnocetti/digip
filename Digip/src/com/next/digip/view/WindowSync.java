package com.next.digip.view;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.next.digip.controller.ControllerLocal;
import com.next.digip.dbf.reader.ReaderArticulos;
import com.next.digip.dbf.reader.ReaderClientes;
import com.next.digip.exceptions.ExceptionRestClient;
import com.next.digip.model.Cliente;
import com.next.digip.model.ClienteUbicacion;
import com.next.digip.rest.RestClient;
import com.next.digip.rest.WebResponse;

public class WindowSync extends JFrame implements ActionListener, Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ReaderArticulos readerArticulos;
	private ReaderClientes readerClientes;

	private JPanel contentPane;
	private JComboBox<String> comboBox;
	private TextArea textAreaResponse;
	private JButton btnOk;
	private RestClient restClient;

	/**
	 * Create the frame.
	 */
	public WindowSync() {

		this.restClient = new RestClient();
		this.readerArticulos = new ReaderArticulos();
		this.readerClientes = new ReaderClientes();

		ControllerLocal.getInstance().addObserver(this);

		setTitle("Sync up files");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 537, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblTestWebServices = new JLabel("Sincronizacion de archivos");
		lblTestWebServices.setBounds(10, 11, 289, 18);
		lblTestWebServices.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lblTestWebServices);

		JLabel lblEntidad = new JLabel("Entidad:");
		lblEntidad.setBounds(10, 54, 46, 14);
		contentPane.add(lblEntidad);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(61, 52, 120, 18);
		comboBox.addItem("Articulos");
		comboBox.addItem("Clientes");
		comboBox.addItem("Ubicaciones");
		comboBox.addItem("Pedidos");
		comboBox.addItem("Detalle pedidos");
		comboBox.addItem("Stock");
		contentPane.add(comboBox);
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener(this);

		textAreaResponse = new TextArea();
		textAreaResponse.setBounds(10, 90, 501, 237);
		contentPane.add(textAreaResponse);

		btnOk = new JButton("Ok");
		btnOk.setBounds(10, 343, 89, 23);
		contentPane.add(btnOk);
		btnOk.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.comboBox)) {
			// change when select something in combobox
			System.out.println(this.comboBox.getSelectedIndex());

		}

		if (e.getSource().equals(this.btnOk)) {

			switch (this.comboBox.getSelectedIndex()) {

			case 0:

				try {

					ControllerLocal.getInstance().sincronizarArticulos();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExceptionRestClient e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				JOptionPane.showMessageDialog(null, "Proceso finalizado", "Envio datos",
						JOptionPane.INFORMATION_MESSAGE);
				break;

			case 1:

				try {

					contentPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));

					this.textAreaResponse.append("**** Sincronizando clientes ****\n");

					this.textAreaResponse.append("--> Bajando clientes \n");

					List<Cliente> clientesPatagonia = this.restClient.getClientes();

					this.textAreaResponse.append("--> Leyendo clientes\n");

					List<Cliente> clientes = this.readerClientes.readClientes();

					List<WebResponse> responses = new ArrayList<WebResponse>();

					int i = 0;

					for (Cliente cliente : clientes) {

						boolean existe = false;

						// if (i <= 1) {

						this.textAreaResponse.append("--> Enviando clientes:" + cliente.getCodigo() + " - "
								+ cliente.getDescripcion() + "\n");

						for (Cliente clientePatagonia : clientesPatagonia) {

							if (cliente.getCodigo().equals(clientePatagonia.getCodigo())) {

								existe = true;
								break;

							}
						}

						if (existe) {

							WebResponse webResponse = this.restClient.putCliente(cliente);
							this.textAreaResponse.append("Respuesta:" + webResponse.getResponseMessage() + "\n");

							responses.add(webResponse);

						} else {

							WebResponse webResponse = this.restClient.postCliente(cliente);
							this.textAreaResponse.append("Respuesta:" + webResponse.getResponseMessage() + "\n");

							responses.add(webResponse);
						}

					}
					i = i + 1;

					// }

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExceptionRestClient e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				this.textAreaResponse.append("**** Finalizo. ****\n");
				JOptionPane.showMessageDialog(null, "Proceso finalizado", "Envio datos",
						JOptionPane.INFORMATION_MESSAGE);
				break;

			case 2:

				try {

					contentPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));

					this.textAreaResponse.append("**** Sincronizando ubicaciones ****\n");

					// this.textAreaResponse.append("--> Bajando ubicaciones \n");

					// List<ClienteUbicacion> clientesUbicacionesPatagonia =
					// this.restClient.getUbicaciones();

					this.textAreaResponse.append("--> Leyendo clientes\n");

					List<ClienteUbicacion> clientesUbicaciones = this.readerClientes.readClientesUbicacion();

					List<WebResponse> responses = new ArrayList<WebResponse>();

					int i = 0;

					for (ClienteUbicacion clienteUbicacion : clientesUbicaciones) {

						// if (i <= 1) {

						this.textAreaResponse.append("--> Enviando clientes:" + clienteUbicacion.getCodigo() + " - "
								+ clienteUbicacion.getDescripcion() + "\n");

						List<ClienteUbicacion> clienteUbicacionPatagonia = this.restClient
								.getClienteUbicacion(clienteUbicacion.getCodigo());

						if (clienteUbicacionPatagonia != null) {

							WebResponse webResponse = this.restClient.putClienteUbicacion(clienteUbicacion);
							this.textAreaResponse.append("Respuesta:" + webResponse.getResponseMessage() + "\n");

							responses.add(webResponse);

						} else {

							WebResponse webResponse = this.restClient.postClienteUbicacion(clienteUbicacion);
							this.textAreaResponse.append("Respuesta:" + webResponse.getResponseMessage() + "\n");

							responses.add(webResponse);

						}

					}
					i = i + 1;

					// }

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExceptionRestClient e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				this.textAreaResponse.append("**** Finalizo. ****\n");
				JOptionPane.showMessageDialog(null, "Proceso finalizado", "Envio datos",
						JOptionPane.INFORMATION_MESSAGE);
				break;

			case 3:

			}

		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		this.textAreaResponse.append((String) arg1);
		// TODO Auto-generated method stub

	}

}
