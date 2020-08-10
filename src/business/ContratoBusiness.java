package business;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;

import dao.ContratoDAO;
import model.Contrato;

@Stateful
public class ContratoBusiness extends BasicBusiness<Contrato>
{
	
	@Inject
	private ContratoDAO repository;
	
	@Override
	public List<?> getAllActive() 
	{		
		return repository.getAllDTO(true, " ORDER BY U.id ");
	}	
}
