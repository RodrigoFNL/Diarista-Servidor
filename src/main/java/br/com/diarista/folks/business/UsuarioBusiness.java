package br.com.diarista.folks.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.diarista.adress.dao.EnderecoDAO;
import br.com.diarista.adress.dao.LocalidadeDAO;
import br.com.diarista.conf.ConstantsSecurity;
import br.com.diarista.conf.EmailInfo;
import br.com.diarista.folks.dao.AssessmentDAO;
import br.com.diarista.folks.dao.RGDAO;
import br.com.diarista.folks.dao.UsuarioDAO;
import br.com.diarista.folks.dto.InfoUserDTO;
import br.com.diarista.folks.dto.UserDTO;
import br.com.diarista.folks.dto.UsuarioDTO;
import br.com.diarista.folks.entity.Assessment;
import br.com.diarista.folks.entity.RG;
import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.routine.dao.SendEmailDAO;
import br.com.diarista.routine.entity.SendEmail;
import br.com.diarista.utils.DateUtils;
import br.com.diarista.utils.DiaristaUtils;
import br.com.diarista.utils.StringUtils;
import br.com.diarista.utils.StringUtils.IntString;
import io.jsonwebtoken.Jwts;

@Service
public class UsuarioBusiness  extends BasicBusiness<Usuario>
{			
	@Autowired
	private UsuarioDAO usuarioRepository;

	@Autowired
	private RGDAO rgRepository;

	@Autowired
	private LocalidadeDAO localidadeRepository;

	@Autowired
	private EnderecoDAO enderecoRepository;
	
	@Autowired
	private AssessmentDAO assessRepository;
	
	@Autowired
	private SendEmailDAO emailRepository;

//	@Autowired
//	PDFUtils utils;

	@Transactional
	public String register(Usuario entity, String password) 
	{
		try
		{		
			RG removeRG = null;			
			if(!DiaristaUtils.validCPF(entity.getCpf())) 	return "CPF é inválido!";	

			if(entity.getRg() == null && entity.getRne() == null) return "RG ou RNE obrigatório!";			
			if(entity.getAndress() == null) 				return "O endereço é obrigatório!";				
			if(entity.getAndress().getLocality() == null)  	return "O endereço é obrigatório!";			
			if(entity.getAndress().getNumber() == null) 	return "O número da residência é obrigatório!";
			if(entity.getBirth_date() == null) 				return "A data de nascimento é obrigatório!";
			if(!DateUtils.isOfAge(entity.getBirth_date())) 	return "O usuário tem que ser maior de idade!";	

			if(StringUtils.isNull(entity.getName())) 	 return "É obrigatório o preenchimento do Nome";	
			if(StringUtils.isNull(entity.getNickname())) return "É obrigatório o preenchimento do NickName";	

			if(StringUtils.isNull(entity.getEmail()))	 return "É obrigatório o preenchimento do Email";			
			if(StringUtils.isNull(entity.getCoupon())) 	 return "É obrigatório o preenchimento do Cupom";				

			if(!DiaristaUtils.validTelefone(entity.getCell_phone())) return "O número telefonico é inválido";

			if(entity.getTermosCondicoes()  == null) 			return "Você deve aceitar os Termos e Condições para participar do App";	
			else if(!entity.getTermosCondicoes())    			return "Você deve aceitar os Termos e Condições para participar do App";	

			if(!entity.getConfirm_password().equals(password))  return "A Senha é divergente da confirmação da senha";				

			if(entity.getNationality() == null) 				return "É obrigatório o preenchimento da Nacionalidade";	
			else if(entity.getNationality().getId() == null)	return "É obrigatório o preenchimento da Nacionalidade";	

			if(entity.getMarital_status() == null) 				return "É obrigatório o preenchimento do Estado Civil";	
			else if(entity.getMarital_status().getId() == null)	return "É obrigatório o preenchimento da Estado Civil";	

			if(entity.getFrontDocument() == null)   return "É obrigatório a foto da frente do documento!";	
			if(entity.getBackDocument() == null)    return "É obrigatório a foto da parte de trás do documento!";	
			if(entity.getHandDocument() == null)    return "É obrigatório a foto do usuário com o documento na mão!";	
			if(entity.getSignature() == null)   	return "É obrigatório a assinatura do contrato!";	

			Optional<Usuario> update = usuarioRepository.findByCpf(entity.getCpf());
			if(update.isEmpty()) return "Usuário não encontrado!";

			Usuario userRecovery = update.get();

			if(userRecovery.getRegistrationSituation() != null && userRecovery.getRegistrationSituation() > 2) return "Usuário Já Cadastrado";					
			if(!userRecovery.getCoupon().equals(entity.getCoupon())) return "O coupon é diferente do registrado no banco de dados!";			

			userRecovery.setFrontDocument(entity.getFrontDocument());	
			userRecovery.setBackDocument(entity.getBackDocument());	
			userRecovery.setHandDocument(entity.getHandDocument());	
			userRecovery.setSignature(entity.getSignature());			

			if(userRecovery.getRg() != null && userRecovery.getRg() != null && entity.getRg() != null)
			{								
				userRecovery.getRg().setNumber(entity.getRg().getNumber());
				userRecovery.getRg().setIssuer(entity.getRg().getIssuer());
				userRecovery.getRg().setUf(entity.getRg().getUf());				
			}
			else 
			{
				if(userRecovery.getRg() != null) removeRG = userRecovery.getRg();
				userRecovery.setRg(entity.getRg());
			}

			userRecovery.setRne(entity.getRne());									
			userRecovery.setAndress(entity.getAndress());			
			userRecovery.setBirth_date(entity.getBirth_date()); 	
			userRecovery.setNickname(entity.getNickname().toUpperCase());
			userRecovery.setEmail(entity.getEmail());			
			userRecovery.setCell_phone(entity.getCell_phone());

			userRecovery.setTermosCondicoes(entity.getTermosCondicoes());		
			userRecovery.setPassword(StringUtils.encrypt(entity.getConfirm_password()).getBytes());	
			userRecovery.setNationality(entity.getNationality());						
			userRecovery.setMarital_status(entity.getMarital_status());		
			userRecovery.setRegistrationSituation(Usuario.CADASTRO_EM_ANALISE);		
			userRecovery.setDateRegister(new Date());
			
			localidadeRepository.save(entity.getAndress().getLocality());

			if(userRecovery.getAndress() != null) 	enderecoRepository.save(userRecovery.getAndress());
			if(userRecovery.getRg() != null) 		rgRepository.save(userRecovery.getRg());

			usuarioRepository.save(userRecovery);	
			
			if(removeRG != null) rgRepository.delete(removeRG);
			
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

			Optional<Usuario> hasDuplic = usuarioRepository.findByCpf(cpf);
			Usuario hasDuplicated = hasDuplic.isPresent() ? hasDuplic.get() : null;			
			if(hasDuplicated != null && hasDuplicated.getRegistrationSituation() != null && hasDuplicated.getRegistrationSituation() > 2) return "Já existe um usuário cadastrado neste CPF.";

			Usuario user = new Usuario();	
			user.setCoupon(StringUtils.generateCoupon(new Date().getTime()));
			user.setRegistrationSituation(Usuario.CADASTRO_INCOMPLETO);
			user.setName(name);
			user.setCpf(cpf);
			user.setEmail(email);
			user.setTermosCondicoes(accept);	
			usuarioRepository.save(user);		
			
			SendEmail sendEmail = new SendEmail();
			sendEmail.setBody("Olá " + user.getName() +  "\nSeja bem vindo(a) a nossa comunidade! \nNúmero do Cupom: " + user.getCoupon());
			sendEmail.setDateRegister(new Date());
			sendEmail.setRecipient(user.getEmail());
			sendEmail.setSubject("Número do Cupon");			
			emailRepository.save(sendEmail);
			
//			new Thread() 
//			{				
//				@Override
//				public void run() 
//				{	
//					EmailUtil email = new EmailUtil();
//					email.sendCoupon(user.getEmail(), "Olá " + user.getName() +  "\nSeja bem vindo(a) a nossa comunidade! \nNúmero do Cupom: " + user.getCoupon());
//				};
//			}.start();				

			return "id:";

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Ocorreu um erro, ao tentar salvar as informações no banco se o erro persistir envie o email para " + EmailInfo.EMAIL_ADMINISTRADOR;
		}
	}

	//busca um usuário que contém um convite, para que o mesmo termine de preencher as informações
	public UsuarioDTO getInfoUserByCodigo(String invitation) 
	{	
		Optional<Usuario> userOptional = usuarioRepository.findByCoupon(invitation);	
		Usuario user = userOptional.isPresent() ? userOptional.get() : null;	
		if(user != null && user.getRegistrationSituation() == null) user.setRegistrationSituation(Usuario.CADASTRO_INCOMPLETO); 				
		UsuarioDTO dto = user != null &&  user.getRegistrationSituation() < 3 ? user.getDTO() : null;	
		
		return dto;		
	}


	@Override
	public List<UserDTO> getAllActive() 
	{
		return null;
	}

	public Usuario findByCPF(String cpf) 
	{
		Optional<Usuario> userOptional = usuarioRepository.findByCpf(cpf);	
		Usuario user = userOptional.isPresent() ? userOptional.get() : null;	
		return user;
	}

	public Boolean sendContratoViaEmail(String cpf) 
	{
		try
		{
			Optional<Usuario> optional = usuarioRepository.findByCpf(cpf);
			Usuario user = optional.isPresent() ? optional.get() : null;
			if(user == null) return false;			
			
//			EmailUtil email = new EmailUtil();
//			email.sendEmail(user.getEmail(), "Contrato Faxinex", textContrato(user.getNickname()) , getContrato(user));	
			
			SendEmail sendEmail = new SendEmail();
			sendEmail.setBody(textContrato(user.getNickname()));
			sendEmail.setDateRegister(new Date());
			sendEmail.setRecipient(user.getEmail());
			sendEmail.setSubject("Contrato Faxinex");	
			sendEmail.setSendContract(true);
			emailRepository.save(sendEmail);			
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	private String textContrato(String nickname) 
	{	
		StringBuilder text = new StringBuilder();
		text.append("Olá ").append(nickname).append("\n");
		text.append("Segue em anexo, o contrato que você solicitou ");	
		return text.toString();
	}

//	public byte[] getContrato(Usuario user) throws ClassNotFoundException, JRException, SQLException, IOException 
//	{	
//		byte [] pdf = utils.getPDF("Contrato.jasper", user);						
//		return pdf;
//	}

	public Map<Boolean, String> resendCoupon(String cpf, String email) 
	{		
			
		try
		{
			Map<Boolean, String> response = new HashMap<Boolean, String>();
			Optional<Usuario> userOption = usuarioRepository.findByCpf(cpf);		
			if(!(userOption != null && !userOption.isEmpty())) 
			{
				response.put(false, "USUÁRIO NÃO ENCONTRADO!");
				return response;
			}
					
			Usuario user = userOption.get();
			
			if(!user.getEmail().equals(email))
			{
				response.put(false, "O EMAIL É DIFERENTE AO CADASTRADO! ENTRE EM CONTATO ATRAVÉS DO " + EmailInfo.EMAIL_ADMINISTRADOR);
				return response;			
			}
			
			SendEmail sendEmail = new SendEmail();
			sendEmail.setBody("Olá " + user.getName() +  "\nVocê solicitou o reenviou do cupom! \nNúmero do Cupom: " + user.getCoupon());
			sendEmail.setDateRegister(new Date());
			sendEmail.setRecipient(user.getEmail());
			sendEmail.setSubject("Número do Cupon");		
			emailRepository.save(sendEmail);
			
	//		new Thread() 
	//		{				
	//			@Override
	//			public void run() 
	//			{	
	//				EmailUtil email = new EmailUtil();
	//				email.sendCoupon(user.getEmail(), "Olá " + user.getName() +  "\nVocê solicitou o reenviou do cupom! \nNúmero do Cupom: " + user.getCoupon());
	//			};
	//		}.start();	
	//		
	
			response.put(true, "CUPOM ENVIADO COM SUCESSO!");
			return response;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Map<Boolean, String> response = new HashMap<Boolean, String>();
			response.put(false, "Error ao tentar enviar o Email, se o erro persistir envie um email para " + EmailInfo.EMAIL_ADMINISTRADOR);
			return response;
		}
	}

	@Transactional
	public Map<Boolean, String> resetPassword(String cpf, String email, String coupon) 
	{
		try
		{
			Map<Boolean, String> response = new HashMap<Boolean, String>();
			
			Optional<Usuario> userOption = usuarioRepository.findByCpf(cpf);		
			if(!(userOption != null && !userOption.isEmpty())) 
			{
				response.put(false, "USUÁRIO NÃO ENCONTRADO!");
				return response;
			}
					
			Usuario user = userOption.get();		
			if(!user.getEmail().equals(email))
			{
				response.put(false, "O EMAIL É DIFERENTE AO CADASTRADO! ENTRE EM CONTATO ATRAVÉS DO " + EmailInfo.EMAIL_ADMINISTRADOR);
				return response;			
			}
			if(!user.getCoupon().equals(coupon))
			{
				response.put(false, "O NUMERO DO CUPOM É DIFERENTE AO CADASTRADO! PEÇA O REENVIOU DO CUPOM SE NECESSÁRIO!");
				return response;			
			}
			
			String newPassword = getNewPassword();
			String emailPassword = String.valueOf(newPassword);
			
			newPassword = StringUtils.encrypt(newPassword);		
			user.setPassword(newPassword.getBytes());
			user.setIsAlterPassword(true);
			
			usuarioRepository.save(user);
			
			SendEmail sendEmail = new SendEmail();
			sendEmail.setBody("Olá " + user.getName() +  "\nFoi solicitado a Redifinição de sua Senha. \nSua senha foi Redifinida! \nPassword: " + emailPassword + "\n Altere sua senha assim que acessar o sistema!");
			sendEmail.setDateRegister(new Date());
			sendEmail.setRecipient(user.getEmail());
			sendEmail.setSubject("REDIFINIÇÃO DA SENHA");			
			emailRepository.save(sendEmail);
			
//			new Thread() 
//			{				
//				@Override
//				public void run() 
//				{	
//					EmailUtil email = new EmailUtil();
//					email.sendEmail(user.getEmail(), "REDIFINIÇÃO DA SENHA", "Olá " + user.getName() +  "\nFoi solicitado a Redifinição de sua Senha. \nSua senha foi Redifinida! \nPassword: " + emailPassword + "\n Altere sua senha assim que acessar o sistema!");
//				};
//			}.start();			
	
			response.put(true, "SENHA REDIFINIDA E ENVIADA POR EMAIL!");
			return response;		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			Map<Boolean, String> response = new HashMap<Boolean, String>();
			response.put(false, "Error ao tentar enviar o Email, se o erro persistir envie um email para " + EmailInfo.EMAIL_ADMINISTRADOR);
			return response;
		}
	}

	private String getNewPassword()
	{	
		Random random = new Random();		
		StringBuilder password = new StringBuilder();		
		for (int i = 0; i < 8; i++) 
		{
			password.append(random.nextInt(8));			
			if(i > 0 && (i % 2) == 0) password.append(IntString.valueOff(random.nextInt(8)).toLowerCase());
		}				
		return password.toString();
	}

	public InfoUserDTO getInfoUser(String userName)
	{		
		try
		{
			Optional<Usuario> userOption = usuarioRepository.findByCpf(userName);		
			if(!(userOption != null && !userOption.isEmpty())) userOption = usuarioRepository.findByEmail(userName);			
			if(!(userOption != null && !userOption.isEmpty())) return null;
			
			Usuario user = userOption.get();			
			return user.getInfoUserDTO();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	public String alterPassword(String cpf, String password, String confirmPassword, String oldPassword) 
	{	
		if(cpf == null) 				return "Usuário não foi Informado!";	
		if(password == null) 			return "O Campo Senha é obrigatório";	
		if(confirmPassword == null) 	return "O Campo Confirme Senha é obrigatório";	
		if(oldPassword == null) 		return "Você deve infomar a senha Antiga!";	
				
		if(!confirmPassword.equals(password)) return "Os campos Senha e Confirme Senha são diferentes";
		if(password.length() < 8) 		return "A senha deve ter no mínimo 8 dígitos";
				
		Optional<Usuario> optional = usuarioRepository.findByCpf(cpf);
		
		if(!(optional != null && !optional.isEmpty())) return "Usuário não encontrado!";		
		Usuario user = optional.get();	
			
		
		if(!StringUtils.validPassword(oldPassword, new String(user.getPassword()))) return "A senha Antiga está incorreta!";				
		if( user.getRegistrationSituation() == Usuario.USUARIO_BLOQUEADO) return "Este usuário está bloqueado temporariamente, Dúvidas, nos contacte! " + EmailInfo.EMAIL_ADMINISTRADOR;
		if( user.getRegistrationSituation() < Usuario.CADASTRO_APROVADO) return "Cadastro do usuário não está aprovado ainda! Dúvidas, nos conatecte!"  + EmailInfo.EMAIL_ADMINISTRADOR;
		
		user.setPassword(StringUtils.encrypt(password).getBytes());
		
		
		Assessment ass = new Assessment();
		
		ass.setDate(new Date());
		ass.setDescription("Gerado Automaticamente para fins de teste");
		ass.setEvaluator(user);
		ass.setRated(user);
		ass.setStatus(true);
		ass.setNote(4);
		
		Assessment ass1 = new Assessment();
		ass1.setDate(new Date());
		ass1.setDescription("Gerado Automaticamente para fins de teste");
		ass1.setEvaluator(user);
		ass1.setRated(user);
		ass1.setStatus(true);
		ass1.setNote(3);
		
		Assessment ass2 = new Assessment();
		ass2.setDate(new Date());
		ass2.setDescription("Gerado Automaticamente para fins de teste");
		ass2.setEvaluator(user);
		ass2.setRated(user);
		ass2.setNote(1);
		ass2.setStatus(true);
		
		assessRepository.save(ass);
		assessRepository.save(ass1);	
		assessRepository.save(ass2);
		
	
	
		usuarioRepository.save(user);		
		return null;
	}

	public String updatePicture(String cpf, byte[] image)
	{
		if(cpf == null) 			return "Usuário não foi Informado!";	
		if(image == null) 			return "É Necessário enviar uma imagem";		
		
		Optional<Usuario> optional = usuarioRepository.findByCpf(cpf);		
		if(!(optional != null && !optional.isEmpty())) return "Usuário não encontrado!";		
		Usuario user = optional.get();	
				
		if( user.getRegistrationSituation() == Usuario.USUARIO_BLOQUEADO) return "Este usuário está bloqueado temporariamente, Dúvidas, nos contacte! " + EmailInfo.EMAIL_ADMINISTRADOR;
		if( user.getRegistrationSituation() < Usuario.CADASTRO_APROVADO) return "Cadastro do usuário não está aprovado ainda! Dúvidas, nos conatecte!"  + EmailInfo.EMAIL_ADMINISTRADOR;		
		user.setImagePortifile(image);
		
		usuarioRepository.save(user);		
		return null;
	}

	@Override
	public String register(Usuario object, Usuario user) 
	{
		return null;
	}

	public Usuario findUserByToken(String token) 
	{		
		boolean isCpf = false;
		if(token.toString().contains(ConstantsSecurity.TOKE_PREFIX_CPF)) 
		{
			token = token.replace(ConstantsSecurity.TOKE_PREFIX_CPF, "").trim();
			isCpf = true;
		}
		else if(token.toString().contains(ConstantsSecurity.TOKE_PREFIX_EMAIL)) token = token.replace(ConstantsSecurity.TOKE_PREFIX_EMAIL, "").trim(); 
					
		String usertoken = Jwts.parser()
				.setSigningKey(ConstantsSecurity.SECRET)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();			
		
		Optional<Usuario> usuario = null;
		if(isCpf) 	usuario = usuarioRepository.findByCpf(usertoken);
		else		usuario = usuarioRepository.findByEmail(usertoken);					
		
		return usuario != null  && !usuario.isEmpty()? usuario.get() : null;			
	}
}
