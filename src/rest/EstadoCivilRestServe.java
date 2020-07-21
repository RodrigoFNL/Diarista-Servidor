package rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import business.BasicBusiness;
import business.EstadoCivilBusiness;
import model.EstadoCivil;

@RequestScoped
@Path("marital_status")
public class EstadoCivilRestServe extends BasicRestServe<EstadoCivil> 
{
	@Inject
	private EstadoCivilBusiness estadoCivilBusiness;
	
	@Override
	protected BasicBusiness<EstadoCivil> business() 
	{		
		return estadoCivilBusiness;
	}
}
