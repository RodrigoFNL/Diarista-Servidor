package br.com.diarista.work.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.work.entity.Work;

public interface WorkDAO extends JpaRepository<Work, Long>
{
	Long countByDateAfterAndStatus(Date date, boolean status);
}
