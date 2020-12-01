package br.com.diarista.folks.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.diarista.conf.ConstantsSecurity;
import br.com.diarista.conf.EmailInfo;
import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.dao.UsuarioDAO;
import br.com.diarista.folks.entity.Usuario;
import io.jsonwebtoken.Jwts;

public abstract class BasicRestServe <E>
{
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int INTERNAL_ERROR = 500;	
	protected abstract BasicBusiness<E> business();	
	
	@Autowired UsuarioDAO userRepository;
	
	@PostMapping("/register")
	public  ResponseEntity<Object> register(@RequestBody E object, HttpServletRequest request)
	{		
		try
		{		
			String token = request.getHeader(ConstantsSecurity.HEADER);		
			boolean isCpf = false;
			
			if(token.toString().contains(ConstantsSecurity.TOKE_PREFIX_CPF)) 
			{
				token = token.replace(ConstantsSecurity.TOKE_PREFIX_CPF, "").trim();
				isCpf = true;
			}
			else if(token.toString().contains(ConstantsSecurity.TOKE_PREFIX_EMAIL)) token = token.replace(ConstantsSecurity.TOKE_PREFIX_EMAIL, "").trim(); 
						
			String usertoken = Jwts.parser()
					.setSigningKey(ConstantsSecurity.SECRET)
					.parseClaimsJws(token)
					.getBody()
					.getSubject();			
			
			Optional<Usuario> usuario = null;
			if(isCpf) 	usuario = userRepository.findByCpf(usertoken);
			else		usuario = userRepository.findByEmail(usertoken);					
			
			Usuario user = usuario != null  && !usuario.isEmpty()? usuario.get() : null;	
			
			if(object == null) 	return error("As informações estão nulas", BasicRestServe.BAD_REQUEST);
			if(user == null) 	return error("Usuário não encontrado na base de dados!", BasicRestServe.BAD_REQUEST);
			if(user.getRegistrationSituation() != Usuario.CADASTRO_APROVADO) return error("Situação do Usuário irregular, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.BAD_REQUEST);
							
			String response = business().register(object, user);			
			if(response.contains("id:")) 	return ok(response.replace("id:", ""));		
			else 							return error(response, BasicRestServe.INTERNAL_ERROR);		
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();			
			return error("Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.INTERNAL_ERROR);
		}
	}
		
	@RequestMapping("/all_active")
	public List<?> getAllActive()
	{	
		try
		{
			List<?> dtos = business().getAllActive();				
			if(dtos == null) return new ArrayList<Object>();
			else return dtos;			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ArrayList<Object>();
		}
	}
	
	protected ResponseEntity<Object> ok(String message)
	{		
		if(message == null) message = "Erro não especificado!";				
		Map<String, Object> mapMessage = new HashMap<String, Object>();
		mapMessage.put("status", true);
		mapMessage.put("message", message);		
		return ResponseEntity.ok(mapMessage);	
	}
	
	protected ResponseEntity<Object> okJson(String json)
	{		
		Map<String, Object> send = new HashMap<String, Object>();
		
		if(json == null)
		{		
			send.put("entity", null);
			send.put("status", false);
			send.put("message", "Erro não especificado!");	
		}
		else
		{	
			send.put("entity", json);
			send.put("status", true);
			send.put("message", "Arquivo encontrado");				
		}			
		return ResponseEntity.ok(send);	
	}
	
	protected ResponseEntity<Object> ok(Object object)
	{		
		Map<String, Object> send = new HashMap<String, Object>();
		
		if(object == null)
		{
			send.put("entity", null);
			send.put("status", false);
			send.put("message", "Erro não especificado!");		
			return ResponseEntity.ok(send);	
		}
			
		send.put("entity", object);
		send.put("status", true);
		send.put("message", "Arquivo encontrado");			
		return ResponseEntity.ok(send);	
	}
	
	protected ResponseEntity<Object> error(String message, int basicRestServeType)
	{	

		Map<String, Object> mapMessage = new HashMap<String, Object>();
		mapMessage.put("status", true);
		mapMessage.put("message", message);
		return ResponseEntity.status(basicRestServeType).body(mapMessage);
	}		
}
