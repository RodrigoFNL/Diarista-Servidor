package dao;

import javax.annotation.PostConstruct;

import conf.BasicPersistence;
import dto.NacionalidadeDTO;
import model.Nacionalidade;

public class NacionalidadeDAO extends BasicPersistence<Nacionalidade, NacionalidadeDTO> {

	@PostConstruct
	@Override
	public void changeClass() 
	{
		this.setEntityClass(Nacionalidade.class);		
	}
	
}
