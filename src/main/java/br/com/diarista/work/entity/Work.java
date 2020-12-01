package br.com.diarista.work.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
	
	@ManyToMany
	@JoinTable(name = "work_task", joinColumns=@JoinColumn(name="work_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="task_id", referencedColumnName="id"))				
	private List<Task> tasks;	
	
	@ManyToOne
	@JoinColumn(name = "user_cpf", referencedColumnName = "cpf")
	private Usuario usuario;
	
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
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) 
	{
		this.tasks = tasks;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
