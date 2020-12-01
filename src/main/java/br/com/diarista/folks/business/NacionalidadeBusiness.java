package br.com.diarista.folks.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.folks.dao.NacionalidadeDAO;
import br.com.diarista.folks.entity.Nacionalidade;
import br.com.diarista.folks.entity.Usuario;

@Service
public class NacionalidadeBusiness extends BasicBusiness<Nacionalidade>
{
	@Autowired
	private NacionalidadeDAO repository;
	
	@Override
	public List<Nacionalidade> getAllActive() 
	{		
		return repository.findByStatus(true);
	}

	@Override
	public String register(Nacionalidade object, Usuario user)
	{
		return null;
	}
}
