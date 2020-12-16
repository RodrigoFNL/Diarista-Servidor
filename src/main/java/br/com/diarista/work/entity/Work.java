package br.com.diarista.work.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.diarista.adress.entity.Endereco;
import br.com.diarista.folks.entity.Usuario;

@Entity
@Table(name = "work") 
public class Work  implements Serializable
{
	private static final long serialVersionUID = 1L;
	public final static short RESIDENCIAL = 1;
	public final static short COMERCIAL = 2;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "establishment_type")
	private Short  establishment_type;
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "adress_id", referencedColumnName = "id")
	private Endereco adress;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "work_task_ammount", joinColumns = @JoinColumn(name = "work_id",  referencedColumnName = "id"),  inverseJoinColumns = @JoinColumn(name = "task_ammount_id", referencedColumnName = "id"))
	private List<TaskAmmount> tasks;
	
	@ManyToOne
	@JoinColumn(name = "user_cpf", referencedColumnName = "cpf")
	private Usuario usuario;
	
	@ManyToMany
	@JoinTable(name = "work_cleaning_lady", joinColumns = @JoinColumn(name="work_id"), inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<Usuario> cleaningLadies;
	
    @Column(columnDefinition = "BOOL DEFAULT TRUE")
    private Boolean status;
	
	public Long getId() 
	{
		return id;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	public Short getEstablishment_type() {
		return establishment_type;
	}
	public void setEstablishment_type(Short establishment_type) 
	{
		this.establishment_type = establishment_type;
	}
	public Date getDate() 
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public Endereco getAdress()
	{
		return adress;
	}
	public void setAdress(Endereco adress)
	{
		this.adress = adress;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public List<TaskAmmount> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskAmmount> tasks) {
		this.tasks = tasks;
	}
	public List<Usuario> getCleaningLadies() {
		return cleaningLadies;
	}
	public void setCleaningLadies(List<Usuario> cleaningLadies) {
		this.cleaningLadies = cleaningLadies;
	}
}
