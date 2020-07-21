package rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import business.BasicBusiness;
import business.UFBusiness;
import model.UF;

@RequestScoped
@Path("uf")
public class UFRestService extends BasicRestServe<UF>
{
	@Inject
	private UFBusiness business;
	
	@Override
	protected BasicBusiness<UF> business()
	{	
		return business;
	}
}
