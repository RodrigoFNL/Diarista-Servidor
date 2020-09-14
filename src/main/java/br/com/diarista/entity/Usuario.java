package br.com.diarista.entity;


import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.diarista.dto.UsuarioDTO;


@Entity
@Table(name = "usuario") 
public class Usuario extends BasicEntity<UsuarioDTO>
{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cpf")
	private String cpf;
	
	private String rg;
	private String rne;	
	
	@Column(nullable = false)
	private String name;	

	private String nickname;	
	private String login;	
	
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
	
	@Transient
	private String token;
	
	@Column(name = "is_prestar_servico", columnDefinition = "BOOL DEFAULT FALSE", nullable = false)
	private Boolean isPrestar_servico;
	
	@Column(name = "is_contratar", columnDefinition = "BOOL DEFAULT FALSE", nullable = false)
	private Boolean isContratar;
		
	@Column(name = "front_document")
	private byte [] frontDocument; 
	
	@Column(name = "back_document")
	private byte [] backDocument; 
	
	@Column(name = "hand_document")
	private byte [] handDocument; 	

	private byte [] signature; 
		
	@Override
	public UsuarioDTO getDTO() 
	{				
		UsuarioDTO dto = new UsuarioDTO();		
		dto.setCpf(this.cpf);		
		dto.setName(this.name);		
		dto.setEmail(this.email);
		dto.setLogin(this.login);
		dto.setCoupon(this.coupon);
		dto.setTermos_condicoes(this.termosCondicoes);		
	
		return dto;
	}
	public static Usuario getEntity(Map<String, Object> map) 
	{	
		return null;
//		Usuario user = new Usuario();		
//		if(map.containsKey("cpf")) 				user.setCpf((String) map.get("cpf"));
//		if(map.containsKey("rg")) 				user.setRg((String) map.get("rg"));
//		if(map.containsKey("rne")) 				user.setRne((String) map.get("rne"));
//		if(map.containsKey("name")) 			user.setName((String) map.get("name"));
//		if(map.containsKey("nickname")) 		user.setNickname((String) map.get("nickname"));
//		if(map.containsKey("login")) 			user.setLogin((String) map.get("login"));
//		if(map.containsKey("email")) 			user.setEmail((String) map.get("email"));
//		if(map.containsKey("coupon")) 			user.setCoupon((String) map.get("coupon"));		
//		if(map.containsKey("cell_phone")) 		user.setCell_phone((String) map.get("cell_phone"));		
//		if(map.containsKey("confirm_password")) user.setConfirm_password(StringUtils.encrypt((String) map.get("confirm_password")));
//		
//		if(map.containsKey("password") && StringUtils.notEnpty((String) map.get("password"))) user.setPassword( StringUtils.encrypt((String) map.get("password")).getBytes());
//				
//		user.setTermosCondicoes(map.containsKey("termos_condicoes") 	&& StringUtils.notEnpty((String) map.get("termos_condicoes"))	? Boolean.valueOf((String) map.get("termos_condicoes")) 	: false);
//		user.setIsPrestar_servico(map.containsKey("is_prestar_servico") && StringUtils.notEnpty((String) map.get("is_prestar_servico"))	? Boolean.valueOf((String) map.get("is_prestar_servico")) 	: false);	
//		user.setIsContratar(map.containsKey("is_contratar") 			&& StringUtils.notEnpty((String) map.get("is_contratar"))		? Boolean.valueOf((String) map.get("is_contratar")) 		: false);		
//				
//		
//		if(map.containsKey("frontDocument") && map.get("frontDocument") != null)user.setFrontDocument(((String) map.get("frontDocument")).getBytes());
//		if(map.containsKey("backDocument")	&& map.get("backDocument") != null) user.setBackDocument(((String) map.get("backDocument")).getBytes());
//		if(map.containsKey("handDocument")  && map.get("handDocument") != null) user.setHandDocument(((String) map.get("handDocument")).getBytes());
//		if(map.containsKey("signature")		&& map.get("signature") != null) 	user.setSignature(((String) map.get("signature")).getBytes());
		
		//if(map.containsKey("nationality")) 		user.setNationality(new Nacionalidade((String) map.get("nationality")));
		//if(map.containsKey("marital_status")) 	user.setMarital_status(new EstadoCivil((String) map.get("marital_status")));

	    //user.birth_date(new Date((String) map.get("birth_date")));		
	    //user.setCpf((String) map.get("andress"));
		
//		return user;
	}
			
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
	public String getLogin() 
	{
		return login;
	}
	public void setLogin(String login) 
	{
		this.login = login;
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
	
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Boolean getIsPrestar_servico() {
		return isPrestar_servico;
	}
	public void setIsPrestar_servico(Boolean isPrestar_servico) {
		this.isPrestar_servico = isPrestar_servico;
	}
	public Boolean getIsContratar() {
		return isContratar;
	}
	public void setIsContratar(Boolean isContratar) {
		this.isContratar = isContratar;
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

}
