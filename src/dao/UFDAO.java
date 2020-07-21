package dao;

import javax.annotation.PostConstruct;

import conf.BasicPersistence;
import dto.UFDTO;
import model.UF;

public class UFDAO extends BasicPersistence<UF, UFDTO>
{
	@PostConstruct
	@Override
	public void changeClass()
	{
		setEntityClass(UF.class);
	}
}
