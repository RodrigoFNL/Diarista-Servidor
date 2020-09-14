package br.com.diarista.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.entity.EstadoCivil;

public interface EstadoCivilDAO extends JpaRepository<EstadoCivil, Long>
{

	List<EstadoCivil> findByStatus(Boolean status);

}
