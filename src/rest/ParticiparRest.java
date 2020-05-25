package rest;

import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("participate")
public class ParticiparRest 
{
	@POST
	@Path("send_info")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendInfo(Map<String, Object> postObject)
	{
		try
		{
			JsonObject object = Json.createObjectBuilder().add("josn", "Json message").add("message", "mensagem do texto json").build();			
			return Response.ok(object).build();		
		}
		catch (Exception e)
		{	
			return Response.serverError().status(400).entity("Erro de Conexão com a Base de Dados").build();
		}
	}	

}
