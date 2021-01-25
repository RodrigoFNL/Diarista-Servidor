package br.com.diarista.routine.routine;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class PaymentRoutine 
{	
	private void executePayment() 
	{
		System.out.println("Realizando o pagamento");
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
