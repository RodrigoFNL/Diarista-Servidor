package business;

import javax.ejb.Stateful;
import javax.json.Json;
import javax.json.JsonObject;

@Stateful
public class ParticiparBusiness 
{
	
	public JsonObject createCoupon()
	{
		return Json.createObjectBuilder().add("status", "OK").add("message", "Gerado com Coupon sucesso, acesse seu email").build();
	}

}
