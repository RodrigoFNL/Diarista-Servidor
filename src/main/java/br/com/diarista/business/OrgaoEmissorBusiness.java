package br.com.diarista.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.dao.OrgaoEmissorDAO;
import br.com.diarista.entity.OrgaoEmissor;

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
