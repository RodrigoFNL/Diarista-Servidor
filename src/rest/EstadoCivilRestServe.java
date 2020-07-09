package rest;

import javax.inject.Inject;
import javax.ws.rs.Path;

import business.BasicBusiness;
import business.EstadoCivilBusiness;
import model.EstadoCivil;

@Path("marital_status")
public class EstadoCivilRestServe extends BasicRestServe<EstadoCivil, EstadoCivilBusiness> 
{
	@Inject
	private EstadoCivilBusiness estadoCivilBusiness;
	
	@Override
	protected BasicBusiness<EstadoCivil, EstadoCivilBusiness> business() 
	{		
		return estadoCivilBusiness;
	}
}
