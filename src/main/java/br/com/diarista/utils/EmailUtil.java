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
		//message.setText(templateInfoCoupon(emailText));
		
		message.setText(emailText);
				
	    Properties props = sendMail.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
		
		sendMail.send(message);
	
	}
	
	@SuppressWarnings("unused")
	private static String templateInfoCoupon(String coupon)
	{
		StringBuilder template = new StringBuilder();	
				 
		 template.append("<!DOCTYPE html>");
		 template.append("<html>");
		 template.append("<head>");
		 template.append("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />");
		 template.append("<meta charset='utf-8'>");
		 template.append("<title>COUPON</title>");	
		 template.append("</head>");
		 template.append("<body>");
		 template.append("<header class='p-3 m-0 bg-dark text-light' mb-3>");
		 template.append("<h1 class='text-center'>DIARISTA ONLINE</h1>");
		 template.append("<p class='font-weight-bold'>Seja bem vindo a Comunidade DIARISTA ONLINE, É um imenso prazer, te-lo em nossa comunidade</p>");
		 template.append("</header>");
		 template.append("<section class='row justify-content-center mt-5 pb-5 shadow'>");
		 template.append("<section class='col-6 text-center'>");
		 template.append("<p>Número do seu Coupon</p>");
		 template.append("<hr />");
		 template.append("<p class='font-weight-bold'>").append(coupon).append("</p>");	
		 template.append("</section>");
		 template.append("</section>");
		 template.append("</body>");
		 template.append("</html>");
		 
		return template.toString();
	}
}
