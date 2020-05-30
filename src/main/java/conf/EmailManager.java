package conf;

import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class EmailManager {

	private String recipientEmail;
	private LocalDateTime sentDate;
	private String subject;
	private String text;
	private Map<String, String> parameters;

	private String anexo;

	public EmailManager() {
		this("", null, "", "");
	}

	public EmailManager(String recipientEmail, LocalDateTime sentDate, String subject, String text) {
		setRecipientEmail(recipientEmail);
		setSentDate(getSentDate());
		setSubject(subject);
		setText(text);
	}

	public EmailManager(List<String> recipientEmails, LocalDateTime sentDate, String subject, String text) 
	{		
		setSentDate(getSentDate());
		setSubject(subject);
		setText(text);
	}

	public EmailManager(List<String> recipientEmails, LocalDateTime sentDate, String subject, String templateLoaderPath, String templateName, String anexo) 
	{	
		setSentDate(getSentDate());
		setSubject(subject);
		setAnexo(anexo);
		this.parameters = new HashMap<String, String>();
	}
	
	public EmailManager(List<String> recipientEmails, LocalDateTime sentDate, String subject, String templateName, String anexo) 
	{	
		setSentDate(getSentDate());
		setSubject(subject);	
		setAnexo(anexo);
		this.parameters = new HashMap<String, String>();
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	public LocalDateTime getSentDate() {
		return sentDate;
	}

	public void setSentDate(LocalDateTime sentDate) {
		this.sentDate = sentDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public void addParameter(String key, String value) {
		parameters.put(key, value);
	}
	
	public void sendEmail() throws Exception 
	{
		try 
		{
			//INSERE AS PROPRIEDADES DO EMAIL, COMO LOGIN, SENHA, SMTP.
			Properties props = new Properties();
			
			props.put("mail.smtp.host", EmailInfo.SMTP_SGS_GMAIL);
			props.put("mail.from", EmailInfo.EMAIL_SGS_GMAIL);
			props.put("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.user", EmailInfo.EMAIL_SGS_GMAIL);
			props.setProperty("mail.smtp.password", EmailInfo.SENHA_SGS_GMAIL);
			props.setProperty("mail.smtp.auth", "true");				
			
			//Carrega template
			BodyPart body = new MimeBodyPart();		
			Writer out = new StringWriter();
			
			//Monta o template		
			body.setContent(out.toString(), "text/html; charset=utf-8");			

			Session session = Session.getInstance(props, new Authenticator() 
			{
				public PasswordAuthentication getPasswordAuthentication() 
				{
				   return new PasswordAuthentication(EmailInfo.EMAIL_SGS_GMAIL, EmailInfo.SENHA_SGS_GMAIL);
				}
			});
			
			Message msg = new MimeMessage(session);			
			
			Address[] adrAddress = new Address[1];
			adrAddress[0] = new InternetAddress(recipientEmail);	
			
			msg.setRecipients(RecipientType.TO, adrAddress);					
			msg.setFrom(new InternetAddress(EmailInfo.EMAIL_SGS_GMAIL));
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setContent(text, "text/html; charset=utf-8");
				
			Transport transport;		
			transport = session.getTransport("smtp");
			transport.connect(EmailInfo.SMTP_SGS_GMAIL, EmailInfo.EMAIL_SGS_GMAIL, EmailInfo.SENHA_SGS_GMAIL);
			Transport.send(msg);
			transport.close();		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}