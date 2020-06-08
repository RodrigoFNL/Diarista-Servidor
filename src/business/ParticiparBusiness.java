package business;

import java.util.Date;
import java.util.Map;

import javax.ejb.Stateful;
import javax.inject.Inject;

import dao.UsuarioDAO;
import model.Usuario;
import util.EmailUtil;
import util.StringUtils;

@Stateful
public class ParticiparBusiness 
{	
	@Inject
	private UsuarioDAO usuarioRepository;

	public String createCoupon(Map<String, Object> postObject) 
	{	
		try
		{
			String name = StringUtils.notEnpty((String) postObject.get("name"))		? (String) postObject.get("name"):	null;
			String cpf =  StringUtils.notEnpty((String) postObject.get("cpf"))		? (String) postObject.get("cpf"): 	null;
			String email = StringUtils.notEnpty((String) postObject.get("email"))	? (String) postObject.get("email"): null;
			Boolean accept = postObject.get("accept") != null? (Boolean) postObject.get("accept"): false;

			if(name == null) 			return "Você deve fornecer um Nome";
			else if(cpf == null)  		return "Você deve fornecer um CPF";
			else if(email == null) 		return "Você deve fornecer um Email";
			else if(!accept)			return "Você deve aceitar os termos e condições";

			Usuario user = new Usuario();
			user.setCoupon(StringUtils.generateCoupon(new Date().getTime()));
			user.setName(name);
			user.setCpf(cpf);
			user.setEmail(email);
			user.setTermosCondicoes(accept);

			Boolean persiste = usuarioRepository.save(user);
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
			else  return "Não foi possível cadastrar o usúario";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Ocorreu um erro, ao tentar salvar as informações no banco";
		}
	}

}
