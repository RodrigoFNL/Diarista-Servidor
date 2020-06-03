package conf;


import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("model")
public class ModelRest 
{

	@Path("html")
	@POST
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHTML()
	{
		return "<h1>TEXTO HTML</h1>";
	}
	
	@Path("text")
	@POST
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getText()
	{
		return "TEXTO";
	}
		
	@Path("json")
	@POST
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getJSON()
	{
		JsonObject object = Json.createObjectBuilder().add("josn", "Json message").add("message", "mensagem do texto json").build();			
		return object.toString();
	}

}
