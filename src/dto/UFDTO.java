package dto;

public class UFDTO 
{
	private Long id; 
    private String sigla; 
    private String name; 
    private Boolean status;
    
	public Long getId() 
	{
		return id;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	public String getSigla() 
	{
		return sigla;
	}
	public void setSigla(String sigla) 
	{
		this.sigla = sigla;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) 
	{
		this.status = status;
	}

}
