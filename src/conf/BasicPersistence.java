package conf;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

public class BasicPersistence
{
	private EntityManager manager;

	@PostConstruct
	public void inicialize() 
	{
		if(this.manager == null)this.manager = DaoUtil.getEntityManager();
	}

}
