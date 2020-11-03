package br.com.diarista.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
		.and().csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.PUT).permitAll()
		.antMatchers("/rest/contract/**").permitAll() 
		.antMatchers("/rest/usuario/participate").permitAll() 
		.antMatchers("/rest/usuario/invitation").permitAll() 
		.anyRequest().authenticated()
		.and()	
		.addFilter(new JWTAutentificationFilter(authenticationManager()))
		.addFilter(new AuthorizationFilter(authenticationManager(), userDetailService));
	}
		
	
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() 
//    {
//        CorsConfiguration configuration = new CorsConfiguration();
//        
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedHeader("accept");
//        configuration.addAllowedHeader("Authorization");
//        configuration.addAllowedHeader("content-type");
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
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception 
//	{
//		http.authorizeRequests()
//		.antMatchers("/rest/contract/**").permitAll() 
//		.antMatchers("/rest/usuario/participate").permitAll() 
////		.antMatchers("/rest/usuario/invitation").permitAll() 
////		.antMatchers("/rest/marital_status/*").hasAnyRole("ADMIN") 
////		.antMatchers("/rest/model/**").permitAll() 
////		.antMatchers("/rest/adress/**").permitAll() 
////		.antMatchers("/rest/nationality/**").permitAll() 
////		.antMatchers("/rest/issuing_department/**").permitAll() 
////		.antMatchers("/rest/uf/**").permitAll() 
//		.anyRequest().authenticated()
//		.and().httpBasic().and().csrf().disable();
//	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception
//	{		
//		auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
//	}
}
