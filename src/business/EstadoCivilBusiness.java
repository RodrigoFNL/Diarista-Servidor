package business;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;

import dao.EstadoCivilDAO;
import dto.EstadoCivilDTO;
import model.EstadoCivil;

@Stateful
public class EstadoCivilBusiness extends BasicBusiness<EstadoCivil>
{
	@Inject
	private EstadoCivilDAO repository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadoCivilDTO> getAllActive()
	{	
		return (List<EstadoCivilDTO>) repository.getAllDTO(true, "ORDER BY U.name");		
	}

}
