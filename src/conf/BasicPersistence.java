package conf;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

public abstract class BasicPersistence <T>
{	
	private EntityManager manager;	

	@PostConstruct
	public void inicialize() 
	{
		if(this.manager == null)this.manager = DaoUtil.getEntityManager();
	}

	public Boolean save(Object objeto)
	{	
		try
		{
			manager.persist(objeto);
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public EntityManager getManager() {
		return manager;
	}
}
