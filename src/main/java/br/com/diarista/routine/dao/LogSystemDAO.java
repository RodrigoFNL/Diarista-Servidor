package br.com.diarista.routine.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.routine.entity.LogSystem;

public interface LogSystemDAO extends JpaRepository<LogSystem, Long> 
{

}
