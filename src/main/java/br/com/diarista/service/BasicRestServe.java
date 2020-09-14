package br.com.diarista.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.diarista.business.BasicBusiness;

public abstract class BasicRestServe <E>
{
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int INTERNAL_ERROR = 500;		
	
	protected abstract BasicBusiness<E> business();		
	
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
