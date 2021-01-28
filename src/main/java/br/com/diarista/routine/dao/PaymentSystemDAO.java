package br.com.diarista.routine.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.routine.entity.PaymentSystem;

public interface PaymentSystemDAO extends JpaRepository<PaymentSystem, Long> 
{
	List<PaymentSystem> findByStatus(short status);

}
