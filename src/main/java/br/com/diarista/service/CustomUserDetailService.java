package br.com.diarista.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
		Optional<Usuario>  user = userRepository.findByEmail(username);		
		if(!(user != null && !user.isEmpty())) user = userRepository.findByCpf(StringUtils.removeCharacters(username));		
		if(!(user != null && !user.isEmpty()))
		{
			 user = userRepository.findByCoupon(username);		
			 if(!(user != null && !user.isEmpty()) && !(user.get().getRegistrationSituation() != null &&  user.get().getRegistrationSituation() > 2)) throw new UsernameNotFoundException("USUÁRIO NÃO ENCONTRADO");	
		}			
		
		Usuario userLoading = user.get();					
		String password = userLoading.getPassword() != null? new String(userLoading.getPassword()) : "faxinex1818";			
		List<GrantedAuthority> granted = null;
		
		if(!(userLoading.getRegistrationSituation() != null &&  userLoading.getRegistrationSituation() > 2)) 	granted = AuthorityUtils.createAuthorityList("CORE", "REGISTER");	
		else if(userLoading.getRegistrationSituation() > 2 && userLoading.getIsContratar() && userLoading.getIsPrestar_servico()) 	granted = AuthorityUtils.createAuthorityList("CORE", "HIRED", "TOHIRE");
		else if(userLoading.getRegistrationSituation() > 2 && userLoading.getIsContratar()) 					granted = AuthorityUtils.createAuthorityList("CORE", "HIRED");
		else if(userLoading.getRegistrationSituation() > 2 && userLoading.getIsPrestar_servico()) 				granted = AuthorityUtils.createAuthorityList("CORE", "TOHIRE");
		else if(userLoading.getRegistrationSituation() == 5) 													granted = AuthorityUtils.createAuthorityList("CORE", "ADMIN");
		User userReading = new User(userLoading.getCoupon(), StringUtils.encrypt(password), granted);

		return userReading;		
	}
}
