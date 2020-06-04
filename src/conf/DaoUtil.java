package conf;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DaoUtil 
{		
	private static EntityManagerFactory factory;
	
	static
	{
		if(factory == null)  factory = Persistence.createEntityManagerFactory("diarista");
	}
	
	public static EntityManager getEntityManager()
	{
		return factory.createEntityManager();
	}
}
