package br.com.diarista.routine.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log_system") 
public class LogSystem implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final short TYPE_ERROR = 1;
	public static final short TYPE_SUCESS = 2;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long 	id; 		
	private String 	description;
	private String 	lineCode;
	private Date 	date;
	private Short 	typeLog;
	
	public Long getId() 
	{
		return id;
	}
	public String getDescription() 
	{
		return description;
	}
	public String getLineCode() 
	{
		return lineCode;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	public void setLineCode(String lineCode) 
	{
		this.lineCode = lineCode;
	}
	public Date getDate()
	{
		return date;
	}
	public short getTypeLog()
	{
		return typeLog;
	}
	public void setDate(Date date) 
	{
		this.date = date;
	}
	public void setTypeLog(short typeLog) 
	{
		this.typeLog = typeLog;
	}	
}
