package br.com.diarista.work.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.adress.dao.EnderecoDAO;
import br.com.diarista.adress.entity.Endereco;
import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.work.dao.WorkDAO;
import br.com.diarista.work.entity.TaskAmmount;
import br.com.diarista.work.entity.Work;

@Service
public class WorkBusiness extends BasicBusiness<Work> 
{
	@Autowired
	private WorkDAO workRepository;	
	
	@Autowired
	private EnderecoDAO endRepository;	
	
	@Override
	public List<?> getAllActive() 
	{		
		return null;
	}
	
	@Override
	public String register(Work work, Usuario user) 
	{	
		if(work.getUsuario() == null) 			return "Não foi informado o usuário que está realizando a publicação";		
		if(work.getDate() == null)  			return "A data é obrigatória";
		if(work.getDate().before(new Date())) 	return "A data deve ser maior que a data atual";
		
		if(!(work.getTasks() != null && !work.getTasks().isEmpty())) 	return "Você deverá informar ao menos uma tarefa";
		
		List<TaskAmmount> tasks = new ArrayList<TaskAmmount>();
		for (TaskAmmount task : work.getTasks()) 
		{
			if(task.getAmmount() > 0) tasks.add(task);			
		}
		work.setTasks(tasks);
		if(!(work.getTasks() != null && !work.getTasks().isEmpty())) 	return "Você deve quantificar ao menos uma tarefa";		
		
		
		if(work.getAdress() == null)	 								return "Você deve informar o endereço";	
		if(work.getAdress().getLocality() == null ) 					return "Não foi informado o endereço";			
		if(work.getAdress().getLocality().getCep() == null ) 			return "Não foi informado o CEP do endereço";	
		if(work.getAdress().getLocality().getLocalidade() == null ) 	return "Não foi informado o Logradouro do endereço (Rua, Av, Serv)";	
		if(work.getAdress().getLocality().getUf() == null )				return "Não foi informado a UF do Endereço";			
		if(work.getAdress().getNumber() == null ) 						return "Não foi informado o número do endereço";	
			
		Long count = workRepository.countByDateAfterAndStatus(new Date(), true);		
		if(count >= user.getAmmountRegister()) return "Você excedeu o limite máximo de publicações ativas!";			
		
		Optional<Endereco> end = endRepository.findByLocalityAndNumber(work.getAdress().getLocality(), work.getAdress().getNumber());
		if(!(end != null && !end.isEmpty()))
		{
			work.getAdress().setId(null);
			endRepository.save(work.getAdress());
		}
		else work.setAdress(end.get());	
		
		work.setStatus(true);
		work = workRepository.save(work);		
		return "id:" + work.getId().toString() ;
	}

}
