package br.com.diarista.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.dao.EstadoCivilDAO;
import br.com.diarista.entity.EstadoCivil;

@Service
public class EstadoCivilBusiness extends BasicBusiness<EstadoCivil>
{
	@Autowired
	private EstadoCivilDAO repository;
	
	
	@Override
	public List<EstadoCivil> getAllActive()
	{	
		return repository.findByStatus(true);
	}

}
