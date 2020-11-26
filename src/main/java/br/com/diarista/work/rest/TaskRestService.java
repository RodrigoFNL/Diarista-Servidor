package br.com.diarista.work.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.rest.BasicRestServe;
import br.com.diarista.work.business.TaskBusiness;
import br.com.diarista.work.entity.Task;

@RestController
@RequestMapping("/rest/task")
public class TaskRestService  extends BasicRestServe<Task>
{
	@Autowired
	private TaskBusiness business;	
	
	@Override
	protected BasicBusiness<Task> business() 
	{
		return business;
	}
	
	
	@PostMapping("/get_all_tasks")
	public List<Task> getAllTasks(@RequestBody Map<String, Object> postObject)
	{
		try
		{	
			Short type = postObject.containsKey("type")? ((Integer)postObject.get("type")).shortValue() : null;			
			if(type == null) return null;			
			return business.getAllActive(type);
		}
		catch (Exception e)
		{	
			e.printStackTrace();
			return null;
		}
	}	
}
