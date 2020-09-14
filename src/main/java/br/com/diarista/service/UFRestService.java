package br.com.diarista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diarista.business.BasicBusiness;
import br.com.diarista.business.UFBusiness;
import br.com.diarista.entity.UF;

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
