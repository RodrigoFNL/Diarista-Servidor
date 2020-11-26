package br.com.diarista.work.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "task") 
public class Task implements Serializable
{
	private static final long serialVersionUID = 1L;	
	public static final Short TYPE_RESIDENCIAL = 1;
	public static final Short TYPE_COMERCIAL = 2;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
    private String description;
    private String detail;
    private Double value;    
    private Short type;
    
    @Column(columnDefinition = "BOOL DEFAULT TRUE")
    private Boolean status;
    
    @Transient
    private Integer ammount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getAmmount() {
		return ammount;
	}

	public void setAmmount(Integer ammount) {
		this.ammount = ammount;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}    
}
