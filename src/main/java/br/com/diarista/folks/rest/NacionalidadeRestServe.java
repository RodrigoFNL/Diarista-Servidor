package br.com.diarista.folks.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.business.NacionalidadeBusiness;
import br.com.diarista.folks.entity.Nacionalidade;

@RestController
@RequestMapping("/rest/nationality")
public class NacionalidadeRestServe extends BasicRestServe<Nacionalidade>
{	
	@Autowired
	private NacionalidadeBusiness nacionalidadeBusiness;
	
	@Override
	protected BasicBusiness<Nacionalidade> business ()
	{
		return nacionalidadeBusiness;
	}

}
