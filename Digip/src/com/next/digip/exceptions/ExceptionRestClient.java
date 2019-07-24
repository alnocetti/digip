package com.next.digip.exceptions;

import org.json.JSONException;
import org.json.JSONObject;

public class ExceptionRestClient extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JSONObject respuesta;

	public ExceptionRestClient(String arg0) {
		super(arg0);
		try {
			respuesta = new JSONObject(arg0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		try {
			return this.respuesta.getString("Message");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	

}
