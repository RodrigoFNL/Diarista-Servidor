package rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import business.BasicBusiness;
import business.ContratoBusiness;
import model.Contrato;

@RequestScoped
@Path("contract")
public class ContratoRestServe extends BasicRestServe<Contrato>
{
	@Inject
	private ContratoBusiness business;

	@Override
	protected BasicBusiness<Contrato> business()
	{	
		return business;
	}

}
