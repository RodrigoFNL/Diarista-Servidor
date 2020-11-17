package br.com.diarista.adress.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diarista.adress.business.UFBusiness;
import br.com.diarista.adress.entity.UF;
import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.rest.BasicRestServe;

@RestController
@RequestMapping("/rest/uf")
public class UFRestService extends BasicRestServe<UF>
{
	@Autowired
	private UFBusiness business;
	
	@Override
	protected BasicBusiness<UF> business()
	{	
		return business;
	}
}
