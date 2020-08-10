package com.next.digip.rest;
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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.next.digip.controller.ControllerLocal;
import com.next.digip.dbf.reader.Reader;
import com.next.digip.main.Application;
import com.next.digip.model.Articulo;
import com.next.digip.model.ArticuloUnidadMedida;
import com.next.digip.model.ArticuloUnidadMedidaCodigo;
import com.next.digip.model.Cliente;
import com.next.digip.model.ClienteUbicacion;
import com.next.digip.model.Contenedor;
import com.next.digip.model.Despacho;
import com.next.digip.model.Pedido;
import com.next.digip.model.PedidoDetalle;

public class RestClient {
	
	public URL url;//your url i.e fetch data from .
	public HttpURLConnection conn;
	public Reader reader;
	private int intentos;
	
	public RestClient() {
		super();
		this.reader = new Reader();
	}
		
	public WebResponse testWebService(String uri, String method) throws IOException, RuntimeException{
			
//		try {
			WebResponse webResponse = new WebResponse();
			
			url = new URL(uri);//your url i.e fetch data from .
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			
			// seteo api-key 
			//conn.addRequestProperty("X-API-Key", Application.API_KEY);
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
						
			conn.setRequestProperty("Accept", "application/json");
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
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
		
		
		ArrayList<Articulo> articulos = new ArrayList<Articulo>();
		
		intentos = 0;
		
		while(intentos <= 1) {

			System.out.println("<-- getArticulos()");
		
		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			URL url = new URL("http://api.patagoniawms.com/v1/Articulos");//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
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
			intentos++;
			continue;
			
		}
		
		return articulos;
		
		}
		
		return articulos;
		
	}
	
	public List<ArticuloUnidadMedida> getUnidadesMedida(String codigoArticulo){
		
		
		ArrayList<ArticuloUnidadMedida> unidadesMedida = new ArrayList<ArticuloUnidadMedida>();
		
		intentos = 0;
		
		while(intentos <= 1) {

			System.out.println("<-- getUnidadesMedida(" + codigoArticulo + ")" );
		
		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
										
			URL url = new URL("http://api.patagoniawms.com/v1/Articulos/" + codigoArticulo + "/UnidadMedida");//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			if (conn.getResponseCode() != 200) {
				
				//no existe unidad de medida para el articulo
				if (conn.getResponseCode() == 400)
					return unidadesMedida;
				
				throw new RuntimeException("Failed : HTTP Error code : "
						
						+ conn.getResponseCode() + "-" + conn.getResponseMessage());
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			TypeToken<ArrayList<ArticuloUnidadMedida>> token = new TypeToken<ArrayList<ArticuloUnidadMedida>>() {};
			
			
			while ((output = br.readLine()) != null) {
								
				unidadesMedida =  gson.fromJson(output,  token.getType());
				
			}
						
			conn.disconnect();


		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
			
		}	
		
		return unidadesMedida;
		}
		return unidadesMedida;
	}
	
	public List<ArticuloUnidadMedidaCodigo> getUnidadesMedidaCodigo(String codigoArticulo, int id_unidadMedida){
		
		ArrayList<ArticuloUnidadMedidaCodigo> codigos = new ArrayList<ArticuloUnidadMedidaCodigo>();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- getUnidadesMedidaCodigo(" + codigoArticulo + ", " + Integer.toString(id_unidadMedida) + ")" );
			
		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
										
			URL url = new URL("http://api.patagoniawms.com/v1/Articulos/" + codigoArticulo + "/UnidadMedida/" + id_unidadMedida + "/Codigos");//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			if (conn.getResponseCode() != 200) {
				
				//no existe codigo para la unidad de medida del articulo
				if (conn.getResponseCode() == 400)
					return codigos;
				
				throw new RuntimeException("Failed : HTTP Error code : "
						
						+ conn.getResponseCode() + "-" + conn.getResponseMessage());
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			TypeToken<ArrayList<ArticuloUnidadMedidaCodigo>> token = new TypeToken<ArrayList<ArticuloUnidadMedidaCodigo>>() {};
						
			while ((output = br.readLine()) != null) {
								
				codigos =  gson.fromJson(output,  token.getType());
				
			}
						
			conn.disconnect();


		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}	
		
		return codigos;
		}
		return codigos;
	}
	
	public List<Cliente> getClientes(){
				
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- getClientes()");
			
		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			URL url = new URL("http://api.patagoniawms.com/v1/Clientes");//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.addRequestProperty("X-API-Key", Application.API_KEY);

			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
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
			intentos++;
			continue;
		}
		
		return clientes;
		}
		return clientes;
	}
	
	public List<ClienteUbicacion> getUbicaciones(){
				
		ArrayList<ClienteUbicacion> ubicaciones = new ArrayList<ClienteUbicacion>();
		
		intentos = 0;
		
		while(intentos <= 1) {
			
			System.out.println("<-- getUbicaciones()");

		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			URL url = new URL("http://api.patagoniawms.com/v1/ClientesUbicaciones");//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
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
			intentos++;
			continue;
		}
		
		return ubicaciones;
		}
		return ubicaciones;
	}
	
	public List<ClienteUbicacion> getClienteUbicacion(String codigo) {
				
		List<ClienteUbicacion> clienteUbicacion = new ArrayList<ClienteUbicacion>();
		
		intentos = 0;
		
		while(intentos <= 1) {
				
			System.out.println("<-- getUbicacion(" + codigo + ")" );

		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			URL url = new URL("http://api.patagoniawms.com/v1/Cliente/" + codigo + "/ClientesUbicaciones/" + codigo);//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			if (conn.getResponseCode() != 200) {
				
				//no existe ubicacion para el cliente
				if (conn.getResponseCode() == 400) {
					return clienteUbicacion;
					
				}else {
				
					throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode() + "-" + conn.getResponseMessage());
			
				}
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			TypeToken<ArrayList<ClienteUbicacion>> token = new TypeToken<ArrayList<ClienteUbicacion>>() {};
			
			while ((output = br.readLine()) != null) {
								
				clienteUbicacion =  gson.fromJson(output,  token.getType());
				
			}
			
			conn.disconnect();

		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return clienteUbicacion;
		}
		return clienteUbicacion;
		
	}
	
	
	public List<Pedido> getPedidos(String estado) {
		
		List<Pedido> pedidos = new ArrayList<Pedido>();
		
		intentos = 0;
		
		while(intentos <= 1) {
				
			System.out.println("<-- getPedidos()" );

		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			URL url = new URL("http://api.patagoniawms.com/v1/Pedidos?PedidoEstado=" + estado);//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			if (conn.getResponseCode() != 200) {
				
				//no existe ubicacion para el cliente
				if (conn.getResponseCode() == 400) {
					return pedidos;
					
				}else {
				
					throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode() + "-" + conn.getResponseMessage());
			
				}
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			TypeToken<ArrayList<Pedido>> token = new TypeToken<ArrayList<Pedido>>() {};
			
			while ((output = br.readLine()) != null) {
								
				pedidos =  gson.fromJson(output,  token.getType());
				
			}
			
			conn.disconnect();

		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return pedidos;
		}
		return pedidos;
		
	}
	
	public Pedido getPedidoPorCodigo(String codigo) {
		
		Pedido pedido = new Pedido();
		
		intentos = 0;
		
		while(intentos <= 1) {
				
			System.out.println("<-- getPedido(" + codigo + ")" );

		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			URL url = new URL("http://api.patagoniawms.com/v1/Pedidos/" + codigo);//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			if (conn.getResponseCode() != 200) {
				
				//no existe ubicacion para el cliente
				if (conn.getResponseCode() == 400) {
					return pedido;
					
				}else {
				
					throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode() + "-" + conn.getResponseMessage());
			
				}
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			TypeToken<Pedido> token = new TypeToken<Pedido>() {};
			
			while ((output = br.readLine()) != null) {
								
				pedido =  gson.fromJson(output,  token.getType());
				
			}
			
			conn.disconnect();

		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return pedido;
		}
		return pedido;
		
	}
	
	public List<PedidoDetalle> getDetallePedido(String codigo) {
		
		List<PedidoDetalle> detalle = new ArrayList<PedidoDetalle>();
		
		intentos = 0;
		
		while(intentos <= 1) {
				
			System.out.println("<-- getPedidoDetalle(" + codigo + ")" );

		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			URL url = new URL("http://api.patagoniawms.com/v1/Pedidos/" + codigo + "/Detalle");//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			if (conn.getResponseCode() != 200) {
				
				//no existe ubicacion para el cliente
				if (conn.getResponseCode() == 400) {
					return detalle;
					
				}else {
				
					throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode() + "-" + conn.getResponseMessage());
			
				}
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			TypeToken<List<PedidoDetalle>> token = new TypeToken<List<PedidoDetalle>>() {};
			
			while ((output = br.readLine()) != null) {
								
				detalle =  gson.fromJson(output,  token.getType());
				
			}
			
			conn.disconnect();

		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return detalle;
		}
		return detalle;
		
	}
	
	
	public Despacho getDespacho(int codigoPedido) {
		
	Despacho despacho = new Despacho();
		
		intentos = 0;
		
		while(intentos <= 1) {
				
			System.out.println("<-- getDespacho(" + codigoPedido + ")" );

		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			URL url = new URL("http://api.patagoniawms.com/v1/Pedidos/" + codigoPedido + "/Despacho");//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			if (conn.getResponseCode() != 200) {
				
				//no existe ubicacion para el cliente
				if (conn.getResponseCode() == 400) {
					return despacho;
					
				}else {
				
					throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode() + "-" + conn.getResponseMessage());
			
				}
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			TypeToken<Despacho> token = new TypeToken<Despacho>() {};
			
			while ((output = br.readLine()) != null) {
								
				despacho =  gson.fromJson(output,  token.getType());
				
			}
			
			conn.disconnect();

		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return despacho;
		}
		return despacho;
		
	}
	

	public List<Contenedor> getContenedores(int codigoPedido) {
		
	List<Contenedor> contenedores = new ArrayList<Contenedor>();
		
		intentos = 0;
		
		while(intentos <= 1) {
				
			System.out.println("<-- getContenedores(" + codigoPedido + ")" );

		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			URL url = new URL("http://api.patagoniawms.com/v1/Pedidos/" + codigoPedido + "/Contenedores");//your url i.e fetch data from .
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Accept", "application/json");
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			if (conn.getResponseCode() != 200) {
				
				//no existe ubicacion para el cliente
				if (conn.getResponseCode() == 400) {
					return contenedores;
					
				}else {
				
					throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode() + "-" + conn.getResponseMessage());
			
				}
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			BufferedReader br = new BufferedReader(in);
			
			String output;
			
			TypeToken<List<Contenedor>> token = new TypeToken<List<Contenedor>>() {};
			
			while ((output = br.readLine()) != null) {
								
				contenedores =  gson.fromJson(output,  token.getType());
				
			}
			
			conn.disconnect();

		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return contenedores;
		}
		return contenedores;
		
	}
	
	
	
	public WebResponse postArticulo(Articulo articulo) throws IOException {
				
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- postArticulo(" + articulo.getCodigo() + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
					
			
			url = new URL("http://api.patagoniawms.com/v1/Articulos");//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoOutput(true);
									
			String auxi = gson.toJson(articulo);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
			bw.write(auxi);
			bw.flush();
			bw.close();
					
			InputStreamReader _is;
			BufferedReader br;
			
			if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
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

		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}
	
	public WebResponse putArticulo(Articulo articulo) throws IOException {
				
		WebResponse webResponse = new WebResponse();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		
		intentos = 0;
		
		while(intentos <= 1) {
				
			System.out.println("<-- putArticulo(" + articulo.getCodigo() + ")");

		try {
			
			url = new URL("http://api.patagoniawms.com/v1/Articulos/" + articulo.getCodigo());//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("PUT");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
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
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		
		return webResponse;

	}
	
	public WebResponse postArticuloUnidadMedida(String codigoArticulo, ArticuloUnidadMedida articuloUnidadMedida) throws IOException {
				
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
			
			System.out.println("<-- postArticuloUnidadMedida(" + codigoArticulo + ", " +  Integer.toString(articuloUnidadMedida.getUnidadMedida_Id()) + ")");

		try {

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
					
									
			url = new URL("http://api.patagoniawms.com/v1/Articulos/" + codigoArticulo + "/" + "UnidadMedida");//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoOutput(true);
			
	//		Writer writer =  new FileWriter("C:\\Users\\anocetti\\Desktop\\personalComercialJson.txt");
	//		gson.toJson(articulo, writer);
	//		writer.flush();
	//		writer.close();
					
			String auxi = gson.toJson(articuloUnidadMedida);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
			bw.write(auxi);
			bw.flush();
			bw.close();
					
			System.out.println(auxi);
			
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
				
				webResponse.setResponseMessage(conn.getResponseMessage() + ", UM enviada correctamente: " + Integer.toString(articuloUnidadMedida.getUnidadMedida_Id()));
				
			} else {
			     /* error from server */
				webResponse.setResponseMessage(aux);
				
			}
			
			conn.disconnect();
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}
	
	public WebResponse putArticuloUnidadMedida(String codigoArticulo, ArticuloUnidadMedida articuloUnidadMedida) throws IOException {
				
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
			
			System.out.println("<-- putArticuloUnidadMedida(" + codigoArticulo + ", " +  Integer.toString(articuloUnidadMedida.getUnidadMedida_Id()) + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
					
			String codigoUnidadMedida = Integer.toString(articuloUnidadMedida.getUnidadMedida_Id());
					
			url = new URL("http://api.patagoniawms.com/v1/Articulos/" + codigoArticulo + "/UnidadMedida/" + codigoUnidadMedida);//your url i.e fetch data from .
					
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("PUT");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
	
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoOutput(true);
					
			String auxi = gson.toJson(articuloUnidadMedida);
			
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
				
				webResponse.setResponseMessage(conn.getResponseMessage() + ", Articulo enviado correctamente: " + Integer.toString(articuloUnidadMedida.getUnidadMedida_Id()));
				
			} else {
			     /* error from server */
				webResponse.setResponseMessage(aux);
				
			}
			
			conn.disconnect();
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}

	
	
	
	public WebResponse postArticuloUnidadMedidaCodigo(String codigoArticulo, int id_unidadMedida, ArticuloUnidadMedidaCodigo articuloUnidadMedidaCodigo) throws IOException {
				
		WebResponse webResponse = new WebResponse();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		intentos = 0;
		
		while(intentos <= 1) {
				
			System.out.println("<-- postArticuloUnidadMedidaCodigo(" + codigoArticulo + ", " +  Integer.toString(id_unidadMedida) + ")");

		try {
		
			String codigoUnidadMedida = Integer.toString(id_unidadMedida);
					
			url = new URL("http://api.patagoniawms.com/v1/Articulos/" + codigoArticulo + "/UnidadMedida/" + codigoUnidadMedida + "/Codigos" );//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
	
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoOutput(true);
					
			String auxi = gson.toJson(articuloUnidadMedidaCodigo);
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
				
				webResponse.setResponseMessage(conn.getResponseMessage() + ", UMC enviado correctamente: " + Integer.toString(id_unidadMedida));
				
			} else {
			     /* error from server */
				webResponse.setResponseMessage(aux);
				
			}
			
			conn.disconnect();
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos ++;
			continue;
			
		}
		return webResponse;
		}
		return webResponse;
		
	}
	
	public WebResponse putArticuloUnidadMedidaCodigo(String codigoArticulo, int id_unidadMedida, ArticuloUnidadMedidaCodigo articuloUnidadMedidaCodigo) throws IOException {
		
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- putArticuloUnidadMedidaCodigo(" + codigoArticulo + ", " +  Integer.toString(id_unidadMedida) + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
					
					
			String codigoUnidadMedida = Integer.toString(id_unidadMedida);
					
			url = new URL("http://api.patagoniawms.com/v1/Articulos/" + codigoArticulo + "/UnidadMedida/" + codigoUnidadMedida + "/Codigos/" + articuloUnidadMedidaCodigo.getCodigo());//your url i.e fetch data from .
					
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("PUT");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
			
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoOutput(true);
					
			String auxi = gson.toJson(articuloUnidadMedidaCodigo);
					
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
				
				webResponse.setResponseMessage(conn.getResponseMessage() + ", UMC enviado correctamente: " + Integer.toString(id_unidadMedida));
				
			} else {
			     /* error from server */
				webResponse.setResponseMessage(aux);
				
			}
			
			conn.disconnect();
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}	
	
	public WebResponse postCliente(Cliente cliente) throws IOException {
				
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- postCliente(" + cliente.getCodigo() + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
					
			
			url = new URL("http://api.patagoniawms.com/v1/Clientes");//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
	
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
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
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}
		
	public WebResponse putCliente(Cliente cliente) throws IOException {
		
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- putCliente(" + cliente.getCodigo() + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
					
			
			url = new URL("http://api.patagoniawms.com/v1/Clientes/" + cliente.getCodigo());//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("PUT");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
	
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
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
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}
	
	public WebResponse postClienteUbicacion(String codigoCliente, ClienteUbicacion clienteUbicacion) throws IOException {
		
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- postClienteUbicacion(" + clienteUbicacion.getCodigo() + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
					
			
			url = new URL("http://api.patagoniawms.com/v1/Cliente/" + codigoCliente + "/ClientesUbicaciones" );//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
	
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
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
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}
	
	public WebResponse putClienteUbicacion(String codigoCliente, ClienteUbicacion clienteUbicacion) throws IOException {
		
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- putClienteUbicacion(" + clienteUbicacion.getCodigo() + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
					
			
			url = new URL("http://api.patagoniawms.com/v1/Cliente/" + codigoCliente + "/ClientesUbicaciones/" + clienteUbicacion.getCodigo());//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("PUT");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
	
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
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
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;
	}
	
	public WebResponse postPedido(Pedido pedido) throws IOException {
		
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- postPedido(" + pedido.getCodigo() + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
					
			
			url = new URL("http://api.patagoniawms.com/v1/Pedidos");//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
	
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoOutput(true);
			
			String auxi = gson.toJson(pedido);
			
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
				
				webResponse.setResponseMessage(conn.getResponseMessage() + ", Pedido enviado correctamente: " + pedido.getCodigo());
				
			} else {
			     /* error from server */
				webResponse.setResponseMessage(aux.substring(12, 62));
				ControllerLocal.getInstance().pedidoPendiente(Integer.valueOf(pedido.getCodigo()),webResponse.getResponseMessage());
				
			}
			
			conn.disconnect();
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}
		
	public WebResponse putPedido(Pedido pedido) throws IOException {
		
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- putPedido(" + pedido.getCodigo() + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
					
			
			url = new URL("http://api.patagoniawms.com/v1/Pedidos/" + pedido.getCodigo());//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("PUT");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
	
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoOutput(true);
					
			String auxi = gson.toJson(pedido);
			
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
				
				webResponse.setResponseMessage(conn.getResponseMessage() + ", Pedido enviado correctamente: " + pedido.getCodigo());
				
			} else {
			     /* error from server */
				webResponse.setResponseMessage(aux);
				
			}
			
			conn.disconnect();
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}
	
	public WebResponse postPedidoDetalle(PedidoDetalle detalle) throws IOException {
		
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- postPedidoDetalle(" + detalle.getCodigoArticulo() + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
					
			
			url = new URL("http://api.patagoniawms.com/v1/Pedidos/" + detalle.getCodigoPedido() + "/Detalle");//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
	
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoOutput(true);
			
			String auxi = gson.toJson(detalle);
			
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
				
				webResponse.setResponseMessage(conn.getResponseMessage() + ", Detalle Pedido enviado correctamente: " + detalle.getCodigoArticulo());
				
			} else {
			     /* error from server */
				webResponse.setResponseMessage(aux);
				
			}
			
			conn.disconnect();
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}
		
	public WebResponse putPedidoDetalle(PedidoDetalle detalle) throws IOException {
		
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- putPedidoDetalle(" + detalle.getCodigoArticulo() + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
					
			
			url = new URL("http://api.patagoniawms.com/v1/Pedidos/" + detalle.getCodigoPedido() + "/Detalle/" + detalle.getCodigoArticulo());//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("PUT");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
	
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoOutput(true);
					
			String auxi = gson.toJson(detalle);
			
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
				
				webResponse.setResponseMessage(conn.getResponseMessage() + ", Detalle Pedido enviado correctamente: " + detalle.getCodigoArticulo());
				
			} else {
			     /* error from server */
				webResponse.setResponseMessage(aux);
				
			}
			
			conn.disconnect();
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}
	
	public WebResponse remitirPedido(int codigoPedido) throws IOException {
		
		WebResponse webResponse = new WebResponse();
		
		intentos = 0;
		
		while(intentos <= 1) {
		
			System.out.println("<-- remitirPedido(" + codigoPedido + ")");

		try {
		
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
					
			
			url = new URL("http://api.patagoniawms.com/v1/Pedidos/" + codigoPedido + "/Remitido");//your url i.e fetch data from .
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("PUT");
			
			// seteo api-key 
			conn.addRequestProperty("X-API-Key", Application.API_KEY);
	
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
	
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoOutput(true);
					
			String auxi = gson.toJson(null);
			
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
				
				webResponse.setResponseMessage(conn.getResponseMessage() + ", Pedido remitido correctamente: " + codigoPedido);
				
			} else {
			     /* error from server */
				webResponse.setResponseMessage(aux);
				
			}
			
			conn.disconnect();
			
		} catch (Exception e) {
			
			System.out.println("Exception in NetClientGet:- " + e);
			intentos++;
			continue;
		}
		
		return webResponse;
		}
		return webResponse;

	}
	
	

}
