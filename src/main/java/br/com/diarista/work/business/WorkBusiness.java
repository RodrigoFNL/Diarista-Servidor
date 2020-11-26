package br.com.diarista.work.business;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.work.entity.Work;

@Service
public class WorkBusiness extends BasicBusiness<Work> 
{
	@Override
	public List<?> getAllActive() 
	{		
		return null;
	}
	
//	
//	@RequestMapping("/find_tasks")
//	public List<Tasks> findTasks(@RequestBody Map<String, Object> postObject)
//	{
//		try
//		{	
//			String cep = (String) postObject.get("cep") != null ? (String) postObject.get("cep") : null;	
//			cep = cep != null? cep.trim(): null;
//			if(cep == null) return null;
//			
//			Localidade localidade = endBusiness.getLocalityfindByCep(cep);			
//			if(localidade != null) return  localidade;
//								
//			URL url = new URL("http://viacep.com.br/ws/" + cep + "/json/");	
//			url.openConnection();
//				
//			InputStream is = url.openStream();				
//			Gson gson = new Gson();
//			
//			String response = new String(is.readAllBytes(), "UTF-8");	
//			LocalidadeDTO dto = gson.fromJson(response, LocalidadeDTO.class);
//			
//			Localidade entity = endBusiness.getEntity(dto);	
//			
//			return entity != null && entity.getCep() != null? entity : null ;
//		}
//		catch (Exception e)
//		{	
//			e.printStackTrace();
//			return null;
//		}	
//	}	
}
