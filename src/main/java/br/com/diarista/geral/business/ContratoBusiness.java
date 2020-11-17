package br.com.diarista.geral.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.geral.dao.ContratoDAO;
import br.com.diarista.geral.entity.Contrato;

@Service
public class ContratoBusiness extends BasicBusiness<Contrato>
{
	@Autowired
	private ContratoDAO repository;
	
	@Override
	public List<Contrato> getAllActive() 
	{		
		return repository.findByStatusOrderByIndexAsc(true); 
	}	
}
