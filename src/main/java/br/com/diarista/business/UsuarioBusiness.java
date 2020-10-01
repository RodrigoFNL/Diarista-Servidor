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
import br.com.diarista.utils.EmailUtil;
import br.com.diarista.utils.StringUtils;

@Service
public class UsuarioBusiness  extends BasicBusiness<Usuario>
{		
	private static String token ;

	@Autowired
	private UsuarioDAO usuarioRepository;


	public String register(Usuario entity) 
	{
		try
		{	
			Optional<Usuario> update = usuarioRepository.findByCpf(entity.getCpf());
			if(update.isEmpty()) return "Usuário não encontrado!";
						
			Usuario userRecovery = update.get();			
			userRecovery.setFrontDocument(entity.getFrontDocument());	
			userRecovery.setBackDocument(entity.getBackDocument());	
			userRecovery.setHandDocument(entity.getHandDocument());	
			userRecovery.setSignature(entity.getSignature());
			
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
