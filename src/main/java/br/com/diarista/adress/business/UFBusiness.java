package br.com.diarista.adress.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.adress.dao.UFDAO;
import br.com.diarista.adress.entity.UF;
import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.utils.StringUtils;

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

	public UF getUFbySigla(String andressUf) 
	{				
		if(StringUtils.isNull(andressUf)) return null;		
		List<UF>  ufRecovery = repository.findBySigla(andressUf);
		if(ufRecovery != null && !ufRecovery.isEmpty()) return ufRecovery.get(0);
			
		UF uf = new UF();
		uf.setSigla(andressUf);
		uf.setName(andressUf);
		uf.setStatus(false);		
		repository.save(uf);
		return uf;				
	}

	@Override
	public String register(UF object, Usuario user)
	{
		return null;
	}
}
