package business;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;

import dao.OrgaoEmissorDAO;
import model.OrgaoEmissor;

@Stateful
public class OrgaoEmissorBusiness extends BasicBusiness<OrgaoEmissor>
{
	@Inject
	private OrgaoEmissorDAO repository;
	

	@Override
	public List<?> getAllActive() 
	{	
		return repository.getAllDTO(true, null);
	}
}
