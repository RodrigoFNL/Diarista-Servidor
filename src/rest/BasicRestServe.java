package rest;

import javax.json.Json;
import javax.ws.rs.core.Response;

public class BasicRestServe
{

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
	
	protected Response error(String message, int status)
	{					
		return Response.serverError().status(status).entity(Json.createObjectBuilder().add("status", true).add("message", message).build()).build();
	}
}
