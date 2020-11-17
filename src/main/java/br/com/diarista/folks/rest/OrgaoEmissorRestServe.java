package br.com.diarista.folks.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.business.OrgaoEmissorBusiness;
import br.com.diarista.folks.entity.OrgaoEmissor;

@RestController
@RequestMapping("/rest/issuing_department")
public class OrgaoEmissorRestServe extends BasicRestServe<OrgaoEmissor>
{
	@Autowired
	private OrgaoEmissorBusiness business;
	
	@Override
	protected BasicBusiness<OrgaoEmissor> business() 
	{		
		return business;
	}

}
