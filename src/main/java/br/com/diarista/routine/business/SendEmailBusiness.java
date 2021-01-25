package br.com.diarista.routine.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.routine.dao.SendEmailDAO;
import br.com.diarista.routine.entity.SendEmail;
import br.com.diarista.utils.EmailUtil;
import br.com.diarista.utils.PDFUtils;

@Service
public class SendEmailBusiness
{
	@Autowired
	private SendEmailDAO emailRepository;	
	
	@Autowired
	PDFUtils utils;
	
	@Autowired
	EmailUtil emailUtil;
	
	public List<SendEmail> getEmailsPendentes() 
	{
		try
		{			
			List<SendEmail> emails = emailRepository.findByStatus(SendEmail.STATUS_OPEN);
			return emails;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	public void sendEmail(SendEmail email) 
	{
		try
		{	
			if(email.isSendContract() && email.getUser() != null)
			{	
				emailUtil.sendEmail(email.getRecipient(), email.getSubject(), email.getBody(), utils.getPDF("Contrato.jasper", email.getUser()));
				return;
			}
			emailUtil.sendEmail(email.getRecipient(), email.getSubject(), email.getBody());
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
			
		
	}
}
