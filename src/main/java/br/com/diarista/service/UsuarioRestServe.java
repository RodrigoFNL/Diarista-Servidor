package br.com.diarista.service;

import java.util.Base64;
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
import br.com.diarista.business.UFBusiness;
import br.com.diarista.business.UsuarioBusiness;
import br.com.diarista.conf.EmailInfo;
import br.com.diarista.dto.UsuarioDTO;
import br.com.diarista.entity.Endereco;
import br.com.diarista.entity.EstadoCivil;
import br.com.diarista.entity.Nacionalidade;
import br.com.diarista.entity.RG;
import br.com.diarista.entity.UF;
import br.com.diarista.entity.Usuario;
import br.com.diarista.utils.DateUtils;
import br.com.diarista.utils.StringUtils;

@RestController
@RequestMapping("/rest/usuario")
public class UsuarioRestServe extends BasicRestServe<Usuario>
{
	@Autowired
	private UsuarioBusiness usuarioBusiness;
	
	@Autowired
	private UFBusiness ufBusiness;


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
	public ResponseEntity<Object>  register(@RequestParam("frontDocument") 	MultipartFile frontDocument, 
											@RequestParam("backDocument")  	MultipartFile backDocument, 
											@RequestParam("handDocument") 	MultipartFile handDocument,
											@RequestParam("signature")  	MultipartFile signature,
											@RequestParam("cpf")			String cpf,
											
											@RequestParam("rg_number")		String rgNumber,
											@RequestParam("rg_issuer")		String rgIssuer,
											@RequestParam("rg_uf")			String rgUF,
											
											@RequestParam("andress_id") 		String andressId,
											@RequestParam("andress_number") 	String andressNumber,
											@RequestParam("andress_complement") String andressComplement,
											@RequestParam("andress_cep") 		String andressCep,
											@RequestParam("andress_localidade") String andressLocalidade,
											@RequestParam("andress_logradouro") String andressLogradouro,
											@RequestParam("andress_bairro") 	String andressBairro,
											@RequestParam("andress_uf") 		String andressUf,
											
											@RequestParam("rne")			String rne,			
											@RequestParam("name")			String userName,
											@RequestParam("nickname")		String nickName,
											@RequestParam("email")			String email,
											@RequestParam("coupon")			String coupon,
											@RequestParam("termos_condicoes") String termosCondicoes,
											@RequestParam("password") 		String password,							
											@RequestParam("confirm_password") String confirmPassword,
											@RequestParam("nationality") 	String nationality,
											@RequestParam("birth_date") 	String birthDate,
											@RequestParam("marital_status") String maritalStatus,
											@RequestParam("cell_phone") 	String cellPhone,
											@RequestParam("token") 			String token,
											@RequestParam("is_prestar_servico") String isPrestarServico,
											@RequestParam("is_contratar") 	String isContratar)
	{			

		try
		{			
			Usuario user = new Usuario();			
			user.setCpf(StringUtils.removeCharacters(cpf));		
			if(StringUtils.isNotNull(rgNumber) && StringUtils.isNotNull(rgIssuer) && StringUtils.isNotNull(rgUF)) user.setRg(new RG(rgNumber, rgIssuer, rgUF));
			
			
			UF uf = ufBusiness.getUFbySigla(andressUf);
			
			if(StringUtils.isNotNull(andressNumber) && StringUtils.isNotNull(andressCep) && StringUtils.isNotNull(andressLocalidade) && StringUtils.isNotNull(andressUf))
				user.setAndress(new Endereco(andressId, andressNumber, andressComplement, andressCep, andressLocalidade, andressLogradouro, andressBairro, uf));
									
			user.setBirth_date(DateUtils.getDate(birthDate));			
			user.setRne(StringUtils.removeCharacters(rne));
			user.setName(userName);
			user.setNickname(nickName);
			user.setEmail(email);
			user.setCoupon(coupon);
			user.setTermosCondicoes(Boolean.valueOf(termosCondicoes));
			user.setConfirm_password(StringUtils.encrypt(password));
			user.setNationality(new Nacionalidade(nationality));
			user.setMarital_status(new EstadoCivil(maritalStatus));
			user.setToken(token);
			user.setIsContratar(Boolean.valueOf(isContratar));
			user.setIsPrestar_servico(Boolean.valueOf(isPrestarServico));
			user.setCell_phone(StringUtils.removeCharacters(cellPhone));		
						
			user.setFrontDocument(Base64.getDecoder().decode(new String(frontDocument.getBytes())));
			user.setBackDocument(Base64.getDecoder().decode(new String(backDocument.getBytes())));
			user.setHandDocument(Base64.getDecoder().decode(new String(handDocument.getBytes())));
			user.setSignature(Base64.getDecoder().decode(new String(signature.getBytes())));			

			String response = usuarioBusiness.register(user, StringUtils.encrypt(confirmPassword));

			if(response.contains("id:")) return  ok(response);	
			else return error(response, BasicRestServe.INTERNAL_ERROR);		
		}
		catch (Exception e) 
		{	
			e.printStackTrace();
			return error("Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.BAD_REQUEST);
		}	

	}	

	@GetMapping("/download_contrato/{cpf}/{token}")
	public void download(@PathVariable("cpf") String cpf,@PathVariable("token") String token,  HttpServletResponse response)
	{		
		try
		{			
			if(token.equals(UsuarioBusiness.getToken()))
			{			
				cpf = StringUtils.removeCharacters(cpf);
				Usuario user = usuarioBusiness.findByCPF(cpf);	
				
				if(user == null) return;			
				
				byte [] pdf = usuarioBusiness.getContrato(user);		
				if(pdf == null) return;
				
				String fileName = "";
				FileCopyUtils.copy(pdf, response.getOutputStream());		
				fileName = "Contrato";
	
				response.setContentType("application/pdf");
				response.flushBuffer();
				response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf" );
				response.getOutputStream().flush();
			}
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@PostMapping("/send_mail_contrato")
	public  ResponseEntity<Object> sendMailContrato(@RequestBody Map<String, Object> postObject)
	{		
		try
		{		
			String cpf = StringUtils.isNotNull((String) postObject.get("cpf")) ? StringUtils.removeCharacters((String) postObject.get("cpf")) : null;			
			if(cpf == null)   return error("CPF INVÁLIDO", BasicRestServe.INTERNAL_ERROR);		
			if(cpf.isEmpty()) return error("CPF INVÁLIDO", BasicRestServe.INTERNAL_ERROR);		
			Boolean isOk = usuarioBusiness.sendContratoViaEmail(cpf);	
			if(isOk) return ok("OK!");	
			else  	 return error("Erro ao tentar enviar o Email", BasicRestServe.INTERNAL_ERROR);	
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			return error("Erro ao tentar enviar o Email", BasicRestServe.INTERNAL_ERROR);		
		}
	}
}










