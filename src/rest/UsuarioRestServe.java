package rest;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import business.UsuarioBusiness;
import conf.EmailInfo;
import dto.UsuarioDTO;

@Path("usuario")
public class UsuarioRestServe  extends BasicRestServe
{

	@Inject
	private UsuarioBusiness usuarioBusiness;

	@POST
	@Path("participate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response participate(Map<String, Object> postObject)
	{
		try
		{	
			String response = usuarioBusiness.createCoupon(postObject);	

			if(response.contains("id:")) return ok(response);		
			else return error(response, BasicRestServe.INTERNAL_ERROR);		
		}
		catch (Exception e)
		{	
			return error("Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.BAD_REQUEST);
		}
	}	

	@POST
	@Path("invitation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response invitation(Map<String, Object> postObject)
	{
		try
		{	
			String invitation = (String) postObject.get("codigo") != null ? (String) postObject.get("codigo") : null;				
			invitation = invitation != null? invitation.trim(): null;

			UsuarioDTO user = usuarioBusiness.getInfoUserByCodigo(invitation);
			if(user != null) return ok(user);		
			else return error("Não foi localizado o número do cupom fornecido", BasicRestServe.UNAUTHORIZED);		
		}
		catch (Exception e)
		{	
			return error("Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.BAD_REQUEST);
		}
	}	

}
