package dao;


import javax.annotation.PostConstruct;

import conf.BasicPersistence;
import model.Usuario;

public class UsuarioDAO extends BasicPersistence<Usuario>
{
	
	@PostConstruct
	@Override
	public void changeClass() 
	{
		this.setEntityClass(Usuario.class);		
	}



	
}
