package br.com.diarista.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.diarista.dao.UsuarioDAO;
import br.com.diarista.entity.Usuario;
import br.com.diarista.utils.StringUtils;

@Component
public class CustomUserDetailService implements UserDetailsService
{
	@Autowired
	private UsuarioDAO userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{		
		boolean isCoupon = false;	
		Optional<Usuario>  user = userRepository.findByEmail(username);		
		Usuario userLoading = null;
		
		if(!(user != null && !user.isEmpty()))	user = userRepository.findByCpf(StringUtils.removeCharacters(username));			
		if(!(user != null && !user.isEmpty()))
		{
			 user = userRepository.findByCoupon(username);		
			 if(!(user != null && !user.isEmpty()) && !(user.get().getRegistrationSituation() != null &&  user.get().getRegistrationSituation() > 2)) throw new UsernameNotFoundException("USUÁRIO NÃO ENCONTRADO");	
			 isCoupon = true;
			 userLoading = user.get();	
		}	
		else
		{
			userLoading = user.get();	
			if(userLoading.getRegistrationSituation() < 4) throw new UsernameNotFoundException("USUÁRIO NÃO APROVADO OU EM ANALISE");	
			else if(userLoading.getRegistrationSituation() == 6) throw new UsernameNotFoundException("USUÁRIO BLOQUEADO");				
		}
				
		String password = userLoading.getPassword() != null && !isCoupon? new String(userLoading.getPassword()) : StringUtils.encrypt("faxinex1818");			
		
		User userReading = null;
		if(isCoupon) 						userReading = new User(userLoading.getCoupon(), password, 	AuthorityUtils.createAuthorityList("INVITE"));
		else if (username.contains("@"))  	userReading = new User(userLoading.getEmail(), password,  	AuthorityUtils.createAuthorityList("EMAIL"));
		else 		 						userReading = new User(userLoading.getCpf(), password, 		AuthorityUtils.createAuthorityList("CPF"));
		return userReading;		
	}
}
