package br.com.diarista.conf;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.diarista.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAutentificationFilter  extends UsernamePasswordAuthenticationFilter
{
	private AuthenticationManager authManager;
	
	public JWTAutentificationFilter(AuthenticationManager authManager) 
	{
		this.authManager = authManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException 
	{
		try 
		{
			UserDTO dto = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);				
			return authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword()));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw  new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException 
	{	
		((User) authResult).getUsername() ;
		
		String token = Jwts.builder().setSubject(((User) authResult.getPrincipal())
									 .getUsername())
									 .setExpiration(new Date(System.currentTimeMillis() + ConstantsSecurity.EXPIRATION_TIME)) 
									 .signWith(SignatureAlgorithm.HS512, ConstantsSecurity.SECRET).compact();	
		
		response.addHeader(ConstantsSecurity.HEADER, ConstantsSecurity.TOKE_PREFIX_INVITE + token);
	}	
}
