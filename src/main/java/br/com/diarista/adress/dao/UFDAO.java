package br.com.diarista.adress.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.adress.entity.UF;

public interface UFDAO extends JpaRepository<UF, Long>
{
	List<UF> findByStatus(Boolean status);
	List<UF> findBySigla(String sigla);
}
