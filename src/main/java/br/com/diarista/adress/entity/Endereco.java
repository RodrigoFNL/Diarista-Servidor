package br.com.diarista.adress.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.diarista.utils.StringUtils;

@Entity
@Table(name="andress")
public class Endereco implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "locality_id", referencedColumnName = "cep")
	private Localidade locality;
	private String complement; 
	private String number; 	
	
	
	public Endereco() {}	
	public Endereco(String id, String number, String complement, String cep, String localidade, String logradouro, String bairro, String uf) 
	{
		if(StringUtils.isNotNull(id)) 		this.id = Long.valueOf(id);
		if(StringUtils.isNotNull(number)) 	this.number = number;
		if(StringUtils.isNotNull(complement))this.complement = complement;		
		if(StringUtils.isNotNull(cep)) locality = new Localidade(cep, localidade, logradouro, bairro, uf);
	}	
	public Endereco(Endereco andress) 
	{
		Localidade local = new Localidade();
		local.setBairro(andress.getLocality().getBairro());
		local.setLocalidade(andress.getLocality().getLocalidade());
		local.setUf(andress.getLocality().getUf());
		this.locality = local;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Localidade getLocality() {
		return locality;
	}
	public void setLocality(Localidade locality) {
		this.locality = locality;
	}
	public String getComplement() {
		return complement;
	}
	public void setComplement(String complement) {
		this.complement = complement;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

}
