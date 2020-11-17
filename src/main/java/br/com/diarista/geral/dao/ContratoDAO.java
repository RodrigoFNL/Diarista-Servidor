package br.com.diarista.geral.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.geral.entity.Contrato;

public interface ContratoDAO extends JpaRepository<Contrato, Long>
{
	List<Contrato> findByStatusOrderByIndexAsc(Boolean status);
}
