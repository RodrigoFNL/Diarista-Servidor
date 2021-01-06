package br.com.diarista.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.diarista.conf.EmailInfo;

@Component
public class EmailUtil
{	
	@Value("${spring.mail.port}")
	private Integer port = 587;

	String TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";	
	private String oauthClientId = ".apps.googleusercontent.com";
	private String refreshToken = "b3fjxfmgzglde4wai2o3gysorgqph66wynoe7g5hb4pixx4mig4q.mx-verification.google.com.";
	private String accessToken = "fixme";	
	private long tokenExpires = 1458168133864L;	

	private JavaMailSenderImpl sendMail;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void authEmail()
	{		
		sendMail = new JavaMailSenderImpl();	
		if(System.currentTimeMillis() > tokenExpires) 
		{
			try
			{
				StringBuilder request  = new StringBuilder();		
				
				request.append("client_id=").append(URLEncoder.encode(EmailInfo.ID_NAO_RESPONDA + oauthClientId, "UTF-8"));				
				request.append("&client_secret="+URLEncoder.encode(EmailInfo.PASSWORD_EMAIL_NAO_RESPONDA, "UTF-8"));
				request.append("&refresh_token="+URLEncoder.encode(refreshToken, "UTF-8"));
				request.append("&grant_type=refresh_token");
				
				HttpURLConnection conn = (HttpURLConnection) new URL(TOKEN_URL).openConnection(); conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				PrintWriter out = new PrintWriter(conn.getOutputStream());
				out.print(request.toString()); // note: println causes error
				out.flush();
				out.close();
				conn.connect();

				try
				{
					HashMap<String,Object> result;
					result = new ObjectMapper().readValue(conn.getInputStream(), HashMap.class);
					accessToken = (String) result.get("access_token");
					tokenExpires = System.currentTimeMillis()+(((Number)result.get("expires_in")).intValue()*1000);
				}
				catch (IOException e)
				{
					String line;
					BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
					while((line = in.readLine()) != null) 
					{
						System.out.println(line);
					}
					System.out.flush();
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		((JavaMailSenderImpl)this.sendMail).setPassword(accessToken);	  
	}

	public void sendCoupon(String email, String coupon)
	{		
		sendEmail(email, "Número do Coupon", coupon);
	}

	public void sendEmail(String emailAdress, String emailTitle, String emailText)
	{			
		try
		{		
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
			props.put("mail.smtp.auth.mechanisms", "true");
			props.put("mail.smtp.starttls.enable", "true");		
			//		props.put("mail.debug", "true");

			sendMail.send(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void sendEmail(String emailAdress, String emailTitle, String emailText, byte[] pdf) throws MessagingException, IOException 
	{
		try
		{
			sendMail.setHost(EmailInfo.SERVIDOR_SMTP);
			sendMail.setPort(port);
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
			props.put("mail.smtp.auth.mechanisms", "true");
			props.put("mail.smtp.starttls.enable", "true");	
			//	props.put("mail.debug", "true");   	    

			sendMail.send(mineMessage);	
			temp.delete();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
