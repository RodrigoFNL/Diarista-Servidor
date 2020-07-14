package dto;

public class LocalidadeDTO
{
	private String bairro;
    private String cep;
    private String complemento;    
    private String ibge;
    private String localidade;
    private String logradouro;
    private String uf;
    
	public String getBairro() 
	{
		return bairro;
	}
	public void setBairro(String bairro) 
	{
		this.bairro = bairro;
	}
	public String getCep() 
	{
		return cep;
	}
	public void setCep(String cep) 
	{
		this.cep = cep;
	}
	public String getComplemento() 
	{
		return complemento;
	}
	public void setComplemento(String complemento) 
	{
		this.complemento = complemento;
	}
	public String getIbge()
	{
		return ibge;
	}
	public void setIbge(String ibge)
	{
		this.ibge = ibge;
	}
	public String getLocalidade() 
	{
		return localidade;
	}
	public void setLocalidade(String localidade)
	{
		this.localidade = localidade;
	}
	public String getLogradouro() 
	{
		return logradouro;
	}
	public void setLogradouro(String logradouro) 
	{
		this.logradouro = logradouro;
	}
	public String getUf() 
	{
		return uf;
	}
	public void setUf(String uf) 
	{
		this.uf = uf;
	}   
}
