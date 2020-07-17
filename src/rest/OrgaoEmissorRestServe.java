package rest;

import javax.inject.Inject;
import javax.ws.rs.Path;

import business.BasicBusiness;
import business.OrgaoEmissorBusiness;
import model.OrgaoEmissor;

@Path("issuing_department")
public class OrgaoEmissorRestServe extends BasicRestServe<OrgaoEmissor>
{
	@Inject
	private OrgaoEmissorBusiness business;
	
	@Override
	protected BasicBusiness<OrgaoEmissor> business() 
	{		
		return business;
	}

}