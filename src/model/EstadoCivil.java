package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import dto.EstadoCivilDTO;


@Entity
@Table(name = "estado_civil") 
public class EstadoCivil extends BasicEntity<EstadoCivilDTO>
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	private String name;
	private Boolean status;
	
	public Long getId() 
	{
		return id;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Boolean getStatus()
	{
		return status;
	}
	public void setStatus(Boolean status) 
	{
		this.status = status;
	}
	
	@Override
	public EstadoCivilDTO getDTO() 
	{
		EstadoCivilDTO dto = new EstadoCivilDTO();
		
		dto.setId(this.id);
		dto.setName(this.name);
		dto.setStatus(this.status);		
		
		return dto;
	}

}










