package br.com.diarista.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.entity.Localidade;

public interface LocalidadeDAO extends JpaRepository<Localidade, String>
{
	public Boolean existsByCep(String cep);
	public Optional<Localidade> findByCep(String cep);

}
