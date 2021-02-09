package br.com.diarista.work.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import br.com.diarista.adress.dao.EnderecoDAO;
import br.com.diarista.adress.dao.LocalidadeDAO;
import br.com.diarista.adress.entity.Endereco;
import br.com.diarista.conf.EmailInfo;
import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.business.UsuarioBusiness;
import br.com.diarista.folks.dao.AssessmentDAO;
import br.com.diarista.folks.entity.Assessment;
import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.routine.business.LogSystemBusiness;
import br.com.diarista.routine.business.PaymentSystemBusiness;
import br.com.diarista.routine.business.SendEmailBusiness;
import br.com.diarista.routine.entity.LogSystem;
import br.com.diarista.routine.entity.PaymentSystem;
import br.com.diarista.routine.entity.SendEmail;
import br.com.diarista.utils.DateUtils;
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
	private SendEmailBusiness emailBusiness;	
	
	@Autowired
	private LogSystemBusiness logBusiness;	
	
	@Autowired
	private UsuarioBusiness userBusiness;	
	
	@Autowired
	private PaymentSystemBusiness paymentBusiness;	
	
	@Autowired
	private EnderecoDAO endRepository;	
	
	@Autowired
	private LocalidadeDAO localRepository;	
	
	@Autowired
	private AssessmentDAO assessmentRepository;	
	
	@Override
	public List<?> getAllActive() 
	{		
		return null;
	}
	
	//cadastrada uma publicação
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
			
		Long count = workRepository. countByDateAfterAndStatusAndUsuarioAndStageNot(new Date(), true, user, (short) 4);		
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
		work.setDate(work.getDate());		
		work.setStage(Work.STAGE_OPEN);
		work.setStatus(true);
		work = workRepository.save(work);		
		return "id:" + work.getId().toString() ;
	}

	//busca todas as oportunidades próximo ao endereço de um profissional
	public List<WorkDTO> getAllActive(Usuario user, int page, int limit) 
	{			
		List<Work> works = workRepository.findAllWorkBairroNative(new Date(), user.getCpf(), limit, page * limit);	
		works.addAll(workRepository.findAllWorkCityNative(new Date(), user.getCpf(), limit, page * limit));	
		works.addAll(workRepository.findAllWorkUFNative(new Date(), user.getCpf(), limit, page * limit));				
		if(!(works != null && !works.isEmpty())) return null;		
		List<WorkDTO> dtos = new ArrayList<WorkDTO>();
		
		for (Work work : works) 
		{			
			List<Assessment> assessment = assessmentRepository.findAllByEvaluatorAndStatusAndDateAfterOrderByDateDesc(work.getUsuario(), true, DateUtils.getRemoveDaysInDate(new Date(), Assessment.BEFOR_DAY));			
			dtos.add(new WorkDTO(work, assessment));
		}
		
		return dtos;
	}

	//Profisional insere usa candidatura em uma publicação de um cliente
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
		if(work.getStage() == Work.STAGE_OPEN) work.setStage(Work.STAGE_EVALUATION);
		
		workRepository.save(work);	
		SendEmail email = new SendEmail();		
		email.setBody(("Olá " + work.getUsuario().getNickname() +  "\n Você obteve um(a) candidato(a) a sua publicação de número " + work.getId()).getBytes());
		email.setDateRegister(new Date());
		email.setRecipient(work.getUsuario().getEmail());
		email.setSubject("Candidatura em sua Publicação");		
		email.setUser(user);
		emailBusiness.save(email);		
		
		return null;	
	}

	//conta todas as oportunidades disponveis próximo ao endereço do profissional
	public List<Long> countAllActive(Usuario user)
	{			
		List<Long> totals = new ArrayList<Long>();		
		totals.add(workRepository.countAllWorkBairroNative(new Date(), user.getCpf()));	
		totals.add(workRepository.countAllWorkCityNative(new Date(), user.getCpf()));	
		totals.add(workRepository.countAllWorkUFNative(new Date(), user.getCpf()));		
		return totals;
	}

	//conta contas oportunidades o cliente/profissional possue 
	public Long countMyOportunity(String cpf, Integer view)	
	{	
		if(view == Work.VIEW_PRESTAR_SERVICO) 	return workRepository.countMyOportunity(cpf, DateUtils.getRemoveDaysInDate(new Date(), Assessment.BEFOR_DAY));
		else if(view == Work.VIEW_CONTRATAR) 	return workRepository.countMyPublicacoes(DateUtils.getRemoveDaysInDate(new Date(), Assessment.BEFOR_DAY), cpf);
		
		else return 0L;
	}

	//busca todas as oportunidades próximo a sua localidade
	public List<WorkDTO> getAllOpportunitiesCleaningLady(Usuario user, Integer page, Integer limit )
	{
		List<Work> list = workRepository.getAllOpportunitiesCleaningLady(DateUtils.getRemoveDaysInDate(new Date(), Assessment.BEFOR_DAY), user.getCpf(), limit, page);	
		List<WorkDTO> dtos = new ArrayList<WorkDTO>();
		 
		for (Work work : list) 
		{	
			dtos.add(new WorkDTO(work, assessmentRepository.findAllByEvaluatorAndStatusAndDateAfterOrderByDateDesc(user, true, DateUtils.getRemoveDaysInDate(new Date(), Assessment.BEFOR_DAY))));
		}		
		return dtos;
	}

	//Proffisional cancela sua candidatura a uma publicação
	public String cancellCleaningLady(Usuario user, Long idWork) 
	{
		try
		{
			if(user.getRegistrationSituation() != Usuario.CADASTRO_APROVADO) 	return "Situação do Usuário irregular, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]";				
			Optional<Work> opWork =  workRepository.findById(idWork);
			
			if(!(opWork != null && !opWork.isEmpty())) return "Oportunidade não encontrada";			
			Work work = opWork.get();
			
			if(!work.getStatus()) return "Esta oportunidade foi excluída";			
			if(work.getStage() == 5) return "Esta oportunidade já foi encerrada, não pode ser mais cancelado";					
			if(!work.getUsuario().getRegistrationSituation().equals(Usuario.CADASTRO_APROVADO))	return "Este Cliente não está mais com o cadastro aprovado, tente outra solicitação de serviços";
			
			
			boolean isContains = false;
			
			for(Usuario cleaningLady : work.getCleaningLadies())
			{
				if(cleaningLady.getCpf().equals(user.getCpf()))  
				{
					isContains = true;
					work.getCleaningLadies().remove(cleaningLady);					
					if(work.getCleaningLadies().isEmpty()) work.setStage(Work.STAGE_OPEN);					
					workRepository.save(work);
					
					workRepository.save(work);	
					SendEmail email = new SendEmail();		
					email.setBody(("Olá " + work.getUsuario().getNickname() +  "\n O(A) Candidato(a) " + cleaningLady.getNickname() + " Cancelou sua Candidatura da sua publicação " + work.getId()).getBytes());
					email.setDateRegister(new Date());
					email.setRecipient(work.getUsuario().getEmail());
					email.setSubject("Candidatura Cancelada em Publicação");		
					emailBusiness.save(email);	
					email.setUser(user);
					
					break;
				}
			}		
			
			work.setStage(Work.STAGE_PAY_OUT);
			if(isContains && work.getStage().equals(Work.STAGE_PAY_OUT))
			{
				try 
				{
					new Thread()
					{
						@Override
						public void run() 
						{
							Assessment assessment = new Assessment();
							assessment.setDate(new Date());
							assessment.setDescription("Cancelou uma faxina que já estava paga e confirmada");
							assessment.setNote(3);
							assessment.setStatus(true);
							assessment.setRated(user);
							assessment.setEvaluator(user);							
							assessmentRepository.save(assessment);
						}
					}.start();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}				
			return isContains ? null : "Usuário não está contido na lista de Faxineira desta oportunidade";
		}
		catch (Exception e)
		{
			return 	"Ocorreu um erro de internto, se o erro persistir, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]";
		}
	}
	
	//busca todas as publicações de um usuário
	public List<WorkDTO> getAllMyPuclications(Usuario user, Integer page, Integer limit) 
	{
	
		List<Work> list = workRepository.getAllMyPuclications(DateUtils.getRemoveDaysInDate(new Date(), Assessment.BEFOR_DAY), user.getCpf(), limit, page);	
		List<WorkDTO> dtos = new ArrayList<WorkDTO>();
		 
		for (Work work : list) 
		{	
			Map<String, Object> workMap = new HashMap<String, Object>();			
			workMap.put("work", work);				
			Map<String, List<Assessment>> evaluation = new HashMap<String, List<Assessment>>();		
			for(Usuario cleaningLady :  work.getCleaningLadies())
			{
				List<Assessment> assessments = assessmentRepository.findAllByEvaluatorAndStatusAndDateAfterOrderByDateDesc(cleaningLady, true, DateUtils.getRemoveDaysInDate(new Date(), Assessment.BEFOR_DAY));				
				evaluation.put(cleaningLady.getCpf(), assessments);				
			}									
			workMap.put("assessment", evaluation);			
			if(work.getStage() == Work.STAGE_EVALUATION) dtos.add(0, new WorkDTO(workMap));
			else dtos.add(new WorkDTO(workMap));
		}		
		return dtos;
	}

	//Cancela uma publicação de um cliente
	public String cancellWork(Usuario user, Long idWork) 
	{		
		try 
		{
			if(user.getRegistrationSituation() != Usuario.CADASTRO_APROVADO) 	return "Situação do Usuário irregular, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]";				
			Optional<Work> opWork =  workRepository.findById(idWork);
			
			if(!(opWork != null && !opWork.isEmpty())) return "Oportunidade não encontrada";			
			Work work = opWork.get();
			
			if(!work.getStatus()) return "Esta oportunidade foi excluída";			
			if(work.getStage() == 5) return "Esta oportunidade já foi encerrada, não pode ser mais cancelado";								
			
			work.setStage(Work.STAGE_PAY_OUT);
			if(work.getCleaningLadies().size() > 0 && work.getDate().after(new Date()) && work.getIsConfirmed())
			{
				new Thread()
				{
					@Override
					public void run() 
					{
						Assessment assessment = new Assessment();
						assessment.setDate(new Date());
						assessment.setDescription("Cancelou com uma faxina que já estava confirmada o serviço");
						assessment.setNote(3);
						assessment.setStatus(true);
						assessment.setRated(user);
						assessment.setEvaluator(user);							
						assessmentRepository.save(assessment);
					}
				}.start();
			} 
			
			work.setStage(Work.STAGE_CANCELED);
			work.setStatus(false);	
			workRepository.save(work);
			return null;
		}					
		catch (Exception e) 
		{
			e.printStackTrace();
			return 	"Ocorreu um erro de internto, se o erro persistir, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]";	
		}
	}

	//Salva as informações de pagamento no banco de dados para que  a rotina de pagamento realize a transação
	@Transactional
	public String schedule(Usuario user, Map<String, String> map) 
	{		
		try
		{			
			if(user.getRegistrationSituation() != Usuario.CADASTRO_APROVADO) return "Usuário com situação irregular, entre em contato com [" + EmailInfo.EMAIL_ADMINISTRADOR + "]";				
			if (map.get("codSeg") == null) 			return "O Código de Segurança é obrigatório";
			if (map.get("formPayment") == null) 	return "O Código de Segurança é obrigatório";
			if (map.get("nameCad") == null) 		return "O Código de Segurança é obrigatório";
			if (map.get("numCard") == null) 		return "O Código de Segurança é obrigatório";
			if (map.get("shelfLifeCard") == null) 	return "O Código de Segurança é obrigatório";
			
			Long 	idWork 	= map.get("idWork") != null? 			Long.valueOf((String) map.get("idWork")) : null;
			String  cpf 	= map.get("cpfCleaningLady") != null? 	(String) map.get("cpfCleaningLady"): null;		
			
			Optional<Work> workOptional	=  workRepository.findById(idWork);			
			Work work = workOptional != null && !workOptional.isEmpty() ? workOptional.get() : null;
			
			if(work == null) 						return "Não foi encontrado a publicação informada"; 
			if(work.getDate().before(new Date())) 	return "A Publicação está expirada!, o pagamento deverá ser feito com antecedência de no mínimo 24 horas"; 
			
			PaymentSystem hasPayment = paymentBusiness.getPaymentSystemByWork(work);
			
			if(hasPayment != null 		&& (hasPayment.getStatus() == PaymentSystem.STATUS_SUCESS || hasPayment.getStatus() == PaymentSystem.STATUS_APPROVED)) return "A Publicação já foi paga"; 
			else if(hasPayment != null 	&& hasPayment.getStatus() != PaymentSystem.STATUS_FAIL)  return "A Publicação já está na lista para pagamento"; 
				
			Usuario cleaningLady =  userBusiness.findByCPF(cpf);			
			if(cleaningLady == null) return "Não foi encontrado o(a) profissional escolhida!"; 
			if(cleaningLady.getRegistrationSituation() != Usuario.CADASTRO_APROVADO) return "Profissional com situação irregular, escolha outro";		
						
			work.setCleaningLady(cleaningLady);
			work.setCleaningLadies(null);
			workRepository.save(work);
			
			PaymentSystem payment = new PaymentSystem();	
			Gson gson = new Gson();
		
			payment.setDateRegister(new Date());
			payment.setStatus(PaymentSystem.STATUS_OPEN);			
			payment.setObject(gson.toJson(map).getBytes());			
			payment.setUser(user);	
			payment.setWork(work);	
			payment.setCleanLady(cleaningLady);
			paymentBusiness.save(payment);	
			
			SendEmail email = new SendEmail();	
			
			email.setDateRegister(new Date());
			email.setRecipient(user.getEmail());
			email.setStatus(SendEmail.STATUS_OPEN);
			email.setSubject("Pagamento em Processo!");
			email.setBody(("Olá " + user.getNickname() +  "\n Foi agendado o pagamento referente ao serviço de nº " + work.getId() + "\n Assim que for confirmado, será liberado o endereço ao profissional, para realização do serviço ").getBytes());	
			emailBusiness.save(email);
			
			SendEmail emailCleaningLady = new SendEmail();	
			
			emailCleaningLady.setDateRegister(new Date());
			emailCleaningLady.setRecipient(cleaningLady.getEmail());
			emailCleaningLady.setStatus(SendEmail.STATUS_OPEN);
			emailCleaningLady.setSubject("Informativo Oportunidade Nº " + work.getId());
			emailCleaningLady.setBody(("Olá " + cleaningLady.getNickname() +  "\n Você foi escolhido(a) para realizar o serviço de nº " + work.getId() + "\n Assim que o cliente efetuar o pagamento, será liberado o endereço do serviço, acompanhei a situação no APP").getBytes());	
			emailBusiness.save(emailCleaningLady);
			
			LogSystem log = new LogSystem();
			log.setDate(new Date());
			log.setDescription("Agendado para pagamento PaySistem id " + payment.getId() + "] CPF Pagante: " + user.getCpf() + " Work id: [" + work.getId() + "] Profissional cpf [" + cleaningLady.getCpf() + "]" );
			log.setLineCode("WorkBusiness, linha 425");			
			log.setTypeLog(LogSystem.TYPE_SUCESS);			
			logBusiness.save(log);
			
			
			return null;
		}
		catch (Exception e)
		{
			LogSystem log = new LogSystem();
			log.setDate(new Date());
			log.setDescription("Error ao tentar agendar pagamento [Id Work: " + map.get("idWork") + "] CPF USUÁRIO: " + user.getCpf());
			log.setLineCode("WorkBusiness, linha 419 a 430");			
			log.setTypeLog(LogSystem.TYPE_ERROR);			
			logBusiness.save(log);
			
			e.printStackTrace();
			return "Ocorreu um erro de comunicação, envie um email para [" + EmailInfo.EMAIL_ADMINISTRADOR + "]";
		}	
	}

	public void save(Work work)
	{
		workRepository.save(work);		
	}

}





















