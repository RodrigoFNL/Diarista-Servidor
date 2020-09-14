package br.com.diarista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diarista.business.BasicBusiness;
import br.com.diarista.business.EstadoCivilBusiness;
import br.com.diarista.entity.EstadoCivil;

@RestController
@RequestMapping("/rest/marital_status")
public class EstadoCivilRestServe extends BasicRestServe<EstadoCivil> 
{
	@Autowired
	private EstadoCivilBusiness estadoCivilBusiness;
	
	@Override
	protected BasicBusiness<EstadoCivil> business() 
	{		
		return estadoCivilBusiness;
	}
}
