package com.next.digip.view;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.next.digip.controller.ControllerLocal;
import com.next.digip.exceptions.ExceptionRestClient;
import com.next.digip.rest.WebResponse;

public class WindowTestWebServices extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnOk;
	private JTextField txtUri;
	private JComboBox<String> comboBox;
	private JRadioButton rdbtnPost;
	private JRadioButton rdbtnGet;
	private TextArea textAreaModel;
	private TextArea textAreaResponse;
	
	private ControllerLocal controller = ControllerLocal.getInstance();
	
	/**
	 * Create the frame.
	 */
	public WindowTestWebServices() {
		setTitle("Test");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 652, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTestWebServices = new JLabel("Test Web Services");
		lblTestWebServices.setBounds(10, 11, 202, 18);
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
		comboBox.addActionListener(this);
		
		ButtonGroup btnGroup = new ButtonGroup();
		
		
		btnOk = new JButton("Test");
		btnOk.setBounds(10, 343, 89, 23);
		contentPane.add(btnOk);
		btnOk.addActionListener(this);
		
		JLabel lblMetodo = new JLabel("Metodo:");
		lblMetodo.setBounds(242, 58, 46, 14);
		contentPane.add(lblMetodo);
		
		rdbtnGet = new JRadioButton("GET");
		rdbtnGet.setBounds(294, 54, 109, 23);
		contentPane.add(rdbtnGet);
		
		rdbtnPost = new JRadioButton("POST");
		rdbtnPost.setBounds(294, 80, 73, 23);
		contentPane.add(rdbtnPost);
		
		btnGroup.add(rdbtnGet);
		btnGroup.add(rdbtnPost);
		rdbtnGet.addActionListener(this);
		rdbtnPost.addActionListener(this);
		
		JLabel lblRespuesta = new JLabel("Respuesta:");
		lblRespuesta.setBounds(10, 132, 89, 14);
		contentPane.add(lblRespuesta);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(325, 132, 289, 213);
		contentPane.add(tabbedPane);
		
		JPanel panelModel = new JPanel();
		tabbedPane.addTab("Model", null, panelModel, null);
		panelModel.setLayout(null);
		
		textAreaModel = new TextArea();
		textAreaModel.setBounds(0, 0, 284, 181);
		panelModel.add(textAreaModel);
		
		JPanel panelExample = new JPanel();
		tabbedPane.addTab("Example", null, panelExample, null);
		panelExample.setLayout(null);
		
		TextArea textAreaExample = new TextArea();
		textAreaExample.setBounds(0, 0, 284, 181);
		panelExample.add(textAreaExample);
		
		JLabel lblUri = new JLabel("URI:");
		lblUri.setBounds(10, 95, 46, 14);
		contentPane.add(lblUri);
		
		txtUri = new JTextField();
		txtUri.setBounds(61, 92, 173, 20);
		contentPane.add(txtUri);
		txtUri.setColumns(10);
		
		textAreaResponse = new TextArea();
		textAreaResponse.setBounds(10, 152, 224, 185);
		contentPane.add(textAreaResponse);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.comboBox)) {
			
			// get URI for WebService
			this.txtUri.setText(this.getUri(this.comboBox.getSelectedIndex()));
			this.txtUri.repaint();
			
			// get Model
			this.textAreaModel.setText(this.getModel(this.comboBox.getSelectedIndex()));
			this.textAreaModel.repaint();
			
		}	
		
		if(e.getSource().equals(this.rdbtnGet)) {	
			
		}
		
		if(e.getSource().equals(this.btnOk)) {
		

			String method = "";

			if(this.rdbtnGet.isSelected()) {
				method = "GET";
			}else {
				if(this.rdbtnPost.isSelected()) {
					method = "POST";
				}else {
			        JOptionPane.showMessageDialog(null, "Debe seleccionar un metodo", "Error: ", JOptionPane.ERROR_MESSAGE);
			        return;
				}
			}
			if(this.txtUri.getText().equals("")) {
		        JOptionPane.showMessageDialog(null, "Debe seleccionar una entidad", "Error: ", JOptionPane.ERROR_MESSAGE);
		        return;
			}
			List<WebResponse> responses;
			try {
//				webResponse = this.controller.testWebService(this.txtUri.getText(), method);
				
				contentPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				responses = this.controller.sincronizarArticulos();
				
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
				for(WebResponse webResponse : responses) {
					
					if(webResponse.getResponseCode() >= 400) {
						
						JOptionPane.showMessageDialog(null, webResponse.getResponseMessage(), "Error: ", JOptionPane.ERROR_MESSAGE);
						
					} else {
						
						JOptionPane.showMessageDialog(null, webResponse.getResponseMessage(), "Envio: ", JOptionPane.INFORMATION_MESSAGE);

					}
				}
				
			} catch (IOException | RuntimeException | ExceptionRestClient e1) {
				// TODO Auto-generated catch block
				
//				this.textAreaResponse.setText(e1.toString());
			} finally {	
//				this.textAreaResponse.setText(webResponse.getResponseMessage());
		
			}
		}
	}
	
	
	private String getUri(int option) {
		String res = "";
		switch(option) {
		case 0:
			res = "http://api.patagoniawms.com/v1/Articulos";
			break;
		case 1:
			res = "http://api.patagoniawms.com/v1/Articulos/{codigoArticulo}/UnidadMedida";
			break;
		case 2:
			res = "http://api.patagoniawms.com/v1/Clientes";
			break;
		case 3:
			res = "http://api.patagoniawms.com/v1/Cliente/{codigoCliente}/ClientesUbicaciones?ClienteId={codigoUbicacion}";
			break;
		case 4:
			res = "http://api.patagoniawms.com/v1/Pedidos";
			break;
		case 5:
			res = "http://api.patagoniawms.com/v1/Pedidos/{codigoPedido}/Detalle";
			break;
		case 6:
			res = "http://api.patagoniawms.com/v1/Stock";
			break;
		}
		return res;
	}
	
	private String getModel(int option) {
		String res = "";
		switch(option){
			case 0:
				res = "Articulo {\r\n" + 
						"Codigo (string, optional),\r\n" + 
						"Descripcion (string),\r\n" + 
						"DiasVidaUtil (integer, optional),\r\n" + 
						"UsaLote (boolean, optional),\r\n" + 
						"UsaSerie (boolean, optional),\r\n" + 
						"UsaVencimiento (boolean, optional),\r\n" + 
						"ArticuloTipoRotacion (string) = ['alta', 'media', 'baja'],\r\n" + 
						"Activo (boolean, optional),\r\n" + 
						"ArticuloUnidadMedida (Array[ArticuloUnidadMedida], optional)\r\n" + 
						"}";
				break;
			case 1:
				res = "ArticuloUnidadMedida {\r\n" + 
						"UnidadMedida (string, optional) = ['unidad', 'caja', 'pallet', 'kilogramo', 'litro', 'metro', 'mililitro', 'display'],\r\n" + 
						"Unidades (integer, optional),\r\n" + 
						"EsUnidadDeVenta (boolean, optional),\r\n" + 
						"EsUnidadMenor (boolean, optional),\r\n" + 
						"EsUnidadConversion (boolean, optional),\r\n" + 
						"Alto (integer, optional),\r\n" + 
						"Ancho (integer, optional),\r\n" + 
						"Profundo (integer, optional),\r\n" + 
						"Peso (integer, optional),\r\n" + 
						"Activo (boolean, optional),\r\n" + 
						"ArticuloUnidadMedidaCodigo (Array[ArticuloUnidadMedidaCodigo], optional)\r\n" + 
						"}";
				break;
			case 2:
				res = "Cliente {\r\n" + 
						"Codigo (string),\r\n" + 
						"Descripcion (string),\r\n" + 
						"IdentificadorFiscal (string),\r\n" + 
						"Activo (boolean, optional),\r\n" + 
						"ClienteUbicacion (Array[ClienteUbicacion], optional)\r\n" + 
						"}";
				break;
			case 3: 
				res = "ClienteUbicacion {\r\n" + 
						"Codigo (string),\r\n" + 
						"CodigoCliente (string),\r\n" + 
						"Descripcion (string),\r\n" + 
						"Direccion (string),\r\n" + 
						"Provincia (string, optional),\r\n" + 
						"Localidad (string),\r\n" + 
						"Email (string, optional),\r\n" + 
						"InformacionAdicional (string, optional),\r\n" + 
						"InformacionEntrega (string, optional),\r\n" + 
						"Latitud (string, optional),\r\n" + 
						"Longitud (string, optional),\r\n" + 
						"DiaEntrega (string, optional) = ['domingo', 'lunes', 'martes', 'miercoles', 'jueves', 'viernes', 'sabado'],\r\n" + 
						"HorarioEntrega (string, optional),\r\n" + 
						"HorasAEntrega (number, optional),\r\n" + 
						"Activo (boolean, optional)\r\n" + 
						"}";
				break;
			case 4:
				res = "Pedido {\r\n" + 
						"Codigo (string),\r\n" + 
						"CodigoClienteUbicacion (string),\r\n" + 
						"PedidoEstado (string, optional) = ['pendiente', 'preparacion', 'eliminado', 'completo', 'remitido'],\r\n" + 
						"Fecha (string),\r\n" + 
						"FechaEstimadaEntrega (string, optional),\r\n" + 
						"Observacion (string, optional),\r\n" + 
						"Importe (number, optional),\r\n" + 
						"CodigoDespacho (string, optional),\r\n" + 
						"PedidoDetalle (Array[PedidoDetalle], optional)\r\n" + 
						"}";
				break;
			
			case 5:
				res = "PedidoDetalle {\r\n" + 
						"CodigoPedido (integer),\r\n" + 
						"CodigoArticulo (string),\r\n" + 
						"Unidades (integer),\r\n" + 
						"UnidadesSatisfecha (integer, optional),\r\n" + 
						"MinimoDiasVencimiento (integer, optional)\r\n" + 
						"}";
				break;
			
			case 6: 
				res = "Stock {\r\n" + 
						"Articulo_Id (integer, optional),\r\n" + 
						"UnidadesDisponibles (integer, optional),\r\n" + 
						"UnidadesReservadas (integer, optional),\r\n" + 
						"UnidadesBloqueadas (integer, optional),\r\n" + 
						"UnidadesADespachar (integer, optional),\r\n" + 
						"UnidadesEnRecepcion (integer, optional),\r\n" + 
						"UnidadesTransitoInterno (integer, optional)\r\n" + 
						"}";
				break;
		}
		return res;
	}
}
