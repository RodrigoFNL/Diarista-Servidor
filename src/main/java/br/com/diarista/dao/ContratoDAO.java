package br.com.diarista.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.entity.Contrato;

public interface ContratoDAO extends JpaRepository<Contrato, Long>
{

	List<Contrato> findByStatus(Boolean status);

}
