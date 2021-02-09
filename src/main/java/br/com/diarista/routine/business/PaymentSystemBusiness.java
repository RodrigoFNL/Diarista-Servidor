package br.com.diarista.routine.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.routine.dao.PaymentSystemDAO;
import br.com.diarista.routine.entity.PaymentSystem;
import br.com.diarista.work.entity.Work;

@Service
public class PaymentSystemBusiness 
{
	@Autowired
	private PaymentSystemDAO repository;

	public List<PaymentSystem> getAllOpenPayment()
	{		
		return repository.findByStatus(PaymentSystem.STATUS_OPEN);
	}

	public void save(PaymentSystem payment) 
	{
		repository.save(payment);	
	}

	public PaymentSystem getPaymentSystemByWork(Work work) 
	{
		Optional<PaymentSystem> optional = repository.findByWorkAndStatusNot(work, PaymentSystem.STATUS_FAIL);		
		return optional != null && !optional.isEmpty()? optional.get() : null;
	}
}
