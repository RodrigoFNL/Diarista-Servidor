package rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import business.BasicBusiness;
import business.NacionalidadeBusiness;
import model.Nacionalidade;

@RequestScoped
@Path("nationality")
public class NacionalidadeRestServe extends BasicRestServe<Nacionalidade>
{	
	@Inject
	private NacionalidadeBusiness nacionalidadeBusiness;
	
	@Override
	protected BasicBusiness<Nacionalidade> business ()
	{
		return nacionalidadeBusiness;
	}

}
