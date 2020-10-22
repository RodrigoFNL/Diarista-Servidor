package br.com.diarista.dto;

import java.util.List;

public class ContratoDTO
{
	private Long id;	
	private Short index;			
	private String clausula;	
	private List<ParagrafoDTO> paragrafo;	
	private Boolean status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Short getIndex() {
		return index;
	}
	public void setIndex(Short index) {
		this.index = index;
	}
	public String getClausula() {
		return clausula;
	}
	public void setClausula(String clausula) {
		this.clausula = clausula;
	}
	public List<ParagrafoDTO> getParagrafo() {
		return paragrafo;
	}
	public void setParagrafo(List<ParagrafoDTO> paragrafo) {
		this.paragrafo = paragrafo;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
		
}
