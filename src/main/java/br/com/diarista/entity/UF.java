package br.com.diarista.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.diarista.dto.UFDTO;
import br.com.diarista.utils.StringUtils;


@Entity
@Table(name = "uf") 
public class UF extends BasicEntity<UFDTO>
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
    private String sigla; 
    private String name; 
    private Boolean status;
    
    public UF() 
	{}
    
	public UF(String id) 
	{		
		this.id = StringUtils.isNotNull(id)?  Long.valueOf(id) : null;
	}
	public Long getId() 
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getSigla()
	{
		return sigla;
	}
	public void setSigla(String sigla)
	{
		this.sigla = sigla;
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
	public UFDTO getDTO() 
	{
		UFDTO dto = new UFDTO();		
		dto.setId(this.id);
		dto.setName(this.name);
		dto.setSigla(this.sigla);
		dto.setStatus(this.status);		
		return dto;
	}
}
