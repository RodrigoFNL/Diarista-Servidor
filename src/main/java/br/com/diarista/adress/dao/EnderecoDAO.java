package br.com.diarista.adress.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.adress.entity.Endereco;
import br.com.diarista.adress.entity.Localidade;

public interface EnderecoDAO extends JpaRepository<Endereco, Long>
{
	Optional<Endereco> findByLocalityAndNumber(Localidade locality, String number);
}
