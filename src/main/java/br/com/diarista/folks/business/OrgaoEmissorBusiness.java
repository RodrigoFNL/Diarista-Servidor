package br.com.diarista.folks.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.folks.dao.OrgaoEmissorDAO;
import br.com.diarista.folks.entity.OrgaoEmissor;

@Service
public class OrgaoEmissorBusiness extends BasicBusiness<OrgaoEmissor>
{
	@Autowired
	private OrgaoEmissorDAO repository;
	

	@Override
	public List<OrgaoEmissor> getAllActive() 
	{	
		return repository.findByStatus(true);
	}
}
