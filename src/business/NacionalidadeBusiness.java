package business;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;

import dao.NacionalidadeDAO;
import dto.NacionalidadeDTO;
import model.Nacionalidade;

@Stateful
public class NacionalidadeBusiness extends BasicBusiness<Nacionalidade, NacionalidadeBusiness> {

	@Inject
	private NacionalidadeDAO repository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NacionalidadeDTO> getAllActive() {
		
		return (List<NacionalidadeDTO>) repository.getAllDTO(true);
	}
}
