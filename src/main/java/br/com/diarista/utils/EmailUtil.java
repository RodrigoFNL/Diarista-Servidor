package br.com.diarista.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import br.com.diarista.conf.EmailInfo;

@Component
public class EmailUtil
{	
	public boolean sendEmail(String emailAdress, String emailTitle, String emailText)
	{			
		try
		{			
			JavaMailSenderImpl sendMail = new JavaMailSenderImpl();	
			sendMail.setHost(EmailInfo.SERVIDOR_SMTP);
			sendMail.setPort(EmailInfo.PORT);
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
//			props.put("mail.debug", "true");

			sendMail.send(message);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

	}

	public boolean sendEmail(String emailAdress, String emailTitle, String emailText, byte[] pdf) throws MessagingException, IOException 
	{
		try
		{			
			JavaMailSenderImpl sendMail = new JavaMailSenderImpl();	
			sendMail.setHost(EmailInfo.SERVIDOR_SMTP);
			sendMail.setPort(EmailInfo.PORT);
			sendMail.setUsername(EmailInfo.USERNAME_NAO_RESPONDA);
			sendMail.setPassword(EmailInfo.PASSWORD_EMAIL_NAO_RESPONDA);	

			MimeMessage mineMessage = sendMail.createMimeMessage();		
			MimeMessageHelper message;
			message = new MimeMessageHelper(mineMessage, true);

			message.setFrom(EmailInfo.USERNAME_NAO_RESPONDA);
			message.setTo(emailAdress);
			message.setSubject(emailTitle);				
			message.setText(emailText);	

			File temp = File.createTempFile("Contrato", "pdf");

			FileOutputStream str = new FileOutputStream(temp);		
			str.write(pdf);
			str.flush();
			str.close();

			message.addAttachment("Contrato.pdf", temp);

			Properties props = sendMail.getJavaMailProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");	
//			props.put("mail.debug", "true");   	    

			sendMail.send(mineMessage);	
			temp.delete();
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
}
