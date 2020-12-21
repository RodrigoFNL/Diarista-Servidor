package br.com.diarista.folks.dto;

public class UsuarioDTO
{
    private String cpf;
    private String name;       
    private String email;	
    private String coupon;
    private Boolean termos_condicoes;
    private String password;
    private String confirm_password;
    private String token;
    
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) 
	{
		this.coupon = coupon;
	}
	public String getPassword() 
	{
		return password;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
	public Boolean getTermos_condicoes() {
		return termos_condicoes;
	}
	public void setTermos_condicoes(Boolean termos_condicoes) {
		this.termos_condicoes = termos_condicoes;
	}
	public String getConfirm_password() {
		return confirm_password;
	}
	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
