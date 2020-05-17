package rest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("participar")
public class ParticiparRestService
{

	@Path("html")
	@POST
	@GET
	@Produces("text/html")
	public String getHTML()
	{
		return "<h1>TEXTO HTML</h1>";
	}
	
	@Path("text")
	@POST
	@GET
	@Produces("text/plain")
	public String getText()
	{
		return "TEXTO";
	}
		
	@Path("json")
	@POST
	@GET
	@Produces("application/json")
	public String getJSON()
	{
		JsonObject object = Json.createObjectBuilder().add("josn", "Json message").add("message", "mensagem do texto json").build();			
		return object.toString();
	}
}
