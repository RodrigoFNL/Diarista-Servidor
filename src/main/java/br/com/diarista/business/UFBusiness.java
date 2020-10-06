package br.com.diarista.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.dao.UFDAO;
import br.com.diarista.entity.UF;

@Service
public class UFBusiness extends BasicBusiness<UF>
{
	@Autowired
	private UFDAO repository;

	@Override
	public List<UF> getAllActive() 
	{		
		return repository.findByStatus(true);
	}

}
