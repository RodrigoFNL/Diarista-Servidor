package util;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import conf.EmailInfo;

public class EmailUtil
{


	public static void sendCoupon(String email, String coupon)
	{		
		sendEmail(email, "Número do Coupon", coupon);
	}
	public static void sendEmail(String email, String title, String text)
	{	
		String to = email;	
		String from = EmailInfo.EMAIL_NAO_RESPONDA;
		final String username = EmailInfo.USERNAME_NAO_RESPONDA;
		final String password = EmailInfo.PASSWORD_EMAIL_NAO_RESPONDA;
		String host = EmailInfo.SERVIDOR_SMTP;
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(username, password);
			}
		});
		try
		{		
			Message message = new MimeMessage(session);		
			
			message.setSentDate(new Date());				
			message.setFrom(new InternetAddress(from));	
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(to));	
			message.setSubject(title);		
			message.setContent(templateInfoCoupon(text), "text/html; charset=utf-8");
			Transport.send(message);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	private static String templateInfoCoupon(String coupon)
	{
		StringBuilder template = new StringBuilder();	
				 
		 template.append("<!DOCTYPE html>");
		 template.append("<html>");
		 template.append("<head>");
		 template.append("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' />");
		 template.append("<meta charset='utf-8'>");
		 template.append("<title>COUPON</title>");	
		 template.append("</head>");
		 template.append("<body>");
		 template.append("<header class='p-3 m-0 bg-dark text-light' mb-3>");
		 template.append("<h1 class='text-center'>DIARISTA ONLINE</h1>");
		 template.append("<p class='font-weight-bold'>Seja bem vindo a Comunidade DIARISTA ONLINE, é um imenso prazer, te-lo em nossa comunidade</p>");
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
