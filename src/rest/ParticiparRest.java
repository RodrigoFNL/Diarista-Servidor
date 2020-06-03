package rest;

import java.util.Map;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import business.ParticiparBusiness;

@Path("participate")
public class ParticiparRest 
{
	
	@Inject
	private ParticiparBusiness participarBusiness;
	
	@POST
	@Path("send_info")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendInfo(Map<String, Object> postObject)
	{
		try
		{
			JsonObject object = participarBusiness.getJson();			
			return Response.ok(object).build();		
		}
		catch (Exception e)
		{	
			return Response.serverError().status(400).entity("Erro de Conexão com a Base de Dados").build();
		}
	}	

}
