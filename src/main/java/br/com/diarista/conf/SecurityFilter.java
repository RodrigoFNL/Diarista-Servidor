package br.com.diarista.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import br.com.diarista.folks.rest.CustomUserDetailService;

@EnableWebSecurity
public class SecurityFilter extends WebSecurityConfigurerAdapter
{
	@Autowired
	private CustomUserDetailService userDetailService;

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{	
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
		.and()
		.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.PUT).permitAll()
		.antMatchers("/rest/model/**").permitAll() 
		.antMatchers("/rest/contract/**").permitAll() 
		.antMatchers("/rest/usuario/participate").permitAll() 
		.antMatchers("/rest/usuario/invitation").permitAll()
		.antMatchers("/rest/usuario/resend_coupon").permitAll()
		.antMatchers("/rest/usuario/reset_password").permitAll()
		.antMatchers("/rest/usuario/download_contrato/**").permitAll()
		.anyRequest().authenticated()
		.and()	
		.addFilter(new JWTAutentificationFilter(authenticationManager()))
		.addFilter(new AuthorizationFilter(authenticationManager(), userDetailService));
	}

	@Bean
	public PasswordEncoder passwordEncoder() 
	{
		return new BCryptPasswordEncoder();
	}
}
