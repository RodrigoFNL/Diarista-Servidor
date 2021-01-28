package br.com.diarista.routine.routine;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.diarista.conf.EmailInfo;
import br.com.diarista.routine.business.PaymentSystemBusiness;
import br.com.diarista.routine.entity.PaymentSystem;
import br.com.diarista.utils.EmailUtil;

@Component
public class PaymentRoutine 
{	
	@Autowired
	private PaymentSystemBusiness business;
	
	@Autowired
	private EmailUtil email;
	
	private void executePayment() 
	{
		System.out.println("Realizando o pagamento");
		paymentOpen();	
	}
	
	private void paymentOpen() 
	{
		try
		{			
			List<PaymentSystem> payments = business.getAllOpenPayment();	
			if(payments != null && payments.size() > 0)
			{
				Gson gson = new Gson();
				for (PaymentSystem pay : payments)
				{					
					System.out.println(gson.toJson(pay));
				}				
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			email.sendEmail(EmailInfo.EMAIL_ADMINISTRADOR, "Error no Sistema de pagamento", e.getMessage());
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
		    	executePayment();
		    }		    
		}, 20000, 120000);
	}	
}
