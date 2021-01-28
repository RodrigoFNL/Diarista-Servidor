package br.com.diarista.routine.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.routine.dao.PaymentSystemDAO;
import br.com.diarista.routine.entity.PaymentSystem;

@Service
public class PaymentSystemBusiness 
{
	@Autowired
	private PaymentSystemDAO repository;

	public List<PaymentSystem> getAllOpenPayment()
	{		
		return repository.findByStatus(PaymentSystem.STATUS_OPEN);
	}

}
