package rest;

import javax.inject.Inject;
import javax.ws.rs.Path;

import business.BasicBusiness;
import business.NacionalidadeBusiness;
import model.Nacionalidade;

@Path("nationality")
public class NacionalidadeRestServe extends BasicRestServe<Nacionalidade, NacionalidadeBusiness>
{	
	@Inject
	private NacionalidadeBusiness nacionalidadeBusiness;
	
	@Override
	protected BasicBusiness<Nacionalidade, NacionalidadeBusiness> business ()
	{
		return nacionalidadeBusiness;
	}

}
