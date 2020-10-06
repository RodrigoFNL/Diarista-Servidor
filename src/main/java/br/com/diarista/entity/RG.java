package br.com.diarista.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import br.com.diarista.utils.StringUtils;

@Entity
@Table(name = "rg") 
public class RG 
{
	@Id
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
    
}
