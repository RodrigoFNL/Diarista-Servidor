package br.com.diarista.routine.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.work.entity.Work;

@Entity
@Table(name = "payment_system") 
public class PaymentSystem 
{	
	public static final short STATUS_OPEN = 0;
	public static final short STATUS_FAIL = 1;
	public static final short STATUS_SUCESS = 2;
	public static final short STATUS_IN_PROCESS = 3;
	public static final short STATUS_APPROVED = 4;
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 		
	private byte[] object; 	
	
	@Column(name = "attempt_send", columnDefinition = "INT2 DEFAULT 0")
	private short attempt;
	
	@Column(name = "date_register")
	private Date dateRegister;
	
	@Column(name = "date_send")
	private Date dateSend;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_cpf")
	private Usuario user;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "clean_lady_cpf")
	private Usuario cleanLady;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "work_id")
	private Work work;
	
	@Column(columnDefinition = "INT2 DEFAULT 0")
	private short status;

	public Long getId() {
		return id;
	}

	public byte[] getObject() {
		return object;
	}

	public short getAttempt() {
		return attempt;
	}

	public Date getDateRegister() {
		return dateRegister;
	}

	public Date getDateSend() {
		return dateSend;
	}

	public Usuario getUser() {
		return user;
	}

	public short getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setObject(byte[] object) {
		this.object = object;
	}

	public void setAttempt(short attempt) {
		this.attempt = attempt;
	}

	public void setDateRegister(Date dateRegister) {
		this.dateRegister = dateRegister;
	}

	public void setDateSend(Date dateSend) {
		this.dateSend = dateSend;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public Usuario getCleanLady() {
		return cleanLady;
	}

	public void setCleanLady(Usuario cleanLady) {
		this.cleanLady = cleanLady;
	}
}
