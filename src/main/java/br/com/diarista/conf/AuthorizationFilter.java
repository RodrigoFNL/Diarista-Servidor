package br.com.diarista.conf;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.diarista.folks.rest.CustomUserDetailService;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter
{	
	private final CustomUserDetailService userDetailService;
	
	public AuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailService userDetailService) 
	{
		super(authenticationManager);	
		this.userDetailService = userDetailService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		String token = request.getHeader(ConstantsSecurity.HEADER);
		
		if(token == null)
		{
			chain.doFilter(request, response);
			return;		
		}
			
		HttpServletRequest requestServlet = (HttpServletRequest) request;
		String userName = null;
				
		if(token.contains(ConstantsSecurity.TOKE_PREFIX_CPF)) 	 		userName = Jwts.parser().setSigningKey(ConstantsSecurity.SECRET).parseClaimsJws(token.replace(ConstantsSecurity.TOKE_PREFIX_CPF, "")).getBody().getSubject();
		else if(token.contains(ConstantsSecurity.TOKE_PREFIX_EMAIL)) 	userName = Jwts.parser().setSigningKey(ConstantsSecurity.SECRET).parseClaimsJws(token.replace(ConstantsSecurity.TOKE_PREFIX_EMAIL, "")).getBody().getSubject();
		else if(token.contains(ConstantsSecurity.TOKE_PREFIX_INVITE)) 	userName = Jwts.parser().setSigningKey(ConstantsSecurity.SECRET).parseClaimsJws(token.replace(ConstantsSecurity.TOKE_PREFIX_INVITE, "")).getBody().getSubject();
		else throw new ServletException("Token inválido");
			
		if(userName != null)
		{
			UserDetails user = userDetailService.loadUserByUsername(userName);
			if (user == null) throw new ServletException("Usuário Não encontrado!");
			
			UsernamePasswordAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);			
			
			if(token.contains(ConstantsSecurity.TOKE_PREFIX_INVITE) && 
					(	requestServlet.getRequestURI().contains("/diarista/rest/usuario/register") 				||			
						requestServlet.getRequestURI().contains("/diarista/rest/usuario/participate") 			|| 
						requestServlet.getRequestURI().contains("/diarista/rest/usuario/invitation")  			|| 			
						requestServlet.getRequestURI().contains("/diarista/rest/model")  						||
						requestServlet.getRequestURI().contains("/diarista/rest/contract/all_active") 			||	
						requestServlet.getRequestURI().contains("/diarista/rest/nationality/all_active") 	 	|| 
						requestServlet.getRequestURI().contains("/diarista/rest/marital_status/all_active") 	||
						requestServlet.getRequestURI().contains("/diarista/rest/adress/locality") 			 	||
						requestServlet.getRequestURI().contains("/diarista/rest/usuario/register")  			|| 
						requestServlet.getRequestURI().contains("/diarista/rest/usuario/send_mail_contrato") 	|| 
						requestServlet.getRequestURI().contains("/diarista/rest/uf/all_active")  				||
						requestServlet.getRequestURI().contains("/diarista/rest/issuing_department/all_active") ||
						requestServlet.getRequestURI().contains("/diarista/rest/usuario/download_contrato")
					))				
				   						
			{						
				chain.doFilter(request, response);	
				return;			
			}	
			
			if((token.contains(ConstantsSecurity.TOKE_PREFIX_CPF) || token.contains(ConstantsSecurity.TOKE_PREFIX_EMAIL) && !requestServlet.getRequestURI().contains("/diarista/rest/usuario/register")))
			{		
				chain.doFilter(request, response);	
				return;			
			}	
			else throw new ServletException("Token sem permissão!");
			
		}
		else throw new ServletException("Usuário Não encontrado!");
	}
}



























