package br.com.diarista.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.entity.UF;

public interface UFDAO extends JpaRepository<UF, Long>
{
	List<UF> findByStatus(Boolean status);
}