package br.com.diarista.conf;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import br.com.diarista.service.CustomUserDetailService;

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


	//    @Bean
	//    CorsConfigurationSource corsConfigurationSource() 
	//    {
	//        CorsConfiguration configuration = new CorsConfiguration();
	//        
	//        configuration.addAllowedOrigin("*");
	//        configuration.addAllowedHeader("accept");
	//        configuration.addAllowedHeader("Authorization");
	//        configuratio
	//        configuration.addAllowedHeader("x-requested-with");
	//        configuration.addAllowedHeader("enctype");        
	//        configuration.setAllowedMethods(Arrays.asList("GET, POST, OPTIONS, PUT"));
	//        configuration.setAllowCredentials(true);
	//        configuration.setMaxAge(1L);        
	//        configuration.addExposedHeader("Undertow/1"); //verificar	response.setHeader("x-powered-by-header", "Undertow/1");
	//        
	//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	//        source.registerCorsConfiguration("/**", configuration);
	//        return source;
	//   }


}
