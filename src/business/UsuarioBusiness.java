package business;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.inject.Inject;

import dao.UsuarioDAO;
import dto.UserDTO;
import dto.UsuarioDTO;
import model.Usuario;
import util.EmailUtil;
import util.StringUtils;

@Stateful
public class UsuarioBusiness  extends BasicBusiness<Usuario, UsuarioBusiness>
{		
	private static String token ;
	
	@Inject
	private UsuarioDAO usuarioRepository;

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
			
			Usuario hasDuplicated = usuarioRepository.findById(cpf);
			if(hasDuplicated != null && hasDuplicated.getLogin() != null) return "Já existe um usuário cadastrado neste CPF.";
						
			Usuario user = new Usuario();			
			user.setCoupon(StringUtils.generateCoupon(new Date().getTime()));
			user.setName(name);
			user.setCpf(cpf);
			user.setEmail(email);
			user.setTermosCondicoes(accept);
			
			Boolean persiste = false;
			if(hasDuplicated == null) persiste =  usuarioRepository.save(user);
			else 					  persiste =  usuarioRepository.update(user);
			
			if(persiste)
			{
				
				new Thread() 
				{
					@Override
					public void run() 
					{
						EmailUtil.sendCoupon(user.getEmail(), user.getCoupon());
					};
				}.start();				
				
				return "id:" + user.getCpf();
			}
			else  return "Não foi possível cadastrar o usuário";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Ocorreu um erro, ao tentar salvar as informações no banco";
		}
	}

	public UsuarioDTO getInfoUserByCodigo(String invitation) 
	{	
		Usuario user = usuarioRepository.findByColumn("coupon", invitation);	
		UsuarioDTO dto = user != null && user.getLogin() == null ? user.getDTO() : null;	
		
		if(dto != null) createToken(dto);
		
		return dto;		
	}

	private void createToken(UsuarioDTO dto) 
	{
		Date date = new Date();
		Long time = date.getTime();		
		String token =  StringUtils.encrypt(time.toString() + dto.getCpf());
		
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

}
