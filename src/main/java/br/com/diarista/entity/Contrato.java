package br.com.diarista.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.diarista.dto.ContratoDTO;


@Entity
@Table(name = "contrato") 
public class Contrato extends BasicEntity<ContratoDTO>
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "TEXT")
	private String artigo;
	private Boolean status;
	
	public Long getId() 
	{
		return id;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	public String getArtigo() 
	{
		return artigo;
	}
	public void setArtigo(String artigo) 
	{
		this.artigo = artigo;
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
	public ContratoDTO getDTO()
	{
		ContratoDTO dto = new ContratoDTO();
		dto.setId(this.id);
		dto.setArtigo(this.artigo);
		dto.setStatus(this.status);		
		return dto;
	}
}



