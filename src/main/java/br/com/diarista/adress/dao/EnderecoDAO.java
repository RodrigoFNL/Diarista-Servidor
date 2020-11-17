package br.com.diarista.adress.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.adress.entity.Endereco;

public interface EnderecoDAO extends JpaRepository<Endereco, Long>
{

}
