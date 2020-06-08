package util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
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

import conf.EmailInfo;

public class EmailUtil
{


	public static void sendCoupon(String email, String coupon)
	{		
		sendEmail(email, "Número do Cupon", coupon);
	}
	public static void sendEmail(String email, String title, String text)
	{
		try 
		{
			//INSERE AS PROPRIEDADES DO EMAIL, COMO LOGIN, SENHA, SMTP.
			Properties props = new Properties();

			props.put("mail.from", EmailInfo.EMAIL_NAO_RESPONDA);
			props.setProperty("mail.smtp.user", EmailInfo.EMAIL_NAO_RESPONDA);
			props.setProperty("mail.smtp.password", EmailInfo.PASSWORD_EMAIL_NAO_RESPONDA);

			props.put("mail.smtp.host", EmailInfo.SERVIDOR_SMTP);
			props.put("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.auth", "true");


			Writer out = new StringWriter();

			BodyPart body = new MimeBodyPart();					
			body.setContent(out.toString(), "text/html; charset=utf-8");			

			Session session = Session.getInstance(props, new Authenticator() 
			{
				public PasswordAuthentication getPasswordAuthentication() 
				{
					return new PasswordAuthentication(EmailInfo.EMAIL_NAO_RESPONDA, EmailInfo.PASSWORD_EMAIL_NAO_RESPONDA);
				}
			});

			Message msg = new MimeMessage(session);			

			Address[] adrAddress = new Address[1];
			adrAddress[0] = new InternetAddress(email);

			msg.setRecipients(RecipientType.TO, adrAddress);

			msg.setFrom(new InternetAddress(EmailInfo.EMAIL_NAO_RESPONDA));
			msg.setSubject(title);
			msg.setSentDate(new Date());
			msg.setContent(text, "text/html; charset=utf-8");

			Transport  transport = session.getTransport("smtp");
			transport.connect(EmailInfo.SERVIDOR_SMTP, EmailInfo.EMAIL_NAO_RESPONDA, EmailInfo.PASSWORD_EMAIL_NAO_RESPONDA);
			Transport.send(msg);
			transport.close();

		} 
		catch (Exception e) 
		{
			e.printStackTrace();			
		}	
	}
}
