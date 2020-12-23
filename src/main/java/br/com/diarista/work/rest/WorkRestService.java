package br.com.diarista.work.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diarista.conf.ConstantsSecurity;
import br.com.diarista.conf.EmailInfo;
import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.business.UsuarioBusiness;
import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.folks.rest.BasicRestServe;
import br.com.diarista.work.business.WorkBusiness;
import br.com.diarista.work.dto.WorkDTO;
import br.com.diarista.work.entity.Work;

@RestController
@RequestMapping("/rest/work")
public class WorkRestService extends BasicRestServe<Work>
{
	@Autowired
	private WorkBusiness business;	
	
	@Autowired
	private UsuarioBusiness userBusiness;
	
	@Override
	protected BasicBusiness<Work> business()
	{		
		return business;
	}	
	
	@PostMapping("/get_all_opportunities")
	public Map<String, Object> getAllOpportunities(@RequestBody Map<String, Object> postObject, HttpServletRequest request)
	{
		try
		{				
			String token = request.getHeader(ConstantsSecurity.HEADER);					
			Usuario user = userBusiness.findUserByToken(token);
			
			if(user == null) 													return null;
			if(user.getRegistrationSituation() != Usuario.CADASTRO_APROVADO) 	return null;			
								
			int page = postObject.get("page") != null? (int) postObject.get("page") : 0;
			int size = postObject.get("size") != null? (int) postObject.get("size") : 10;
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			List<WorkDTO> works = business.getAllActive(user, page, size);
			List<Long> lengths =  business.countAllActive(user);
	
			map.put("works", works);
			map.put("lengths", lengths);
			
			return map;		
		}
		catch (Exception e)
		{	
			return null;
		}
	}	
	
	@PostMapping("/insert_cleaning_lady")
	public ResponseEntity<Object> insertCleaningLady(@RequestBody Work work, HttpServletRequest request)
	{
		try
		{				
			String token = request.getHeader(ConstantsSecurity.HEADER);					
			Usuario user = userBusiness.findUserByToken(token);		
			if(user == null) 													return error("Usuário não encontrado na base de dados!", BasicRestServe.BAD_REQUEST);
			if(user.getRegistrationSituation() != Usuario.CADASTRO_APROVADO) 	return error("Situação do Usuário irregular, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.BAD_REQUEST);
				
			String resp = business.insertCleaningLayd(work, user);		
			
			if(resp == null)  return ok("OK");
			else return error(resp, BasicRestServe.INTERNAL_ERROR);				
		}
		catch (Exception e)
		{	
			return error("Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.BAD_REQUEST);
		}
	}	
}
