package rest;

import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.ws.rs.core.Response;

public class BasicRestServe
{
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int INTERNAL_ERROR = 500;

	protected Response ok(String message)
	{		
		if(message == null) message = "Erro n�o especificado!";
		return Response.ok(Json.createObjectBuilder().add("status", true).add("message", message).build()).build();	
	}
	
	protected Response ok(Object object)
	{		
		Map<String, Object> send = new HashMap<String, Object>();
		
		if(object == null)
		{
			send.put("entity", null);
			send.put("status", false);
			send.put("message", "Erro n�o especificado!");		
			return Response.ok(send).build();	
		}
			
		send.put("entity", object);
		send.put("status", true);
		send.put("message", "Arquivo encontrado");			
		return Response.ok(send).build();	
	}
	
	protected Response error(String message, int basicRestServeType)
	{					
		return Response.serverError().status(basicRestServeType).entity(Json.createObjectBuilder().add("status", true).add("message", message).build()).build();
	}
}
