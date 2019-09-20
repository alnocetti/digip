package com.next.digip.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.next.digip.controller.ControllerLocal;
import com.next.digip.dbf.reader.Reader;
import com.next.digip.exceptions.ExceptionRestClient;
import com.next.digip.model.Articulo;
import com.next.digip.rest.RestClient;
import com.next.digip.rest.WebResponse;

public class WindowSync extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ControllerLocal controller = ControllerLocal.getInstance();
	private Reader dbfReader;

	
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
		this.dbfReader = new Reader();
		
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
		comboBox.addItem("Unidades de medida");
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
		if(e.getSource().equals(this.comboBox)) {
			// change when select something in combobox
			System.out.println(this.comboBox.getSelectedIndex());
			
		}
		
		if(e.getSource().equals(this.btnOk)) {
			
			switch(this.comboBox.getSelectedIndex()) {
			
			case 0:
				
				try {
					
					contentPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					
					this.textAreaResponse.append("**** Sincronizando articulos ****\n");
					
					this.textAreaResponse.append("--> Bajando articulos\n");
					
					List<Articulo> articulosPatagonia = this.restClient.getArticulos();
					
					this.textAreaResponse.append("--> Leyendo articulos\n");
					
					List<Articulo> articulos = this.dbfReader.readArticulos();
					
					List<WebResponse> responses = new ArrayList<WebResponse>();
					
					int i = 0;
							
					for (Articulo articulo : articulos) {
							
						boolean existe = false;
						
						if (i <= 12) {
							
						this.textAreaResponse.append("--> Enviando articulo:" + articulo.getCodigo() + " - " + articulo.getDescripcion() + "\n");

						for(Articulo articuloPatagonia : articulosPatagonia) {

							if (articulo.getCodigo().equals(articuloPatagonia.getCodigo())) {
								
								existe = true;
								break;
								
							}
						}
						
						if (existe) {

							WebResponse webResponse = this.restClient.putArticulo(articulo);
							this.textAreaResponse.setBackground(Color.red);
							this.textAreaResponse.append("Respuesta:" + webResponse.getResponseMessage() + "\n");

							responses.add(webResponse);
							
						}else {
							
							this.textAreaResponse.setBackground(Color.white);
							WebResponse webResponse = this.restClient.postArticulo(articulo);
							this.textAreaResponse.append("Respuesta:" + webResponse.getResponseMessage() + "\n");

							responses.add(webResponse);
						}
						
						}
						i = i + 1;	
							
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExceptionRestClient e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				this.textAreaResponse.append("**** Finalizo. ****\n");
			}
			
		}
	}

}
