package br.com.diarista.folks.business;

import java.util.List;

import br.com.diarista.folks.entity.Usuario;

public abstract class BasicBusiness<E> 
{
	public abstract List<?> getAllActive();
	public abstract String register(E object, Usuario user);

}
