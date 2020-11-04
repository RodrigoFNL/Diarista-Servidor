//package br.com.diarista.conf;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//
//@Component
//public class FilterWeb extends GenericFilterBean 
//{
//	@Override
//	public void doFilter(ServletRequest requestServlet, ServletResponse responseServlet, FilterChain chain)	throws IOException, ServletException 
//	{		
//		HttpServletRequest request = (HttpServletRequest) requestServlet;
//		HttpServletResponse response = (HttpServletResponse) responseServlet;
//
//		//Adiciona cabeçario para que o Angular aceite a resposta
//		response.setHeader("server-header", "WildFly/19");
//		response.setHeader("x-powered-by-header", "Undertow/1");		
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT");
//		response.setHeader("Access-Control-Allow-Headers", "accept, authorization, content-type, x-requested-with, enctype");
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Access-Control-Max-Age", "1");	
//
//		chain.doFilter(request,response);
//	}
//}