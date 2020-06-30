package model;

import java.io.Serializable;

public abstract class BasicEntity<D> implements Serializable
{
	private static final long serialVersionUID = 1L;	
	
	
	public abstract D getDTO(); 

}
