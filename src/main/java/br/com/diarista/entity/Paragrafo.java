package br.com.diarista.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paragrafo") 
public class Paragrafo
{
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
}
