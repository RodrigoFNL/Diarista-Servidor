package br.com.diarista.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.diarista.dto.OrgaoEmissorDTO;


@Entity
@Table(name = "orgao_emissor") 
public class OrgaoEmissor extends BasicEntity<OrgaoEmissorDTO>
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	public String getName() {
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
	public OrgaoEmissorDTO getDTO() 
	{
		OrgaoEmissorDTO dto = new OrgaoEmissorDTO();
		
		dto.setId(this.id);
		dto.setName(this.name);
		dto.setStatus(this.status);
		
		return dto;
	}    
}
