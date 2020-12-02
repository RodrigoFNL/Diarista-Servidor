package br.com.diarista.work.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.work.dao.TaskDAO;
import br.com.diarista.work.entity.Task;

@Service
public class TaskBusiness extends BasicBusiness<Task>
{
	@Autowired
	private TaskDAO taskRepository;
	
	@Override
	public List<?> getAllActive() 
	{
		return null;
	}

	public List<Task> getAllActive(Short type) 
	{		
		List<Task> tasks = taskRepository.findBytypeAndStatus(type, true);				
		return tasks;
	}

	@Override
	public String register(Task object, Usuario user)
	{	
		return null;
	}
}
