package br.com.diarista.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.entity.RG;

public interface RGDAO extends JpaRepository<RG, String>
{

}
