package br.com.diarista.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<Object>  register(@RequestParam("frontDocument") MultipartFile frontdocument, 
											@RequestParam("backDocument")  MultipartFile backDocument, 
											@RequestParam("handDocument")  MultipartFile handDocument )
	{			

		try
		{			
			Usuario user = new Usuario();

			user.setCpf("82052875972");
			user.setFrontDocument(frontdocument.getBytes());
			user.setBackDocument(backDocument.getBytes());
			user.setHandDocument(handDocument.getBytes());

			String response = usuarioBusiness.register(user);

			if(response.contains("id:")) return  ok(response);	
			else return error(response, BasicRestServe.INTERNAL_ERROR);		
		}
		catch (Exception e) 
		{	
			e.printStackTrace();
			return error("Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.BAD_REQUEST);
		}	

	}	



	//@Produces(MediaType.APPLICATION_OCTET_STREAM)	

	@GetMapping("/download/{id}")
	public void download(@PathVariable("id") String id, HttpServletResponse response)
	{		
		try
		{				

			Usuario user = usuarioBusiness.findByCPF("82052875972");	
			String fileName = "";

			switch (id)
			{
				case "1": 	FileCopyUtils.copy(user.getFrontDocument(), response.getOutputStream());					
							fileName = "Front-Document";
					break;
				case "2": 	FileCopyUtils.copy(user.getBackDocument(), response.getOutputStream());				
							fileName = "Back-Document";
					break;
				case "3": 	FileCopyUtils.copy(user.getHandDocument(), response.getOutputStream());
						  	fileName = "Hand-Document";						 
					break;
				case "4": 	FileCopyUtils.copy(user.getSignature(), response.getOutputStream());
							fileName = "Signature";
					break;
			}

			response.setContentType("image/png;base64");
			response.flushBuffer();
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".jpg" );
			response.getOutputStream().flush();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}










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
		//				StringBuilder title = new StringBuilder();
		//	      		title.append("attachment;filename=back_document.pdf");	      		
		//	      	      		
		//	      		return Response.ok(arquivo).header("Content-Disposition", title).build();	
		//				
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
		//			else
		//			{
		////				responseBuilder = Response.ok();	 
		////				responseBuilder.header("Content-Disposition", "attachment;filename=no_file.png ");					
		//			}
		//			return responseBuilder.build();	
		//			
		//			
		//			return ok("assets/image.png");
		//		}
		//		catch (Exception e) 
		//		{
		//			e.printStackTrace();
		//			return error("OCORREU UM ERRO", BasicRestServe.INTERNAL_ERROR);
		//		}	
	}
}










