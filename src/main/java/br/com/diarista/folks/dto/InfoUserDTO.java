package br.com.diarista.folks.dto;

import java.util.List;

import br.com.diarista.adress.entity.Endereco;
import br.com.diarista.folks.entity.Assessment;

public class InfoUserDTO 
{
	private String nickName;
	private String cpf;
	private String email;
	private Short registrationSituation;
	private String image;
	private Boolean isAlterPassword;
	private Endereco adress;
    private List<Assessment> assessments;
			
	public String getCpf() 
	{
		return cpf;
	}
	public void setCpf(String cpf) 
	{
		this.cpf = cpf;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public Short getRegistrationSituation() 
	{
		return registrationSituation;
	}
	public void setRegistrationSituation(Short registrationSituation) 
	{
		this.registrationSituation = registrationSituation;
	}
	public String getImage() 
	{
		return image;
	}
	public void setImage(String image)
	{
		this.image = image;
	}
	public String getNickName() 
	{
		return nickName;
	}
	public void setNickName(String nickName) 
	{
		this.nickName = nickName;
	}
	public Boolean getIsAlterPassword()
	{
		return isAlterPassword;
	}
	public void setIsAlterPassword(Boolean isAlterPassword)
	{
		this.isAlterPassword = isAlterPassword;
	}
	public Endereco getAdress() {
		return adress;
	}
	public void setAdress(Endereco adress) {
		this.adress = adress;
	}
	public List<Assessment> getAssessments() {
		return assessments;
	}
	public void setAssessments(List<Assessment> assessments) {
		this.assessments = assessments;
	} 	
}
