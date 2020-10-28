package br.com.diarista.conf;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityFilter extends WebSecurityConfigurerAdapter
{
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.csrf().disable().authorizeRequests()
		.antMatchers("/rest/contract/**").permitAll() 
		.antMatchers("/rest/model/**").permitAll() 
		.antMatchers("/rest/usuario/participate").permitAll() 
		.antMatchers("/rest/usuario/invitation").permitAll() 
//		.antMatchers("/rest/marital_status/**").permitAll() 
//		.antMatchers("/rest/adress/**").permitAll() 
//		.antMatchers("/rest/nationality/**").permitAll() 
//		.antMatchers("/rest/issuing_department/**").permitAll() 
//		.antMatchers("/rest/uf/**").permitAll() 
		.anyRequest().authenticated();
	}
}
