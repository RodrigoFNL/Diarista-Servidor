package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "usuario") 
public class Usuario implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cpf")
	private String cpf;
	
	@Column(nullable = false)
	private String name;
	
	private String login;	
	
	@Column(nullable = false)
	private String email;	
	private String coupon;

	@Column(name = "termos_condicoes", columnDefinition = "BOOL DEFAULT TRUE", nullable = false)
	private Boolean termosCondicoes;	
			
	
	public Boolean getTermosCondicoes() {
		return termosCondicoes;
	}
	public void setTermosCondicoes(Boolean termosCondicoes) {
		this.termosCondicoes = termosCondicoes;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	private byte [] password; 
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
