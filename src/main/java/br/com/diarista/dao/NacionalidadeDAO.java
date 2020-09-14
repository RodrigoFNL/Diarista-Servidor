package br.com.diarista.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.entity.Nacionalidade;

public interface NacionalidadeDAO extends JpaRepository<Nacionalidade, Long>
{
	List<Nacionalidade> findByStatus(Boolean status);	
}
