package br.com.diarista.adress.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.adress.entity.Localidade;

public interface LocalidadeDAO extends JpaRepository<Localidade, String>
{
	public Boolean existsByCep(String cep);
	public Optional<Localidade> findByCep(String cep);

}
