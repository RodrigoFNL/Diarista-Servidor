package rest;

import javax.json.Json;
import javax.ws.rs.core.Response;

public class BasicRestServe
{
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int INTERNAL_ERROR = 500;

	protected Response ok(String message)
	{		
		if(message == null) message = "Erro não especificado!";
		return Response.ok(Json.createObjectBuilder().add("status", true).add("message", message).build()).build();	
	}
	
	protected Response ok(Object object)
	{		
		if(object == null) return Response.ok(Json.createObjectBuilder().add("status", false).add("message", "Erro não especificado!").build()).build();			
		return Response.ok(object).build();	
	}
	
	protected Response error(String message, int basicRestServeType)
	{					
		return Response.serverError().status(basicRestServeType).entity(Json.createObjectBuilder().add("status", true).add("message", message).build()).build();
	}
}
