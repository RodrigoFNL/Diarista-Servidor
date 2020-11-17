package br.com.diarista.geral.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "contrato") 
public class Contrato implements Serializable
{	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Short index;
			
	private String clausula;
	
	@OneToMany
	private List<Paragrafo> paragrafo;
	
	private Boolean status;	

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public String getClausula()
	{
		return clausula;
	}

	public void setClausula(String clausula) {
		this.clausula = clausula;
	}

	public Boolean getStatus()
	{
		return status;
	}

	public void setStatus(Boolean status) 
	{
		this.status = status;
	}

	public List<Paragrafo> getParagrafo() {
		return paragrafo;
	}

	public void setParagrafo(List<Paragrafo> paragrafo) {
		this.paragrafo = paragrafo;
	}

	public Short getIndex() {
		return index;
	}

	public void setIndex(Short index) {
		this.index = index;
	}

}



