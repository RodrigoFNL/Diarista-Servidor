package br.com.diarista.bank.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.diarista.folks.entity.Usuario;

@Entity
@Table(name = "extrato") 
public class Extrato 
{
	public static final short TYPE_DEPOSITO = 1;
	public static final short TYPE_SAQUE = 2;
	public static final short TYPE_JUIZADO = 3;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "date_register")
	private Date dateRegister;
	
	private Double value;
	private Short type;
	private String origin;
	private String document;
	private Boolean blocked;
	
	@ManyToOne
	@JoinColumn(name = "user_cpf", referencedColumnName = "cpf")
	private Usuario user;
	
	public Long getId() {
		return id;
	}
	public Double getValue() {
		return value;
	}
	public Short getType() {
		return type;
	}
	public Usuario getUser() {
		return user;
	}
	public String getOrigin() {
		return origin;
	}
	public String getDocument() {
		return document;
	}
	public Date getDateRegister() {
		return dateRegister;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public void setDateRegister(Date dateRegister) {
		this.dateRegister = dateRegister;
	}
	public Boolean getBlocked() {
		return blocked;
	}
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
}
