package business;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;

import dao.UFDAO;
import model.UF;

@Stateful
public class UFBusiness extends BasicBusiness<UF>
{
	@Inject
	private UFDAO repository;

	@Override
	public List<?> getAllActive() 
	{		
		return repository.getAllDTO("ORDER BY U.name");
	}

}
