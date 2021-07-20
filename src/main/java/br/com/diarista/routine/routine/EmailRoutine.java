package br.com.diarista.routine.routine;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.diarista.routine.business.LogSystemBusiness;
import br.com.diarista.routine.business.SendEmailBusiness;
import br.com.diarista.routine.entity.LogSystem;
import br.com.diarista.routine.entity.SendEmail;

@Component
public class EmailRoutine
{
	@Autowired
	private SendEmailBusiness emailBusiness;

	@Autowired
	private LogSystemBusiness LogBusiness;

	private void sendMail() 
	{	
		try
		{
			List<SendEmail> emails = emailBusiness.getEmailsPendentes();		
			if(emails != null && emails.size() > 0)
			{
				for (SendEmail email : emails) 
				{
					boolean isSucess = emailBusiness.sendEmail(email);				
					if(isSucess)
					{
						email.setStatus(SendEmail.STATUS_SUCESS);
						email.setDateSendMail(new Date());
						emailBusiness.save(email);
					}
					else if (!isSucess) 
					{
						if(email.getAttempt() < 5) email.setAttempt((short) (email.getAttempt() + (short) 1));
						else 
						{
							email.setStatus(SendEmail.STATUS_FAIL);					
							LogSystem log = new LogSystem();
							log.setDate(new Date());
							log.setTypeLog(LogSystem.TYPE_ERROR);
							log.setDescription("Não foi possível enviar o Email para " + email.getRecipient() + " Após 5 tentativas, o mesmo foi excluído! ");
							log.setLineCode("EmailRoutine entre as linhas 30 ao 58");
							LogBusiness.save(log);
						}
						emailBusiness.save(email);
					}
				}		
			}
		}
		catch (Exception e) 
		{
			LogSystem log = new LogSystem();
			log.setDate(new Date());
			log.setTypeLog(LogSystem.TYPE_ERROR);
			log.setDescription("Ocorreu um erro no sistema de envio de email");
			log.setLineCode("EmailRoutine entre as linhas 30 ao 58");
			LogBusiness.save(log);
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
				try
				{
					sendMail();
				}
				catch (Exception e)
				{
					System.out.println(e);
				}
			}		    
		}, 20000, 120000);
	}	
}
