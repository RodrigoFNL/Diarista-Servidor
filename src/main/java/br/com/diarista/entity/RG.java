package br.com.diarista.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import br.com.diarista.utils.StringUtils;

@Entity
@Table(name = "rg") 
public class RG  implements Serializable
{	
	private static final long serialVersionUID = 1L;	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 	
	
	private String number; 
		
	@JoinColumn(name = "issuer_id", referencedColumnName = "id")
	private OrgaoEmissor issuer;
	
	@JoinColumn(name = "uf_id", referencedColumnName = "id")
	private UF uf;

	public RG() {}
	
	public RG(String rgNumber, String rgIssuer, String rgUF) 
	{		
		this.number = StringUtils.isNotNull(rgNumber)	? StringUtils.removeCharacters(rgNumber) : null;		
		this.uf = StringUtils.isNotNull(rgUF)			? new UF(rgUF) : null;
		this.issuer = StringUtils.isNotNull(rgIssuer)	? new OrgaoEmissor(rgIssuer) : null;				
	}

	public String getNumber() 
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public OrgaoEmissor getIssuer() 
	{
		return issuer;
	}

	public void setIssuer(OrgaoEmissor issuer)
	{
		this.issuer = issuer;
	}

	public UF getUf()
	{
		return uf;
	}

	public void setUf(UF uf) 
	{
		this.uf = uf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
}
