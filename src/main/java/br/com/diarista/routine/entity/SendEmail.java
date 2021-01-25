package br.com.diarista.routine.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.diarista.folks.entity.Usuario;

@Entity
@Table(name = "send_email") 
public class SendEmail implements Serializable
{
	private static final long serialVersionUID = 1L;	
	public static final short STATUS_OPEN = 0;
	public static final short STATUS_FAIL = 1;
	public static final short STATUS_SUCESS = 2;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 		
	private String recipient;
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String body;
	
	@Column(columnDefinition = "INT2 DEFAULT 0")
	private short status;
	
	@Column(name = "attempt", columnDefinition = "INT2 DEFAULT 0")
	private short attempt;
	
	@Column(name = "date_register")
	private Date dateRegister;
	
	@Column(name = "date_send_mail")
	private Date dateSendMail;
	
	@Column(name = "is_send_contract", columnDefinition = "BOOL DEFAULT FALSE")
	private boolean isSendContract;
	
	@ManyToOne
	@JoinColumn(name = "user_cpf")
	private Usuario user;
	
	//Métodos sets e gets

	public Long getId() {
		return id;
	}


	public String getSubject() {
		return subject;
	}

	public short getStatus() {
		return status;
	}

	public Date getDateRegister() {
		return dateRegister;
	}

	public Date getDateSendMail() {
		return dateSendMail;
	}

	public boolean isSendContract() {
		return isSendContract;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public void setDateRegister(Date dateRegister) {
		this.dateRegister = dateRegister;
	}

	public void setDateSendMail(Date dateSendMail) {
		this.dateSendMail = dateSendMail;
	}

	public void setSendContract(boolean isSendContract) {
		this.isSendContract = isSendContract;
	}
	
	public String getRecipient() {
		return recipient;
	}
	
	public short getAttempt() {
		return attempt;
	}
	
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public void setAttempt(short attempt) {
		this.attempt = attempt;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}	
}
