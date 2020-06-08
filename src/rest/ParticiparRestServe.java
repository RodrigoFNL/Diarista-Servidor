package rest;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import business.ParticiparBusiness;
import conf.EmailInfo;

@Path("participate")
public class ParticiparRestServe  extends BasicRestServe
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
			
			
			String response = participarBusiness.createCoupon(postObject);						
			return ok(response);		
		}
		catch (Exception e)
		{	
			return error("Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", 400);
		}
	}	

}
