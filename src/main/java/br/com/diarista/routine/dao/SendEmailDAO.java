package br.com.diarista.routine.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.routine.entity.SendEmail;

public interface SendEmailDAO extends JpaRepository<SendEmail, Long> 
{
	public List<SendEmail> findByStatus(short status);
}
