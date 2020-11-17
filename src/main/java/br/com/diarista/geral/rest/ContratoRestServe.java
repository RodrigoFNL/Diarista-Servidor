package br.com.diarista.geral.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.rest.BasicRestServe;
import br.com.diarista.geral.business.ContratoBusiness;
import br.com.diarista.geral.entity.Contrato;

@RestController
@RequestMapping("/rest/contract")
public class ContratoRestServe extends BasicRestServe<Contrato>
{
	@Autowired
	private ContratoBusiness business;

	@Override
	protected BasicBusiness<Contrato> business()
	{	
		return business;
	}

}
