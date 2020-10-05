package br.com.diarista.business;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.dao.UsuarioDAO;
import br.com.diarista.dto.UserDTO;
import br.com.diarista.dto.UsuarioDTO;
import br.com.diarista.entity.Usuario;
import br.com.diarista.utils.DateUtils;
import br.com.diarista.utils.DiaristaUtils;
import br.com.diarista.utils.EmailUtil;
import br.com.diarista.utils.StringUtils;

@Service
public class UsuarioBusiness  extends BasicBusiness<Usuario>
{		
	private static String token ;

	@Autowired
	private UsuarioDAO usuarioRepository;


	public String register(Usuario entity, String password) 
	{
		try
		{				
			if(!DiaristaUtils.validCPF(entity.getCpf())) 	return "CPF é inválido!";	
			
			if(entity.getRg() == null && entity.getRne() == null) return "RG ou RNE obrigatório!";			
			if(entity.getAndress() == null) 				return "O endereço é obrigatório!";				
			if(entity.getAndress().getLocality() == null)  	return "O endereço é obrigatório!";			
			if(entity.getAndress().getNumber() == null) 	return "O número da residência é obrigatório!";
			if(entity.getBirth_date() == null) 				return "A data de nascimento é obrigatório!";
			if(!DateUtils.isOfAge(entity.getBirth_date())) 	return "O usuário tem que ser maior de idade!";	
			
			if(StringUtils.isNull(entity.getName())) 	 return "É obrigatório o preenchimento do Nome";	
			if(StringUtils.isNull(entity.getNickname())) return "É obrigatório o preenchimento do NickName";	
			if(StringUtils.isNull(entity.getLogin()))	 return "É obrigatório o preenchimento do Login";				
			if(StringUtils.isNull(entity.getEmail()))	 return "É obrigatório o preenchimento do Email";			
			if(StringUtils.isNull(entity.getCoupon())) 	 return "É obrigatório o preenchimento do Cupom";				

			if(DiaristaUtils.validTelefone(entity.getCell_phone())) return "O número telefonico é inválido";
				
			if(entity.getTermosCondicoes()  == null) return "Você deve aceitar os Termos e Condições para participar do App";	
			else if(!entity.getTermosCondicoes())    return "Você deve aceitar os Termos e Condições para participar do App";				
			if(!entity.getConfirm_password().equals(password))  return "A Senha é divergente da confirmação da senha";				
	
			if(entity.getNationality() == null) 				return "É obrigatório o preenchimento da Nacionalidade";	
			else if(entity.getNationality().getId() == null)	return "É obrigatório o preenchimento da Nacionalidade";	
						
			if(entity.getMarital_status() == null) 				return "É obrigatório o preenchimento do Estado Civil";	
			else if(entity.getMarital_status().getId() == null)	return "É obrigatório o preenchimento da Estado Civil";	
		
			if(entity.getIsContratar() == null)			return "Você não definiu se irá contratar ou não";	
			if(entity.getIsPrestar_servico() == null)   return "Você não definiu se irá prestar serviço ou não";	
		
			if(entity.getFrontDocument() == null)   return "É obrigatório a foto da frente do documento!";	
			if(entity.getBackDocument() == null)    return "É obrigatório a foto da parte de trás do documento!";	
			if(entity.getHandDocument() == null)    return "É obrigatório a foto do usuário com o documento na mão!";	
			if(entity.getSignature() == null)   	return "É obrigatório a assinatura do contrato!";	
							
			Optional<Usuario> update = usuarioRepository.findByCpf(entity.getCpf());
			if(update.isEmpty()) return "Usuário não encontrado!";
						
			Usuario userRecovery = update.get();
			
			if(!userRecovery.getCoupon().equals(entity.getCoupon())) return "O coupon é diferente do registrado no banco de dados!";			
			
			userRecovery.setFrontDocument(entity.getFrontDocument());	
			userRecovery.setBackDocument(entity.getBackDocument());	
			userRecovery.setHandDocument(entity.getHandDocument());	
			userRecovery.setSignature(entity.getSignature());			
			
			userRecovery.setRg(entity.getRg());
			userRecovery.setAndress(entity.getAndress());			
			userRecovery.setBirth_date(entity.getBirth_date()); 	
			userRecovery.setNickname(entity.getNickname());
			userRecovery.setLogin(DiaristaUtils.genarateLogin( entity.getLogin()));
			userRecovery.setEmail(entity.getEmail());			
			userRecovery.setCell_phone(entity.getCell_phone());
				
			userRecovery.setTermosCondicoes(entity.getTermosCondicoes());		
			userRecovery.setPassword(entity.getConfirm_password().getBytes());	
			userRecovery.setNationality(entity.getNationality());						
			userRecovery.setMarital_status(entity.getMarital_status());		
			userRecovery.setIsContratar(entity.getIsContratar());
			userRecovery.setIsPrestar_servico(entity.getIsPrestar_servico());
			
			usuarioRepository.save(userRecovery);			
			return "id:" + entity.getCpf();
		}
		catch (Exception e) 
		{			
			e.printStackTrace();
			return null;
		}
	}

	//Salva um usuário com informações básica, cria um número de cupom, e salva no banco
	public String createCoupon(Map<String, Object> postObject) 
	{	
		try
		{
			String name = StringUtils.notEnpty((String) postObject.get("name"))		? (String) postObject.get("name"):	null;
			String cpf =  StringUtils.notEnpty((String) postObject.get("cpf"))		? StringUtils.removeCharacters((String) postObject.get("cpf")): null;
			String email = StringUtils.notEnpty((String) postObject.get("email"))	? (String) postObject.get("email"): null;
			Boolean accept = postObject.get("accept") != null? (Boolean) postObject.get("accept"): false;

			if(name == null) 			return "Você deve fornecer um Nome";
			else if(cpf == null)  		return "Você deve fornecer um CPF";
			else if(email == null) 		return "Você deve fornecer um Email";
			else if(!accept)			return "Você deve aceitar os termos e condições";

			Optional<Usuario> hasDuplic = usuarioRepository.findById(cpf);

			Usuario hasDuplicated = hasDuplic.isPresent() ? hasDuplic.get() : null;			
			if(hasDuplicated != null && hasDuplicated.getLogin() != null) return "Já existe um usuário cadastrado neste CPF.";

			Usuario user = new Usuario();			
			user.setCoupon(StringUtils.generateCoupon(new Date().getTime()));
			user.setName(name);
			user.setCpf(cpf);
			user.setEmail(email);
			user.setTermosCondicoes(accept);	
			user.setIsContratar(false);
			user.setIsPrestar_servico(false);

			usuarioRepository.save(user);		

			new Thread() 
			{				
				@Override
				public void run() 
				{	
					EmailUtil email = new EmailUtil();
					email.sendCoupon(user.getEmail(), "Olá " + user.getName() +  "\nSeja bem vindo(a) a nossa comunidade! \nNúmero do Coupon: " + user.getCoupon());
				};
			}.start();				

			return "id:";

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Ocorreu um erro, ao tentar salvar as informações no banco";
		}
	}

	//busca um usuário que contém um convite, para que o mesmo termine de preencher as informações
	public UsuarioDTO getInfoUserByCodigo(String invitation) 
	{	
		Optional<Usuario> userOptional = usuarioRepository.findByCoupon(invitation);	

		Usuario user = userOptional.isPresent() ? userOptional.get() : null;		
		UsuarioDTO dto = user != null && user.getLogin() == null ? user.getDTO() : null;	

		//cria um token de convite para finalizar cadastro
		if(dto != null) createToken(dto, "INV-2020");

		return dto;		
	}

	//cria um token, o preToken dirá se o usuário está logado ou está preenchendo o resto do cadastro
	private void createToken(UsuarioDTO dto, String preToken) 
	{
		Date date = new Date();
		Long time = date.getTime();		
		String token =  preToken + StringUtils.encrypt(time.toString() + dto.getCpf());
		
		UsuarioBusiness.setToken(token);
		dto.setToken(token);
	}

	@Override
	public List<UserDTO> getAllActive() 
	{
		return null;
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		UsuarioBusiness.token = token;
	}

	public Usuario findByCPF(String cpf) 
	{
		Optional<Usuario> userOptional = usuarioRepository.findByCpf(cpf);	
		Usuario user = userOptional.isPresent() ? userOptional.get() : null;	
		return user;
	}

}
