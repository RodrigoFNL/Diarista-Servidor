package business;

import javax.ejb.Stateful;
import javax.json.Json;
import javax.json.JsonObject;

@Stateful
public class ParticiparBusiness 
{

	public JsonObject getJson() 
	{	
		return Json.createObjectBuilder().add("josn", "Json message").add("message", "mensagem do texto json").build();
	}

}
