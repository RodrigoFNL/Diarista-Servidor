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
	
	@PostMapping("/count_my_oportunity")
	public Long countMyOportunity(@RequestBody Map<String, Object> postObject)
	{
		try
		{				
			String cpf =  postObject.containsKey("cpf") ? (String) postObject.get("cpf"): null;				
			Integer view =  postObject.containsKey("view") ? (Integer) postObject.get("view"): null;	
			
			if(cpf == null) return 0l;
			if(view == null) return 0l;
			
			return business.countMyOportunity(cpf, view);		
		}
		catch (Exception e)
		{	
			return 0l;
		}
	}	
		
	@PostMapping("/get_all_publications")
	public Map<String, Object> getAllPublications(@RequestBody Map<String, Object> postObject, HttpServletRequest request)
	{
		try
		{				
			String token = request.getHeader(ConstantsSecurity.HEADER);					
			Usuario userToken = userBusiness.findUserByToken(token);
			if(userToken == null) return null;
			
			String cpf =  postObject.containsKey("cpf") ? (String) postObject.get("cpf"): null;					
			Map<String, Object> map = new HashMap<String, Object>();	
			
			if(!userToken.getCpf().equals(cpf)) return null;	
			
			Integer limit  =  postObject.containsKey("pagSize") 	? (Integer) postObject.get("pagSize"): 0;	
			Integer page   =  postObject.containsKey("pageIndex")	? (Integer) postObject.get("pageIndex"): 5;				
			
			Usuario user = userBusiness.findByCPF(cpf);			
			if(user == null) return null;
			
			map.put("list",		business.getAllMyPuclications(user, page * limit, limit));
			map.put("length",	business.countMyOportunity(cpf, Work.VIEW_CONTRATAR));
			
			return 	map;		
		}
		catch (Exception e)
		{	
			e.printStackTrace();
			return null;
		}
	}		
	
	
	@PostMapping("/get_all_opportunities_cleaning_lady")
	public Map<String, Object> getAllOpportunitiesCleaningLady(@RequestBody Map<String, Object> postObject, HttpServletRequest request)
	{
		try
		{				
			String token = request.getHeader(ConstantsSecurity.HEADER);					
			Usuario userToken = userBusiness.findUserByToken(token);
			if(userToken == null) return null;
			
			String cpf =  postObject.containsKey("cpf") ? (String) postObject.get("cpf"): null;					
			Map<String, Object> map = new HashMap<String, Object>();	
			
			if(!userToken.getCpf().equals(cpf)) return null;			
			
			Integer limit  =  postObject.containsKey("pagSize") 	? (Integer) postObject.get("pagSize"): 0;	
			Integer page   =  postObject.containsKey("pageIndex")	? (Integer) postObject.get("pageIndex"): 5;				
			
			Usuario user = userBusiness.findByCPF(cpf);			
			if(user == null) return null;
			
			
			map.put("list",		business.getAllOpportunitiesCleaningLady(user, page * limit, limit));
			map.put("length",	business.countMyOportunity(cpf, Work.VIEW_PRESTAR_SERVICO));
			
			return 	map;		
		}
		catch (Exception e)
		{	
			e.printStackTrace();
			return null;
		}
	}	
	
	
	@PostMapping("/cancel_cleaning_lady")
	public ResponseEntity<Object> cancelCleaningLady(@RequestBody Map<String, Object> postObject, HttpServletRequest request)
	{
		try
		{				
			String token = request.getHeader(ConstantsSecurity.HEADER);					
			Usuario user = userBusiness.findUserByToken(token);
			
			if(user == null) return error("Usuário não encontrado na base de dados!", BasicRestServe.BAD_REQUEST);			
								
			Integer work = postObject.get("work") != null? (Integer) postObject.get("work") : null;
			String  cpf = postObject.get("cpf") != null?  (String) postObject.get("cpf") :    null;
	
			if(work == null) return error("Não foi passado nenhuma oportunidade", BasicRestServe.BAD_REQUEST);
			if(cpf == null)  return error("Não foi passado o usuário", BasicRestServe.BAD_REQUEST);
						
			Usuario userCPF = userBusiness.findByCPF(cpf);
			if(userCPF == null) return error("Usuário não encontrado na base de dados!", BasicRestServe.BAD_REQUEST);
		
			if(!userCPF.getCpf().equals(user.getCpf())) return error("O usuário informado é diferente do que realizou o login, refaça o login!", BasicRestServe.BAD_REQUEST);	
			
			String cancell = business.cancellCleaningLady(user, Long.valueOf(work));
						
			if(cancell == null) return ok("OK");	
			else 				return error(cancell, BasicRestServe.INTERNAL_ERROR);
			
		}
		catch (Exception e)
		{	
			return error("Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.BAD_REQUEST);
		}
	}	
	
	@PostMapping("/cancel_publication")
	public ResponseEntity<Object> cancelPublication(@RequestBody Map<String, Object> postObject, HttpServletRequest request)
	{
		try
		{				
			String token = request.getHeader(ConstantsSecurity.HEADER);					
			Usuario user = userBusiness.findUserByToken(token);
			
			if(user == null) return error("Usuário não encontrado na base de dados!", BasicRestServe.BAD_REQUEST);			
								
			Integer work = postObject.get("work") != null? (Integer) postObject.get("work") : null;
			String  cpf = postObject.get("cpf") != null?  (String) postObject.get("cpf") :    null;
	
			if(work == null) return error("Não foi passado nenhuma oportunidade", BasicRestServe.BAD_REQUEST);
			if(cpf == null)  return error("Não foi passado o usuário", BasicRestServe.BAD_REQUEST);
						
			Usuario userCPF = userBusiness.findByCPF(cpf);
			if(userCPF == null) return error("Usuário não encontrado na base de dados!", BasicRestServe.BAD_REQUEST);
		
			if(!userCPF.getCpf().equals(user.getCpf())) return error("O usuário informado é diferente do que realizou o login, refaça o login!", BasicRestServe.BAD_REQUEST);	
			
			String cancell = business.cancellWork(user, Long.valueOf(work));
						
			if(cancell == null) return ok("OK");	
			else 				return error(cancell, BasicRestServe.INTERNAL_ERROR);
			
		}
		catch (Exception e)
		{	
			return error("Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]", BasicRestServe.BAD_REQUEST);
		}
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
