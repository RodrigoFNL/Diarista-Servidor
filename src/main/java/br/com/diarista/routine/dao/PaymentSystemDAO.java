package br.com.diarista.routine.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.routine.entity.PaymentSystem;
import br.com.diarista.work.entity.Work;

public interface PaymentSystemDAO extends JpaRepository<PaymentSystem, Long> 
{
	List<PaymentSystem> findByStatus(short status);
	public Optional<PaymentSystem> findByWorkAndStatusNot(Work work, short status);
}
