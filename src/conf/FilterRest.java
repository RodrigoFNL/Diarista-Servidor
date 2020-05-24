package conf;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Intercepta a conexão e verifica se o token é válido.
 * igora o token nos casos de inicializar o token ou módulo de participar.
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
		
		try
		{		
			if(request.getRequestURI().equals("/diarista-1.0/rest/participate/generate_coupon") || request.getRequestURI().equals("/diarista-1.0/rest/login/authenticate")) 
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
				else if(token.isEmpty()) 
				{
					response.setStatus(401);	
					return;
				}
				chain.doFilter(request,response);		
			}

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			response.setStatus(401);
			return;
		}
	}
}
