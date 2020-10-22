package br.com.diarista.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.diarista.dto.ParagrafoDTO;

@Entity
@Table(name = "paragrafo") 
public class Paragrafo extends BasicEntity<ParagrafoDTO>
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "TEXT")
	private String paragrafo;
	
	private Boolean status;

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getParagrafo()
	{
		return paragrafo;
	}

	public void setParagrafo(String paragrafo) 
	{
		this.paragrafo = paragrafo;
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
	public ParagrafoDTO getDTO() 
	{
		ParagrafoDTO dto = new ParagrafoDTO();		
		dto.setId(id);
		dto.setParagrafo(paragrafo);
		dto.setStatus(status);		
		return dto;
	}
}
