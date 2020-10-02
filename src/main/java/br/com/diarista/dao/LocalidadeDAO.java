package br.com.diarista.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.entity.Localidade;

public interface LocalidadeDAO extends JpaRepository<Localidade, String>
{

}
