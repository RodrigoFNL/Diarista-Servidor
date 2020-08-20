package rest;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import business.BasicBusiness;
import business.UsuarioBusiness;
import conf.EmailInfo;
import dto.UsuarioDTO;
import model.Usuario;

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
	
//	  public Response register(@Context HttpServletRequest obj) 
//	{
//		
//		System.out.println(obj);	
//		return ok(obj);

//	
//	@POST
//	@Path("register")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public Response register(UserRegisterMultiFormRest object)
//	{
//		System.out.println(object);	
//		return ok(object);
	
	@POST
	@Path("register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response register(@FormParam("cpf") String cpf,
			@FormParam("rg") String rg,
			@FormParam("rne") String rne, 
			@FormParam("name") String name, 
			@FormParam("nickname") String nickName,
			@FormParam("login")	String login,
			@FormParam("email") String email,
			@FormParam("coupon") String coupon,
			@FormParam("termos_condicoes") String termosCondicoes,
			@FormParam("password")	String password,
			@FormParam("confirm_password") String confirmPassword,
			@FormParam("nationality") String nationality,
			@FormParam("birth_date")  String birthDate,
			@FormParam("marital_status") String maritalStatus,
			@FormParam("cell_phone")	String cellPhone,
			@FormParam("andress") String andress,
			@FormParam("token")	String token,
			@FormParam("is_contratar")		 String isContratar,
			@FormParam("is_prestar_servico") String isService,
			@FormParam("frontDocument") byte[] frontDocument,
			@FormParam("backDocument")  byte[] backDocument,
			@FormParam("handDocument")  byte[] handDocument,
			@FormParam("signature")	    byte[] signature )
			{		
			
			
			System.out.println(cpf);	
			return ok("em construção");

		
		
		
//		try
//		{	
//			String invitation = (String) postObject.get("codigo") != null ? (String) postObject.get("codigo") : null;				
//			invitation = invitation != null? invitation.trim(): null;
//
//			UsuarioDTO user = usuarioBusiness.getInfoUserByCodigo(invitation);
//			if(user != null) return ok(user);		
//			else return error("Não foi localizado o número do cupom fornecido", BasicRestServe.UNAUTHORIZED);		
//		}
//		catch (Exception e)
//		{	
//			return error("Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.BAD_REQUEST);
//		}
	}
}
