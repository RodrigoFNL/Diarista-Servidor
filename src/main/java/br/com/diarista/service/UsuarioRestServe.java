package br.com.diarista.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.diarista.business.BasicBusiness;
import br.com.diarista.business.UsuarioBusiness;
import br.com.diarista.conf.EmailInfo;
import br.com.diarista.dto.UsuarioDTO;
import br.com.diarista.entity.Usuario;

@RestController
@RequestMapping("/rest/usuario")
public class UsuarioRestServe extends BasicRestServe<Usuario>
{
	@Autowired
	private UsuarioBusiness usuarioBusiness;

	@Override
	protected BasicBusiness<Usuario> business() 
	{	
		return usuarioBusiness;
	}		


	@PostMapping("/participate")
	public ResponseEntity<Object> participate(@RequestBody Map<String, Object> postObject)
	{
		try
		{	
			String response = usuarioBusiness.createCoupon(postObject);	

			if(response.contains("id:")) return  ok(response);	
			else return error(response, BasicRestServe.INTERNAL_ERROR);		
		}
		catch (Exception e)
		{	
			return error("Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.BAD_REQUEST);
		}
	}	

	@PostMapping("/invitation")
	public ResponseEntity<Object> invitation(@RequestBody Map<String, Object> postObject)
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


	

	@RequestMapping(value="/register",  method=RequestMethod.POST, consumes = "multipart/form-data") 
	@ResponseBody
	public ResponseEntity<Object>  register(@RequestParam("frontdocument") MultipartFile file, HttpServletResponse response )
	{			
		
		try
		{				
			FileCopyUtils.copy(file.getBytes(), response.getOutputStream());
		
			response.flushBuffer();
			response.setContentType("application/pdf");
			
			System.out.println("Resolvido?");
		
			return ResponseEntity.ok().build();	
		}
		catch (Exception e) 
		{	
			e.printStackTrace();
			return ResponseEntity.status(405).body("message:Error!");
		}	
		
	}	
////		try
////		{			
////						
////			System.out.println(data);
//			
//			
////			System.out.println("ok");			
////			   String UPLOAD_PATH = "c:/temp/";
////			    try
////			    {
////			        int read = 0;
////			        byte[] bytes = new byte[1024];
////			 
////			        OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + "image.jpeg"));
////			        while ((read = part.read(bytes)) != -1) 
////			        {
////			            out.write(bytes, 0, read);
////			        }
////			        out.flush();
////			        out.close();
////			    } catch (IOException e) 
////			    {
////			       e.printStackTrace();
////			    }
////			    return ok("em construção");
//			
//			
//			
//			
//			
//			
//			
////			@FormDataParam("signature")
//			
////			BufferedInputStream signatureBuff = new BufferedInputStream(uploadedInputStream);			
////			byte[] signature = signatureBuff.readAllBytes();
////			
////			System.out.println(new String(signature));
//			
////			Map<String, Object> map = MultiPartUtils.multiPart(object);			
////			if(map == null) return error("Informações necessária incompleta", BasicRestServe.BAD_REQUEST);					
////			String execute = usuarioBusiness.register(Usuario.getEntity(map));	
////			
//			
//			
//			
//	
//			
//			
////			
////			if(execute == null) return ok("em construção");
////			else return error(execute, BasicRestServe.INTERNAL_ERROR);
////		}
////		catch (Exception e) 
////		{
////			e.printStackTrace();
////			return error("OCORREU UM ERRO", BasicRestServe.INTERNAL_ERROR);
////		}	
////	}
//	
//	@GET
//	@Path("download/{id}")
//	@Produces(MediaType.APPLICATION_OCTET_STREAM)
//	//@Produces("image/jpeg")	
//	public Response download(@PathParam("id") String id)
//	{		
//		try
//		{				
//			byte[] arquivo = null;
//			ResponseBuilder responseBuilder;
//				
//			if(id.equals("1"))
//			{ 
//				arquivo = usuarioBusiness.getFile(1);		
//				responseBuilder = Response.ok();	 
//				responseBuilder.header("Content-Disposition", "attachment;filename=assinatura.jpeg ");	 
//			}
//			else if(id.equals("2"))
//			{ 
//				arquivo = usuarioBusiness.getFile(2);
//				System.out.println(new String(arquivo));
//				return ok(new String(arquivo));
//			//	responseBuilder = Response.ok(arquivo);	 
//			//	responseBuilder.header("Content-Disposition", "attachment;filename=back_document.jpeg ");	
//				
////				StringBuilder title = new StringBuilder();
////	      		title.append("attachment;filename=back_document.pdf");	      		
////	      	      		
////	      		return Response.ok(arquivo).header("Content-Disposition", title).build();	
////				
//				
//			}
//			
//			else if(id.equals("3"))
//			{ 
//				arquivo = usuarioBusiness.getFile(3);
//				System.out.println(new String(arquivo));
//				return ok(new String(arquivo));
////				responseBuilder = Response.ok(arquivo);	 
////				responseBuilder.header("Content-Disposition", "attachment;filename=front_document.jpeg ");	 
//			}
//			
//			else if(id.equals("4"))
//			{ 
//				arquivo = usuarioBusiness.getFile(4);
//				System.out.println(new String(arquivo));
//				return ok(new String(arquivo));
////				responseBuilder = Response.ok(arquivo);	 
////				responseBuilder.header("Content-Disposition", "attachment;filename=hand_document.jpeg ");	 
//			}
////			else
////			{
//////				responseBuilder = Response.ok();	 
//////				responseBuilder.header("Content-Disposition", "attachment;filename=no_file.png ");					
////			}
////			return responseBuilder.build();	
//			
//			
//			return ok("assets/image.png");
//		}
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			return error("OCORREU UM ERRO", BasicRestServe.INTERNAL_ERROR);
//		}	
//	}
}










