package rest;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import business.ParticipeBusiness;

import javax.json.JsonObject;

public class ParticipeRestService
{	
	
	@Inject
	private ParticipeBusiness particeBusiness;
	
	@POST
	@Path("send_info")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendInfo(Map<String, Object> postObject)
	{
		try
		{
			JsonObject object = particeBusiness.getJson();			
			return Response.ok(object).build();		
		}
		catch (Exception e)
		{	
			return Response.serverError().status(400).entity("Erro de Conexão com a Base de Dados").build();
		}
	}

}
