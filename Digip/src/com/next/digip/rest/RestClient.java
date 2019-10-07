package com.next.digip.rest;
import static java.net.HttpURLConnection.HTTP_SERVER_ERROR;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.next.digip.dbf.reader.Reader;
import com.next.digip.exceptions.ExceptionRestClient;
import com.next.digip.model.Articulo;
import com.next.digip.model.Cliente;
import com.next.digip.model.ClienteUbicacion;
import com.next.digip.model.Pedido;

public class RestClient {
	
	public URL url;//your url i.e fetch data from .
	public HttpURLConnection conn;
	public Reader reader;
	
	public RestClient() {
		super();
		this.reader = new Reader();
		// TODO Auto-generated constructor stub
	}
		
	public WebResponse testWebService(String uri, String method) throws IOException, RuntimeException, ExceptionRestClient{
				
//		try {
			WebResponse webResponse = new WebResponse();
			
			url = new URL(uri);//your url i.e fetch data from .
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			
			// seteo api-key 
			//conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
			conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
			
						
			conn.setRequestProperty("Accept", "application/json");
			
			InputStreamReader _is;
			
			if (conn.getResponseCode() != 200) {
				
				if (conn.getResponseCode() < 500 && conn.getResponseCode() >= 300) {
					
					_is = new InputStreamReader(conn.getErrorStream());
					BufferedReader br = new BufferedReader(_is);
					
					String response = "";
					String line;
					
					while ((line = br.readLine()) != null) {
						response += line;
					}
					throw new ExceptionRestClient(response);
				}
				throw new RuntimeException("Failed : HTTP Error code : "
						+ conn.getResponseCode() + "-" + conn.getResponseMessage());
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				webResponse.setResponseMessage(webResponse.getResponseMessage() + "|" + output);
			}
			conn.disconnect();
			webResponse.setResponseCode(conn.getResponseCode());
			//webResponse.setResponseMessage(conn.getResponseMessage());
			
//		} catch (Exception e) {
//			System.out.println("Exception in NetClientGet:- " + e);
//		}
//		
		return webResponse;
	}

	public List<Articulo> getArticulos(){
		
		System.out.println("<-- getArticulos()");
		
		ArrayList<Articulo> articulos = new ArrayList<Articulo>();
		
//		Articulo articulos[];
		
		try {
			
			Gson gson = new Gson();

			URL url = new URL("http://api.patagoniawms.com/v1/Articulos");//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
			
			if (conn.getResponseCode() != 200) {
				
				throw new RuntimeException("Failed : HTTP Error code : "
						
						+ conn.getResponseCode() + "-" + conn.getResponseMessage());
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			TypeToken<ArrayList<Articulo>> token = new TypeToken<ArrayList<Articulo>>() {};
			
			while ((output = br.readLine()) != null) {
								
				articulos =  gson.fromJson(output,  token.getType());
				
			}
			
			conn.disconnect();

		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			
		}
		
		return articulos;
	}
	
	public List<Cliente> getClientes(){
		
		System.out.println("<-- getClientes()");
		
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		
//		Articulo articulos[];
		
		try {
			
			Gson gson = new Gson();

			URL url = new URL("http://api.patagoniawms.com/v1/Clientes");//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
			
			if (conn.getResponseCode() != 200) {
				
				throw new RuntimeException("Failed : HTTP Error code : "
						
						+ conn.getResponseCode() + "-" + conn.getResponseMessage());
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			TypeToken<ArrayList<Cliente>> token = new TypeToken<ArrayList<Cliente>>() {};
			
			while ((output = br.readLine()) != null) {
								
				clientes =  gson.fromJson(output,  token.getType());
				
			}
			
			conn.disconnect();

		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			
		}
		
		return clientes;
	}
	
	public List<ClienteUbicacion> getUbicaciones(){
		
		System.out.println("<-- getUbicaciones()");
		
		ArrayList<ClienteUbicacion> ubicaciones = new ArrayList<ClienteUbicacion>();
		
//		Articulo articulos[];
		
		try {
			
			Gson gson = new Gson();

			URL url = new URL("http://api.patagoniawms.com/v1/ClientesUbicaciones");//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
			
			if (conn.getResponseCode() != 200) {
				
				throw new RuntimeException("Failed : HTTP Error code : "
						
						+ conn.getResponseCode() + "-" + conn.getResponseMessage());
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			TypeToken<ArrayList<ClienteUbicacion>> token = new TypeToken<ArrayList<ClienteUbicacion>>() {};
			
			while ((output = br.readLine()) != null) {
								
				ubicaciones =  gson.fromJson(output,  token.getType());
				
			}
			
			conn.disconnect();

		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			
		}
		
		return ubicaciones;
	}
	
	public ClienteUbicacion getClienteUbicacion(String codigo) {
		
		System.out.println("<-- getUbicacion()");
		
		ClienteUbicacion clienteUbicacion = new ClienteUbicacion();
				
		try {
			
			Gson gson = new Gson();

			URL url = new URL("http://api.patagoniawms.com/v1/Cliente/" + codigo + "/ClientesUbicaciones/" + codigo);//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
			
			if (conn.getResponseCode() != 200) {
				
				if (conn.getResponseCode() < conn.HTTP_SERVER_ERROR) {
					
					return null;
					
				}else {
				
					throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode() + "-" + conn.getResponseMessage());
			
				}
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			//TypeToken<ArrayList<ClienteUbicacion>> token = new TypeToken<ArrayList<ClienteUbicacion>>() {};
			
			while ((output = br.readLine()) != null) {
								
				clienteUbicacion =  gson.fromJson(output,  ClienteUbicacion.class);
				
			}
			
			conn.disconnect();

		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			
		}
		
		return clienteUbicacion;
		
	}
	
	public WebResponse postArticulo(Articulo articulo) throws IOException, ExceptionRestClient {
		
		System.out.println("<-- postArticulo()" + articulo.getCodigo());
		
		Gson gson = new Gson();
				
		WebResponse webResponse = new WebResponse();
		
		url = new URL("http://api.patagoniawms.com/v1/Articulos");//your url i.e fetch data from .
		
		conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("POST");
		
		// seteo api-key 
		conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
		
		conn.setRequestProperty("Content-Type", "application/json");
		
		conn.setDoOutput(true);
		
		OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
		
		String auxi = gson.toJson(articulo);
		
		wr.write(auxi);
		
		wr.flush();
		
		InputStreamReader _is;
		BufferedReader br;
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
		    _is = new InputStreamReader(conn.getInputStream());
		} else {
		     /* error from server */
		    _is = new InputStreamReader(conn.getErrorStream());
		}
		
		br = new BufferedReader(_is);
		
		StringBuilder builder = new StringBuilder();
		
		String output;
		
		while ((output = br.readLine()) != null) {

			builder.append(output);
		
		}
		
		String aux = builder.toString();
		
		webResponse.setResponseCode(conn.getResponseCode());
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
			
			webResponse.setResponseMessage(conn.getResponseMessage() + ", Articulo enviado correctamente: " + articulo.getCodigo() + " - " + articulo.getDescripcion());
			
		} else {
		     /* error from server */
			webResponse.setResponseMessage(aux);
			
		}
		
		conn.disconnect();
		
		return webResponse;

	}
	
	public WebResponse putArticulo(Articulo articulo) throws IOException, ExceptionRestClient {
		
		System.out.println("<-- putArticulo()" + articulo.getCodigo());
		
		Gson gson = new Gson();
				
		WebResponse webResponse = new WebResponse();
		
		url = new URL("http://api.patagoniawms.com/v1/Articulos/" + articulo.getCodigo());//your url i.e fetch data from .
		
		conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("PUT");
		
		// seteo api-key 
		conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
		
		conn.setRequestProperty("Content-Type", "application/json");
		
		conn.setDoOutput(true);
				
		String auxi = gson.toJson(articulo);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		bw.write(auxi);
		bw.flush();
		bw.close();
		
		InputStreamReader _is;
		BufferedReader br;
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
		    _is = new InputStreamReader(conn.getInputStream());
		} else {
		     /* error from server */
		    _is = new InputStreamReader(conn.getErrorStream());
		}
		
		br = new BufferedReader(_is);
		
		StringBuilder builder = new StringBuilder();
		
		String output;
		
		while ((output = br.readLine()) != null) {

			builder.append(output);
		
		}
		
		String aux = builder.toString();
		
		webResponse.setResponseCode(conn.getResponseCode());
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
			
			webResponse.setResponseMessage(conn.getResponseMessage() + ", Articulo enviado correctamente: " + articulo.getCodigo() + " - " + articulo.getDescripcion());
			
		} else {
		     /* error from server */
			webResponse.setResponseMessage(aux);
			
		}
		
		conn.disconnect();
		
		return webResponse;

	}
	
	public WebResponse postCliente(Cliente cliente) throws IOException, ExceptionRestClient {
		
		System.out.println("<-- postCliente()" + cliente.getCodigo());
		
		Gson gson = new Gson();
				
		WebResponse webResponse = new WebResponse();
		
		url = new URL("http://api.patagoniawms.com/v1/Clientes");//your url i.e fetch data from .
		
		conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("POST");
		
		// seteo api-key 
		conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
		
		conn.setRequestProperty("Content-Type", "application/json");
		
		conn.setDoOutput(true);
		
		OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
		
		String auxi = gson.toJson(cliente);
		
		wr.write(auxi);
		
		wr.flush();
		
		InputStreamReader _is;
		BufferedReader br;
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
		    _is = new InputStreamReader(conn.getInputStream());
		} else {
		     /* error from server */
		    _is = new InputStreamReader(conn.getErrorStream());
		}
		
		br = new BufferedReader(_is);
		
		StringBuilder builder = new StringBuilder();
		
		String output;
		
		while ((output = br.readLine()) != null) {

			builder.append(output);
		
		}
		
		String aux = builder.toString();
		
		webResponse.setResponseCode(conn.getResponseCode());
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
			
			webResponse.setResponseMessage(conn.getResponseMessage() + ", Cliente enviado correctamente: " + cliente.getCodigo() + " - " + cliente.getDescripcion());
			
		} else {
		     /* error from server */
			webResponse.setResponseMessage(aux);
			
		}
		
		conn.disconnect();
		
		return webResponse;

	}
		
	public WebResponse putCliente(Cliente cliente) throws IOException, ExceptionRestClient {
		
		System.out.println("<-- putCliente()" + cliente.getCodigo());
		
		Gson gson = new Gson();
				
		WebResponse webResponse = new WebResponse();
		
		url = new URL("http://api.patagoniawms.com/v1/Clientes/" + cliente.getCodigo());//your url i.e fetch data from .
		
		conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("PUT");
		
		// seteo api-key 
		conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
		
		conn.setRequestProperty("Content-Type", "application/json");
		
		conn.setDoOutput(true);
				
		String auxi = gson.toJson(cliente);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		bw.write(auxi);
		bw.flush();
		bw.close();
		
		InputStreamReader _is;
		BufferedReader br;
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
		    _is = new InputStreamReader(conn.getInputStream());
		} else {
		     /* error from server */
		    _is = new InputStreamReader(conn.getErrorStream());
		}
		
		br = new BufferedReader(_is);
		
		StringBuilder builder = new StringBuilder();
		
		String output;
		
		while ((output = br.readLine()) != null) {

			builder.append(output);
		
		}
		
		String aux = builder.toString();
		
		webResponse.setResponseCode(conn.getResponseCode());
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
			
			webResponse.setResponseMessage(conn.getResponseMessage() + ", Cliente enviado correctamente: " + cliente.getCodigo() + " - " + cliente.getDescripcion());
			
		} else {
		     /* error from server */
			webResponse.setResponseMessage(aux);
			
		}
		
		conn.disconnect();
		
		return webResponse;

	}
	
	public WebResponse postClienteUbicacion(ClienteUbicacion clienteUbicacion) throws IOException, ExceptionRestClient {
		
		System.out.println("<-- postClienteUbicacion()" + clienteUbicacion.getCodigo());
		
		Gson gson = new Gson();
				
		WebResponse webResponse = new WebResponse();
		
		url = new URL("http://api.patagoniawms.com/v1/Cliente/" + clienteUbicacion.getCodigo() + "/ClientesUbicaciones" );//your url i.e fetch data from .
		
		conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("POST");
		
		// seteo api-key 
		conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
		
		conn.setRequestProperty("Content-Type", "application/json");
		
		conn.setDoOutput(true);
				
		String auxi = gson.toJson(clienteUbicacion);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		bw.write(auxi);
		bw.flush();
		bw.close();
		
		InputStreamReader _is;
		BufferedReader br;
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
		    _is = new InputStreamReader(conn.getInputStream());
		} else {
		     /* error from server */
		    _is = new InputStreamReader(conn.getErrorStream());
		}
		
		br = new BufferedReader(_is);
		
		StringBuilder builder = new StringBuilder();
		
		String output;
		
		while ((output = br.readLine()) != null) {

			builder.append(output);
		
		}
		
		String aux = builder.toString();
		
		webResponse.setResponseCode(conn.getResponseCode());
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
			
			webResponse.setResponseMessage(conn.getResponseMessage() + ", Ubicacion enviada correctamente: " + clienteUbicacion.getCodigo() + " - " + clienteUbicacion.getDescripcion());
			
		} else {
		     /* error from server */
			webResponse.setResponseMessage(aux);
			
		}
		
		conn.disconnect();
		
		return webResponse;

	}
	
	public WebResponse putClienteUbicacion(ClienteUbicacion clienteUbicacion) throws IOException, ExceptionRestClient {
		
		System.out.println("<-- putClienteUbicacion()" + clienteUbicacion.getCodigo());
		
		Gson gson = new Gson();
				
		WebResponse webResponse = new WebResponse();
		
		url = new URL("http://api.patagoniawms.com/v1/Cliente/" + clienteUbicacion.getCodigo() + "/ClientesUbicaciones/" + clienteUbicacion.getCodigo());//your url i.e fetch data from .
		
		conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("PUT");
		
		// seteo api-key 
		conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
		
		conn.setRequestProperty("Content-Type", "application/json");
		
		conn.setDoOutput(true);
				
		String auxi = gson.toJson(clienteUbicacion);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		bw.write(auxi);
		bw.flush();
		bw.close();
		
		InputStreamReader _is;
		BufferedReader br;
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
		    _is = new InputStreamReader(conn.getInputStream());
		} else {
		     /* error from server */
		    _is = new InputStreamReader(conn.getErrorStream());
		}
		
		br = new BufferedReader(_is);
		
		StringBuilder builder = new StringBuilder();
		
		String output;
		
		while ((output = br.readLine()) != null) {

			builder.append(output);
		
		}
		
		String aux = builder.toString();
		
		webResponse.setResponseCode(conn.getResponseCode());
		
		if (conn.getResponseCode() < conn.HTTP_BAD_REQUEST) {
			
			webResponse.setResponseMessage(conn.getResponseMessage() + ", Ubicacion enviada correctamente: " + clienteUbicacion.getCodigo() + " - " + clienteUbicacion.getDescripcion());
			
		} else {
		     /* error from server */
			webResponse.setResponseMessage(aux);
			
		}
		
		conn.disconnect();
		
		return webResponse;

	}
	
	// revisar metodos pedidos
	public List<Pedido> getPedidos(){
		
		List<Pedido> pedidos = new ArrayList<Pedido>();
		
		try {

			URL url = new URL("http://api.patagoniawms.com/v1/Pedidos");//your url i.e fetch data from .
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : "
						+ conn.getResponseCode() + "-" + conn.getResponseMessage());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();

		} catch (Exception e) {
			System.out.println("Exception in NetClientGet:- " + e);
		}
		
		return pedidos;
	}

	public void postPedidos(Pedido pedido) throws IOException, ExceptionRestClient {
		
		Gson gson = new Gson();
					
		WebResponse webResponse = new WebResponse();
		
		url = new URL("http://api.patagoniawms.com/v1/Pedidos");//your url i.e fetch data from .
		
		conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("PUT");
		
		// seteo api-key 
		conn.addRequestProperty("X-API-Key", "nr9H4yTB3WhnFMx8mnRW55nJgMTtFzr7");
		
		conn.setRequestProperty("Accept", "application/json");
		
		conn.setDoOutput(true);
		
		OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
		
		wr.write(gson.toJson(pedido));
					
		InputStreamReader _is;
		
		if (conn.getResponseCode() != 200) {
			
			if (conn.getResponseCode() < 500 && conn.getResponseCode() >= 300) {
				
				_is = new InputStreamReader(conn.getErrorStream());
				BufferedReader br = new BufferedReader(_is);
				
				String response = "";
				String line;
				
				while ((line = br.readLine()) != null) {
					response += line;
				}
				throw new ExceptionRestClient(response);
			}
			throw new RuntimeException("Failed : HTTP Error code : "
					+ conn.getResponseCode() + "-" + conn.getResponseMessage());
		}
		
		InputStreamReader in = new InputStreamReader(conn.getInputStream());
		BufferedReader br = new BufferedReader(in);
		String output;
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
		conn.disconnect();
		webResponse.setResponseCode(conn.getResponseCode());
		webResponse.setResponseMessage(conn.getResponseMessage());
	

	}

}
