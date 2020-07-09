package conf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.UsuarioBusiness;


/**
 * Intercepta a conexão e verifica se o token é válidoo.
 * igora o token nos casos de login ou cadastro do participar
 * @author Rodrigo
 *
 */

@WebFilter("/rest/*") 
public class FilterRest implements Filter
{
	@Override
	public void doFilter(ServletRequest requestServlet, ServletResponse responseServlet, FilterChain chain) throws IOException, ServletException 
	{	
		HttpServletRequest request = (HttpServletRequest) requestServlet;
		HttpServletResponse response = (HttpServletResponse) responseServlet;

		response.setHeader("server-header", "WildFly/19");
		response.setHeader("x-powered-by-header", "Undertow/1");		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT");
		response.setHeader("Access-Control-Allow-Headers", "accept, authorization, content-type, x-requested-with");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "1");	

		try
		{		
			if(	request.getRequestURI().contains("/diarista/rest/usuario/participate") 	|| 
				request.getRequestURI().contains("/diarista/rest/usuario/invitation")  	|| 
				request.getRequestURI().contains("/diarista/rest/login/authenticate")  	||			
				request.getRequestURI().contains("/diarista/rest/model")  				||
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

				chain.doFilter(request, response);		
			}

		}
		catch (Exception e) 
		{
			e.printStackTrace();			
			response.setStatus(401);			
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{		

	}

	@Override
	public void destroy()
	{

	}

}
