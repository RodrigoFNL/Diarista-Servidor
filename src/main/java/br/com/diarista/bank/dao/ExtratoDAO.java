package br.com.diarista.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.bank.entity.Extrato;

public interface ExtratoDAO extends JpaRepository<Extrato, Long>
{

}
