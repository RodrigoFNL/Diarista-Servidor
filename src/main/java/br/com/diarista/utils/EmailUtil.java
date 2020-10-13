package br.com.diarista.utils;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import br.com.diarista.conf.EmailInfo;

@Component
public class EmailUtil
{
	@Value("${spring.mail.port}")
	private Integer port = 587;
	
	public void sendCoupon(String email, String coupon)
	{		
		sendEmail(email, "Número do Coupon", coupon);
	}
	public void sendEmail(String emailAdress, String emailTitle, String emailText)
	{	
		JavaMailSenderImpl sendMail = new JavaMailSenderImpl();		
		
		sendMail.setHost(EmailInfo.SERVIDOR_SMTP);
		sendMail.setPort(port);
		sendMail.setUsername(EmailInfo.USERNAME_NAO_RESPONDA);
		sendMail.setPassword(EmailInfo.PASSWORD_EMAIL_NAO_RESPONDA);
		
		SimpleMailMessage message = new SimpleMailMessage();	
		
		message.setFrom(EmailInfo.USERNAME_NAO_RESPONDA);
		message.setTo(emailAdress);
		message.setSubject(emailTitle);
				
		message.setText(emailText);
				
	    Properties props = sendMail.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
		
		sendMail.send(message);
	
	}
	
	public void sendEmail(String emailAdress, String emailTitle, String emailText, byte[] backDocument) 
	{
		JavaMailSenderImpl sendMail = new JavaMailSenderImpl();		
		
		sendMail.setHost(EmailInfo.SERVIDOR_SMTP);
		sendMail.setPort(port);
		sendMail.setUsername(EmailInfo.USERNAME_NAO_RESPONDA);
		sendMail.setPassword(EmailInfo.PASSWORD_EMAIL_NAO_RESPONDA);	
		
		SimpleMailMessage message = new SimpleMailMessage();	
		
		message.setFrom(EmailInfo.USERNAME_NAO_RESPONDA);
		message.setTo(emailAdress);
		message.setSubject(emailTitle);				
		message.setText(emailText);	
				
	    Properties props = sendMail.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");   	    
		
		sendMail.send(message);		
	}
}
