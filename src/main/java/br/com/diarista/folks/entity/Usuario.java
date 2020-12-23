package br.com.diarista.folks.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.diarista.adress.entity.Endereco;
import br.com.diarista.folks.dto.AssessmentDTO;
import br.com.diarista.folks.dto.InfoUserDTO;
import br.com.diarista.folks.dto.UsuarioDTO;


@Entity
@Table(name = "usuario") 
public class Usuario implements Serializable
{	
	private static final long serialVersionUID = 1L;	
	public static final Short CADASTRO_INCOMPLETO = 1;
	public static final Short CADASTRO_NAO_APROVADO = 2;
	public static final Short CADASTRO_EM_ANALISE = 3;
	public static final Short CADASTRO_APROVADO = 4;	
	public static final Short USUARIO_ADMINISTRADOR = 5;	
	public static final Short USUARIO_BLOQUEADO= 6;	

	@Id
	@Column(name = "cpf", unique = true)
	private String cpf;
	
	@OneToOne
	@JoinColumn(name = "rg_id", referencedColumnName = "id")
	private RG rg;
	
	private String rne;	
	
	@Column(nullable = false)
	private String name;	

	private String nickname;
	
	@Column(nullable = false)
	private String email;	
	private String coupon;

	@Column(name = "termos_condicoes", columnDefinition = "BOOL DEFAULT TRUE", nullable = false)
	private Boolean termosCondicoes;	
	
	private byte [] password; 
	
	@Transient
	private String confirm_password;
	
	private Date birth_date;	
	private String cell_phone;
	
	@ManyToOne
	@JoinColumn(name = "nationality_id", referencedColumnName = "id")
	private Nacionalidade nationality;
	
	@ManyToOne
	@JoinColumn(name = "marital_status_id", referencedColumnName = "id")
	private EstadoCivil marital_status;
	
	@ManyToOne
	@JoinColumn(name = "andress_id", referencedColumnName = "id")
	private Endereco andress;
		
	@Column(name = "alter_password", columnDefinition = "BOOL DEFAULT FALSE", nullable = false)
	private Boolean isAlterPassword = false;
	
	@Column(name = "front_document")
	private byte [] frontDocument; 
	
	@Column(name = "back_document")
	private byte [] backDocument; 
	
	@Column(name = "hand_document")
	private byte [] handDocument; 	

	private byte [] signature; 
	
	private byte [] imagePortifile; 
	
	@Column(name = "registration_situation")
	private Short registrationSituation; 
	
	@Column(name = "ammount_register", columnDefinition = "INT8 DEFAULT 3")
	private Long ammountRegister; 	
		
	public Boolean getTermosCondicoes() 
	{
		return termosCondicoes;
	}
	public void setTermosCondicoes(Boolean termosCondicoes) 
	{
		this.termosCondicoes = termosCondicoes;
	}
	public String getCoupon()
	{
		return coupon;
	}
	public void setCoupon(String coupon) 
	{
		this.coupon = coupon;
	}
	
	public String getCpf()
	{
		return cpf;
	}
	public void setCpf(String cpf) 
	{
		this.cpf = cpf;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public byte[] getPassword() 
	{
		return password;
	}
	public void setPassword(byte[] password) 
	{
		this.password = password;
	}
	public String getEmail() 
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}	
	public String getRne() {
		return rne;
	}
	public void setRne(String rne) {
		this.rne = rne;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getConfirm_password() {
		return confirm_password;
	}
	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}
	public Date getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}
	public String getCell_phone() {
		return cell_phone;
	}
	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}
	public Nacionalidade getNationality() {
		return nationality;
	}
	public void setNationality(Nacionalidade nationality) {
		this.nationality = nationality;
	}
	public EstadoCivil getMarital_status() {
		return marital_status;
	}
	public void setMarital_status(EstadoCivil marital_status) {
		this.marital_status = marital_status;
	}
	public Endereco getAndress() {
		return andress;
	}
	public void setAndress(Endereco andress) {
		this.andress = andress;
	}
	public byte[] getFrontDocument() {
		return frontDocument;
	}
	public void setFrontDocument(byte[] frontDocument) {
		this.frontDocument = frontDocument;
	}
	public byte[] getBackDocument() {
		return backDocument;
	}
	public void setBackDocument(byte[] backDocument) {
		this.backDocument = backDocument;
	}
	public byte[] getHandDocument() {
		return handDocument;
	}
	public void setHandDocument(byte[] handDocument) {
		this.handDocument = handDocument;
	}
	public byte[] getSignature() {
		return signature;
	}
	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
	public RG getRg() {
		return rg;
	}
	public void setRg(RG rg) {
		this.rg = rg;
	}

	public Short getRegistrationSituation() {
		return registrationSituation;
	}

	public void setRegistrationSituation(Short registrationSituation) {
		this.registrationSituation = registrationSituation;
	}

	public Boolean getIsAlterPassword() {
		return isAlterPassword;
	}

	public void setIsAlterPassword(Boolean isAlterPassword) {
		this.isAlterPassword = isAlterPassword;
	}

	public UsuarioDTO getDTO() 
	{				
		UsuarioDTO dto = new UsuarioDTO();		
		dto.setCpf(this.cpf);		
		dto.setName(this.name);		
		dto.setEmail(this.email);
		dto.setCoupon(this.coupon);
		dto.setTermos_condicoes(this.termosCondicoes);		
		return dto;
	}
	public InfoUserDTO getSimpleDTO()
	{
		InfoUserDTO dto = new InfoUserDTO();	
		dto.setNickName(this.nickname);
		if(this.imagePortifile != null)	dto.setImage(Base64.getEncoder().encodeToString(this.imagePortifile));
		else dto.setImage("");		
		return dto;
	}
	public InfoUserDTO getInfoUserDTO() 
	{		
		InfoUserDTO dto = new InfoUserDTO();	
		dto.setNickName(this.nickname);
		dto.setCpf(this.cpf);
		dto.setEmail(this.email);
		dto.setRegistrationSituation(this.registrationSituation);
		dto.setIsAlterPassword(this.isAlterPassword);
		dto.setAdress(this.andress);
		
		if(this.imagePortifile != null)	dto.setImage(Base64.getEncoder().encodeToString(this.imagePortifile));
		else dto.setImage("");
		return dto;
	}

	public byte[] getImagePortifile() {
		return imagePortifile;
	}

	public void setImagePortifile(byte[] imagePortifile) {
		this.imagePortifile = imagePortifile;
	}

	public Long getAmmountRegister() {
		return ammountRegister;
	}

	public void setAmmountRegister(Long ammountRegister) {
		this.ammountRegister = ammountRegister;
	}
	public InfoUserDTO getSimpleDTO(List<Assessment> assessment)
	{	
		InfoUserDTO dto = new InfoUserDTO();	
		dto.setNickName(this.nickname);			
		if(this.imagePortifile != null)	dto.setImage(Base64.getEncoder().encodeToString(this.imagePortifile));
		else dto.setImage("");
		
		dto.setAssessments(new ArrayList<AssessmentDTO>());
		if(assessment != null && !assessment.isEmpty())
		for (Assessment assess : assessment)
		{			
			dto.getAssessments().add(assess.getDTO());
		}				
		return dto;
	}
}















