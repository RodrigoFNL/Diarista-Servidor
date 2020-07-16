package dao;

import javax.annotation.PostConstruct;

import conf.BasicPersistence;
import dto.OrgaoEmissorDTO;
import model.OrgaoEmissor;

public class OrgaoEmissorDAO extends BasicPersistence<OrgaoEmissor, OrgaoEmissorDTO>
{

	@PostConstruct
	@Override
	public void changeClass()
	{
		setEntityClass(OrgaoEmissor.class);
	}

}
