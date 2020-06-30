package dao;


import javax.annotation.PostConstruct;

import conf.BasicPersistence;
import dto.UsuarioDTO;
import model.Usuario;

public class UsuarioDAO extends BasicPersistence<Usuario, UsuarioDTO>
{
	
	@PostConstruct
	@Override
	public void changeClass() 
	{
		this.setEntityClass(Usuario.class);		
	}	
}
