package br.com.diarista.folks.dto;

import java.util.Date;

public class AssessmentDTO 
{
	private Long id; 
	private InfoUserDTO evaluator;	
	private Date date;
	private String description;
	private Integer note;
	
	public Long getId()
	{
		return id;
	}
	public InfoUserDTO getEvaluator() 
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
	public void setEvaluator(InfoUserDTO evaluator)
	{
		this.evaluator = evaluator;
	}
	public void setDate(Date date) {
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
