package rest;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import business.BasicBusiness;
import business.UsuarioBusiness;
import conf.EmailInfo;
import dto.UsuarioDTO;
import model.Usuario;
import util.MultiPartUtils;

@RequestScoped
@Path("usuario")
public class UsuarioRestServe  extends BasicRestServe<Usuario>
{

	@Inject
	private UsuarioBusiness usuarioBusiness;

	@Override
	protected BasicBusiness<Usuario> business() 
	{	
		return usuarioBusiness;
	}		

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

	@POST
	@Path("register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response register(String object)
	{		
		try
		{
			Map<String, Object> map = MultiPartUtils.multiPart(object);			
			if(map == null) return error("Informações necessária incompleta", BasicRestServe.BAD_REQUEST);					
			String execute = usuarioBusiness.register(Usuario.getEntity(map));	
			
			if(execute == null) return ok("em construção");
			else return error(execute, BasicRestServe.INTERNAL_ERROR);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return error("OCORREU UM ERRO", BasicRestServe.INTERNAL_ERROR);
		}	
	}
	
	@GET
	@Path("download/{id}")
	//@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Produces("image/png")
	public Response download(@PathParam("id") String id)
	{		
		try
		{				
			byte[] arquivo = null;
			ResponseBuilder responseBuilder;
			
			if(id.equals("1"))
			{ 
				arquivo = usuarioBusiness.getFile(1);
				responseBuilder = Response.ok(arquivo);	 
				responseBuilder.header("Content-Disposition", "attachment;filename=assinatura.jpeg ");	 
			}
			else if(id.equals("2"))
			{ 
				arquivo = usuarioBusiness.getFile(2);
			//	responseBuilder = Response.ok(arquivo);	 
			//	responseBuilder.header("Content-Disposition", "attachment;filename=back_document.jpeg ");	
				
				StringBuilder title = new StringBuilder();
	      		title.append("attachment;filename=back_document.pdf");	      		
	      	      		
	      		return Response.ok(arquivo).header("Content-Disposition", title).build();	
				
				
			}
			
			else if(id.equals("3"))
			{ 
				arquivo = usuarioBusiness.getFile(3);
				responseBuilder = Response.ok(arquivo);	 
				responseBuilder.header("Content-Disposition", "attachment;filename=front_document.jpeg ");	 
			}
			
			else if(id.equals("4"))
			{ 
				arquivo = usuarioBusiness.getFile(4);
				responseBuilder = Response.ok(arquivo);	 
				responseBuilder.header("Content-Disposition", "attachment;filename=hand_document.jpeg ");	 
			}
			else
			{
				responseBuilder = Response.ok();	 
				responseBuilder.header("Content-Disposition", "attachment;filename=no_file.png ");					
			}
			return responseBuilder.build();					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return error("OCORREU UM ERRO", BasicRestServe.INTERNAL_ERROR);
		}	
	}
}










