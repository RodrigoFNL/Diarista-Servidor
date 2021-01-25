package br.com.diarista.routine.routine;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.diarista.routine.business.SendEmailBusiness;
import br.com.diarista.routine.entity.SendEmail;

@Component
public class EmailRoutine
{
	@Autowired
	private SendEmailBusiness emailBusiness;
	
	private void sendMail() 
	{	
		List<SendEmail> emails = emailBusiness.getEmailsPendentes();		
		if(emails != null && emails.size() > 0)
		{
			for (SendEmail email : emails) 
			{
				emailBusiness.sendEmail(email);
			}		
		}
	}
	
	@PostConstruct
	public void run() 
	{	
		
		Timer timer = new Timer(); 
		timer.scheduleAtFixedRate(new TimerTask() 
		{
		    @Override 
		    public void run()
		    { 
		    	sendMail();
		    }		    
		}, 20000, 120000);
	}	
}
