package br.com.diarista.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diarista.business.BasicBusiness;
import br.com.diarista.business.ContratoBusiness;
import br.com.diarista.entity.Contrato;

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
