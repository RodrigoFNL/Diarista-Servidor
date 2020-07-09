package rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import business.BasicBusiness;

public abstract class BasicRestServe <E, B>
{
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int INTERNAL_ERROR = 500;	
	
	protected abstract BasicBusiness<E, B> business();
	
	@GET	
	@Path("all_active")	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<?> getAllActive()
	{	
		try
		{
			List<?> dtos = business().getAllActive();				
			if(dtos == null) return new ArrayList<Object>();
			else return dtos;			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ArrayList<Object>();
		}
	}
	
	protected Response ok(String message)
	{		
		if(message == null) message = "Erro não especificado!";
		return Response.ok(Json.createObjectBuilder().add("status", true).add("message", message).build()).build();	
	}
	
	protected Response ok(Object object)
	{		
		Map<String, Object> send = new HashMap<String, Object>();
		
		if(object == null)
		{
			send.put("entity", null);
			send.put("status", false);
			send.put("message", "Erro não especificado!");		
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
