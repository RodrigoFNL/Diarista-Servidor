package br.com.diarista.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.diarista.service.CustomUserDetailService;

@EnableWebSecurity
public class SecurityFilter extends WebSecurityConfigurerAdapter
{
	@Autowired
	private CustomUserDetailService userDetailService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.authorizeRequests()
		.antMatchers("/rest/contract/**").permitAll() 
		.antMatchers("/rest/usuario/participate").permitAll() 
//		.antMatchers("/rest/usuario/invitation").permitAll() 
//		.antMatchers("/rest/marital_status/*").hasAnyRole("ADMIN") 
//		.antMatchers("/rest/model/**").permitAll() 
//		.antMatchers("/rest/adress/**").permitAll() 
//		.antMatchers("/rest/nationality/**").permitAll() 
//		.antMatchers("/rest/issuing_department/**").permitAll() 
//		.antMatchers("/rest/uf/**").permitAll() 
		.anyRequest().authenticated()
		.and().httpBasic().and().csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{		
		auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
