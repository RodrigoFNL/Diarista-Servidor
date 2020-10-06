package br.com.diarista.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.dao.LocalidadeDAO;
import br.com.diarista.entity.Endereco;
import br.com.diarista.entity.Localidade;

@Service
public class EnderecoBusiness extends BasicBusiness<Endereco>
{
	@Autowired
	LocalidadeDAO localityRepository;
	
	@Override
	public List<?> getAllActive()
	{		
		return null;
	}

	public Localidade getLocalityfindByCep(String cep) 
	{	
		Optional<Localidade> optional = localityRepository.findByCep(cep);		
		if(!(optional != null && !optional.isEmpty())) return null;		
		return optional.get();
	}
}
