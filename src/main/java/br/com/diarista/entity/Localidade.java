package br.com.diarista.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.diarista.dto.LocalidadeDTO;
import br.com.diarista.utils.StringUtils;

@Entity
@Table(name="locality")
public class Localidade  extends BasicEntity<LocalidadeDTO>
{
	private static final long serialVersionUID = 1L;
	@Id
	private String cep;
	private String bairro;
	private String complemento;           
	private String ibge;
	private String localidade;
	private String logradouro;
      
    @ManyToOne
    @JoinColumn(name = "uf_id", referencedColumnName = "id")
    private UF uf;
    
	public Localidade() 
	{}
    
	public Localidade(String cep, String localidade, String logradouro, String bairro, UF uf) 
	{
		if(StringUtils.isNotNull(cep)) 			this.cep = StringUtils.removeCharacters(cep);
		if(StringUtils.isNotNull(localidade)) 	this.localidade = localidade;
		if(StringUtils.isNotNull(logradouro)) 	this.logradouro = logradouro;
		if(StringUtils.isNotNull(bairro)) 		this.bairro = bairro;
		this.uf = uf;		
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getIbge() {
		return ibge;
	}
	public void setIbge(String ibge) {
		this.ibge = ibge;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public UF getUf() {
		return uf;
	}
	public void setUf(UF uf) {
		this.uf = uf;
	}

	@Override
	public LocalidadeDTO getDTO() 
	{	
		LocalidadeDTO dto = new LocalidadeDTO();
		
		dto.setCep(this.cep);
		dto.setBairro(this.bairro);
		dto.setComplemento(this.complemento);
		dto.setIbge(this.ibge);
		dto.setLocalidade(this.localidade);
		dto.setLogradouro(this.logradouro);
		dto.setUf(this.uf != null? this.uf.getSigla() : null);
		
		return dto;		
	}    
}
