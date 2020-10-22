package br.com.diarista.dto;

public class ParagrafoDTO 
{
	private Long id;
	private String paragrafo;	
	private Boolean status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getParagrafo() {
		return paragrafo;
	}
	public void setParagrafo(String paragrafo) {
		this.paragrafo = paragrafo;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
