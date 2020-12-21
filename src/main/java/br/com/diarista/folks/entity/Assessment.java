package br.com.diarista.folks.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "assessment") 
public class Assessment implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 		
	@OneToOne
	private Usuario evaluator;
	
	private Date date;
	private String description;
	private Integer note;
	
	public Long getId() {
		return id;
	}
	public Usuario getEvaluator() 
	{
		return evaluator;
	}
	public Date getDate()
	{
		return date;
	}
	public String getDescription()
	{
		return description;
	}
	public Integer getNote() 
	{
		return note;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	public void setEvaluator(Usuario evaluator) 
	{
		this.evaluator = evaluator;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	public void setNote(Integer note)
	{
		this.note = note;
	}
}
