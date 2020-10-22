package br.com.diarista.conf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import br.com.diarista.business.UsuarioBusiness;

@Component
public class FilterWeb implements Filter
{
	@Override
	public void doFilter(ServletRequest requestServlet, ServletResponse responseServlet, FilterChain chain)	throws IOException, ServletException 
	{		
		HttpServletRequest request = (HttpServletRequest) requestServlet;
		HttpServletResponse response = (HttpServletResponse) responseServlet;

		//Adiciona cabeçario para que o Angular aceite a resposta
		response.setHeader("server-header", "WildFly/19");
		response.setHeader("x-powered-by-header", "Undertow/1");		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT");
		response.setHeader("Access-Control-Allow-Headers", "accept, authorization, content-type, x-requested-with, enctype");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "1");	

		try
		{		
			//exceções para que o usuário inicie o cadastramento
			if(	request.getRequestURI().contains("/diarista/rest/usuario/participate") 	|| 
				request.getRequestURI().contains("/diarista/rest/usuario/invitation")  	|| 
				request.getRequestURI().contains("/diarista/rest/login/authenticate")  	||			
				request.getRequestURI().contains("/diarista/rest/model")  				||
				request.getRequestURI().contains("/diarista/rest/contract/all_active") 	||	
				request.getRequestURI().contains("/diarista/rest/usuario/download_contrato") 	||	
				(!request.getMethod().equalsIgnoreCase("POST") && !request.getMethod().equalsIgnoreCase("GET"))) 
			{
				chain.doFilter(request,response);	
				return;
			}		
			else
			{
				String token = request.getHeader("Authorization");		

				if(token == null)
				{					
					response.setStatus(401);	
					return;
				}
				if(UsuarioBusiness.getToken() == null)
				{					
					response.setStatus(401);	
					return;
				}

				if(!token.equals(UsuarioBusiness.getToken()))
				{
					response.setStatus(401);	
					return;			
				}
				
				//restringe o usuário para opções do convite, para que o mesmo não tenha acesso ao sistema, visto que ele tem um token para poder realizar o resto do cadastro
				if(token.contains("INV-2020") && (
						request.getRequestURI().contains("/diarista/rest/nationality/all_active") 	 	|| 
						request.getRequestURI().contains("/diarista/rest/marital_status/all_active") 	||
						request.getRequestURI().contains("/diarista/rest/adress/locality") 			 	||
						request.getRequestURI().contains("/diarista/rest/usuario/register")  			|| 
						request.getRequestURI().contains("/diarista/rest/usuario/send_mail_contrato") 	|| 
						request.getRequestURI().contains("/diarista/rest/uf/all_active")  				||
						request.getRequestURI().contains("/diarista/rest/issuing_department/all_active")
						))
				{						
					chain.doFilter(request, response);	
					return;			
				}
				
				//vefica se o token é do login, se conter esse prefixo, ele valída o token de login
				else if(token.contains("DIA-2020"))
				{
					chain.doFilter(request, response);	
					return;			
				}
				response.setStatus(401);
			}

		}
		catch (Exception e) 
		{
			e.printStackTrace();			
		}	
	}
}
