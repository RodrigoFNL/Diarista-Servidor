package br.com.diarista.work.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.diarista.adress.entity.Endereco;
import br.com.diarista.folks.dto.InfoUserDTO;
import br.com.diarista.folks.dto.UsuarioDTO;
import br.com.diarista.folks.entity.Assessment;
import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.work.entity.TaskAmmount;
import br.com.diarista.work.entity.Work;

public class WorkDTO
{
	private Long id;
    private UsuarioDTO usuario;
    private InfoUserDTO contractor;
    private Short establishment_type;
    private Date date;
    private Endereco adress;
    private List<TaskAmmount> tasks;
    private Short stage;
    private Boolean toEvaluate;    
    private InfoUserDTO cleaningLady;
    private List<InfoUserDTO> cleaningLadies;  
    
    
	public WorkDTO(){}
	
	//constroi um work para oportunidade, com a contratante e suas avaliações
	public WorkDTO(Work work, List<Assessment> assessment) 
	{				
		this.id 	= work.getId();
		this.date	= work.getDate();
		this.adress = work.getAdress();
		this.tasks 	= work.getTasks();	
		this.stage  = work.getStage();
		this.contractor	= work.getUsuario().getSimpleDTO(assessment);
		this.establishment_type = work.getEstablishment_type();	
		this.toEvaluate = work.getStage() > Work.STAGE_PAY_OUT && work.getStage() != Work.STAGE_CANCELED;		
	}


	//construi um work para uma publicação, com as faxineiras e suas avaiações
	@SuppressWarnings("unchecked")
	public WorkDTO(Map<String, Object> workMap) 
	{				
		Work work = workMap.containsKey("work")? (Work) workMap.get("work") : null;		
		if(work == null) return;		
		Map<String, List<Assessment>>  assessments = workMap.containsKey("assessment")? (Map<String, List<Assessment>>) workMap.get("assessment") : null;
	
		this.id 	= work.getId();
		this.date	= work.getDate();
		this.adress = work.getAdress();
		this.tasks 	= work.getTasks();	
		this.stage  = work.getStage();

		this.establishment_type = work.getEstablishment_type();	
		this.toEvaluate = work.getStage() > Work.STAGE_PAY_OUT && work.getStage() != Work.STAGE_CANCELED;		
		
		this.cleaningLadies = new ArrayList<InfoUserDTO>();		
		for(Usuario user : work.getCleaningLadies())
		{
			this.cleaningLadies.add(user.getSimpleDTO(assessments.containsKey(user.getCpf())  ? assessments.get(user.getCpf()) : null));
		}	
	}	
	
	//Métodos sets e Gets

	public Long getId() 
	{
		return id;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	public Short getEstablishment_type()
	{
		return establishment_type;
	}
	public void setEstablishment_type(Short establishment_type) 
	{
		this.establishment_type = establishment_type;
	}
	public Date getDate() 
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public Endereco getAdress() 
	{
		return adress;
	}
	public void setAdress(Endereco adress) 
	{
		this.adress = adress;
	}
	public UsuarioDTO getUsuario() 
	{
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario)
	{
		this.usuario = usuario;
	}
	public List<TaskAmmount> getTasks() 
	{
		return tasks;
	}
	public void setTasks(List<TaskAmmount> tasks) 
	{
		this.tasks = tasks;
	}
	public InfoUserDTO getContractor()
	{
		return contractor;
	}
	public void setContractor(InfoUserDTO contractor) 
	{
		this.contractor = contractor;
	}

	public Short getStage() 
	{
		return stage;
	}

	public void setStage(Short stage)
	{
		this.stage = stage;
	}

	public Boolean getToEvaluate() 
	{
		return toEvaluate;
	}

	public void setToEvaluate(Boolean toEvaluate)
	{
		this.toEvaluate = toEvaluate;
	}

	public InfoUserDTO getCleaningLady() {
		return cleaningLady;
	}

	public List<InfoUserDTO> getCleaningLadies() {
		return cleaningLadies;
	}

	public void setCleaningLady(InfoUserDTO cleaningLady) {
		this.cleaningLady = cleaningLady;
	}

	public void setCleaningLadies(List<InfoUserDTO> cleaningLadies) {
		this.cleaningLadies = cleaningLadies;
	}
}
