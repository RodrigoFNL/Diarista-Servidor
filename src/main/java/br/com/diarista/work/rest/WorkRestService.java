package br.com.diarista.work.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.rest.BasicRestServe;
import br.com.diarista.work.business.WorkBusiness;
import br.com.diarista.work.entity.Work;

@RestController
@RequestMapping("/rest/work")
public class WorkRestService extends BasicRestServe<Work>
{
	@Autowired
	private WorkBusiness business;	
	
	@Override
	protected BasicBusiness<Work> business()
	{		
		return business;
	}	
}
