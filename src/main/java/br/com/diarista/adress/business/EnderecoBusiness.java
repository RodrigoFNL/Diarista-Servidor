package br.com.diarista.adress.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.adress.dao.LocalidadeDAO;
import br.com.diarista.adress.dao.UFDAO;
import br.com.diarista.adress.dto.LocalidadeDTO;
import br.com.diarista.adress.entity.Endereco;
import br.com.diarista.adress.entity.Localidade;
import br.com.diarista.adress.entity.UF;
import br.com.diarista.folks.business.BasicBusiness;

@Service
public class EnderecoBusiness extends BasicBusiness<Endereco>
{
	@Autowired
	private LocalidadeDAO localityRepository;
	
	@Autowired
	private UFDAO ufRepository;
	
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
	
	public Localidade getEntity(LocalidadeDTO dto) 
	{
		if(dto == null)			 return null;
		if(dto.getUf() == null)  return null;		
		List<UF> ufs = ufRepository.findBySigla(dto.getUf());
		
		UF uf = null;
		
		if(!(ufs != null && !ufs.isEmpty()))
		{
			uf = new UF();
			uf.setName(dto.getUf());
			uf.setSigla(dto.getUf());
			uf.setStatus(false);			
			ufRepository.save(uf);				
		}
		else  uf = ufs.get(0);
		
		Localidade entity = new Localidade();
		entity.setBairro(dto.getBairro());
		entity.setCep(dto.getCep());
		entity.setComplemento(dto.getComplemento());
		entity.setIbge(dto.getIbge());
		entity.setLocalidade(dto.getLocalidade());
		entity.setLogradouro(dto.getLogradouro());
		entity.setUf(uf);		
		return entity;
	}
}
