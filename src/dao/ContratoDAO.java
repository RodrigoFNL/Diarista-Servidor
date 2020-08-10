package dao;

import javax.annotation.PostConstruct;

import conf.BasicPersistence;
import dto.ContratoDTO;
import model.Contrato;

public class ContratoDAO extends BasicPersistence<Contrato, ContratoDTO>
{
	@Override
	@PostConstruct
	public void changeClass()
	{
		setEntityClass(Contrato.class);
	}
}
