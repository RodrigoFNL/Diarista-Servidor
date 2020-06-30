package dao;

import javax.annotation.PostConstruct;

import conf.BasicPersistence;
import dto.EstadoCivilDTO;
import model.EstadoCivil;

public class EstadoCivilDAO extends BasicPersistence<EstadoCivil, EstadoCivilDTO>
{
	@PostConstruct
	@Override
	public void changeClass() 
	{
		this.setEntityClass(EstadoCivil.class);		
	}

}
