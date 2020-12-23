package br.com.diarista.folks.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.diarista.folks.dto.AssessmentDTO;

@Entity
@Table(name = "assessment") 
public class Assessment implements Serializable
{	
	private static final long serialVersionUID = 1L;
	public static final int BEFOR_DAY = 60;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 	
	
	@OneToOne
	@JoinColumn(name = "evaluator_cpf")
	private Usuario evaluator;	
	
	@OneToOne
	@JoinColumn(name = "rated_cpf")
	private Usuario rated;
	
	private Date date;
	private String description;
	private Integer note;
	
	@Column(columnDefinition = "BOOL DEFAULT TRUE")
	private Boolean status;
	
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
	public Usuario getRated() {
		return rated;
	}
	public void setRated(Usuario rated) {
		this.rated = rated;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public AssessmentDTO getDTO() 
	{
		AssessmentDTO dto = new AssessmentDTO();
		dto.setDate(this.date);
		dto.setId(this.id);
		dto.setNote(this.note);
		dto.setDescription(this.description);
		dto.setEvaluator(this.evaluator.getSimpleDTO());		
		return dto;
	}
}
