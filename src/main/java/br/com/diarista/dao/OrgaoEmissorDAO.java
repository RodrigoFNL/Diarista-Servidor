package br.com.diarista.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.entity.OrgaoEmissor;

public interface OrgaoEmissorDAO extends JpaRepository<OrgaoEmissor, Long> 
{

	List<OrgaoEmissor> findByStatus(Boolean status);

}
