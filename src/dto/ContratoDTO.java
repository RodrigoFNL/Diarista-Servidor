package dto;

public class ContratoDTO
{
	private Long id;
	private String artigo;
	private Boolean status;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	public String getArtigo() {
		return artigo;
	}
	public void setArtigo(String artigo)
	{
		this.artigo = artigo;
	}
	public Boolean getStatus() 
	{
		return status;
	}
	public void setStatus(Boolean status) 
	{
		this.status = status;
	}
}
