package br.com.diarista.work.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.adress.dao.EnderecoDAO;
import br.com.diarista.adress.dao.LocalidadeDAO;
import br.com.diarista.adress.entity.Endereco;
import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.utils.DiaristaUtils;
import br.com.diarista.work.dao.WorkDAO;
import br.com.diarista.work.dto.WorkDTO;
import br.com.diarista.work.entity.TaskAmmount;
import br.com.diarista.work.entity.Work;

@Service
public class WorkBusiness extends BasicBusiness<Work> 
{
	@Autowired
	private WorkDAO workRepository;	
	
	@Autowired
	private EnderecoDAO endRepository;	
	
	@Autowired
	private LocalidadeDAO localRepository;	
	
	@Override
	public List<?> getAllActive() 
	{		
		return null;
	}
	
	@Override
	public String register(Work work, Usuario user) 
	{	
		if(work.getUsuario() == null) 			return "Não foi informado o usuário que está realizando a publicação";		
		if(!(work.getUsuario().getCpf().equals(user.getCpf()))) 	return "O usuário informado é diferente do usuário que está logado no sistema";	
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
			
		Long count = workRepository.countByDateAfterAndStatusAndUsuario(new Date(), true, user);		
		if(count >= user.getAmmountRegister()) return "Você excedeu o limite máximo de publicações ativas!";			
		
		Optional<Endereco> end = endRepository.findByLocalityAndNumber(work.getAdress().getLocality(), work.getAdress().getNumber());
		if(!(end != null && !end.isEmpty()))
		{
			Boolean isExist = localRepository.existsByCep(work.getAdress().getLocality().getCep());
			if(!isExist)
			{
				localRepository.save(work.getAdress().getLocality());
			}		
			
			work.getAdress().setId(null);
			endRepository.save(work.getAdress());
		}
		else work.setAdress(end.get());	
		
		work.setDate(DiaristaUtils.addHour(work.getDate(), 3));
		
		work.setStatus(true);
		work = workRepository.save(work);		
		return "id:" + work.getId().toString() ;
	}

	public List<WorkDTO> getAllActive(Usuario user, int page, int limit) 
	{			
		List<Work> works = workRepository.findAllWorkBairroNative(new Date(), user.getCpf(), limit, page * limit);	
		works.addAll(workRepository.findAllWorkCityNative(new Date(), user.getCpf(), limit, page * limit));	
		works.addAll(workRepository.findAllWorkUFNative(new Date(), user.getCpf(), limit, page * limit));				
		if(!(works != null && !works.isEmpty())) return null;		
		List<WorkDTO> dtos = new ArrayList<WorkDTO>();
		
		for (Work work : works) 
		{
			dtos.add(new WorkDTO(work));
		}
		
		return dtos;
	}

	public String insertCleaningLayd(Work workRequest, Usuario user)
	{		
		if(workRequest.getUsuario() == null) 							return "Não foi informado o usuário que está realizando a publicação";		
		if(!(workRequest.getUsuario().getCpf().equals(user.getCpf()))) 	return "O usuário informado é diferente do usuário que está logado no sistema";	
		
		Optional<Work> optWork = workRepository.findById(workRequest.getId());		
		if(!(optWork != null && !optWork.isEmpty()))					return "Oportunidade não foi encontrado nos registros";	
		
		Work work = optWork.get();		
		
		if(work.getCleaningLadies() != null && work.getCleaningLadies().size() >= work.getUsuario().getAmmountRegister()) 	return "Desculpe, Esta solicitação já recebeu o número máximo de faxineiras, procure outra solicitação";	
		if(!work.getUsuario().getRegistrationSituation().equals(Usuario.CADASTRO_APROVADO))									return "Este Cliente não está mais com o cadastro aprovado, tente outra solicitação de serviços";	
				
		//if(work.getUsuario().getCpf().equals(workRequest.getUsuario().getCpf())) 	return "O usuário não pode ser o mesmo que fez a solicitação do serviço";	
		
		Long countWorks = workRepository.countAllWorksByCleaningLadies(new Date(), user.getCpf());		
		if(countWorks > user.getAmmountRegister()) return "Você atingiu o número máximo de candidaturas ativas permitida, acompanhe o processo na aba MINHAS OPORTUNIDADES";	
			
		if(work.getCleaningLadies() == null) work.setCleaningLadies(new ArrayList<Usuario>());		
		boolean isExist = false;		
		
		for (Usuario cleaningLady : work.getCleaningLadies()) 
		{
			if(cleaningLady.getCpf().equals(workRequest.getUsuario().getCpf()))
			{
				isExist = true;
				break;
			}
		}		
		
		if(isExist) return "O usuário já está cadastrado neste solicitação, acompanhei na tela de Minhas Oportunidades";
		
		work.getCleaningLadies().add(workRequest.getUsuario());
		workRepository.save(work);
		return null;	
	}
}
























