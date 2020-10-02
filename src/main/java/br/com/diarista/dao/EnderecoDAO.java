package br.com.diarista.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.entity.Endereco;

public interface EnderecoDAO extends JpaRepository<Endereco, Long>
{

}
