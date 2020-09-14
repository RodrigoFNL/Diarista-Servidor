package br.com.diarista.business;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.diarista.entity.Endereco;

@Service
public class EnderecoBusiness extends BasicBusiness<Endereco>
{
	@Override
	public List<?> getAllActive()
	{		
		return null;
	}
}
