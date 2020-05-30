package conf;

import java.io.File;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class Application implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static String rootPath;
	
	@PersistenceContext(unitName = "diarista")
    private static EntityManager entityManager;
		
    public void init( @Observes @Initialized(ApplicationScoped.class) Object init ) 
    {      
    	getRootPath();
    }
    
    public static EntityManager getEntityManager()
    {
    	return entityManager;
    }    	
	
	//Cria um Arquivo sem nome no bin do wildfly apenas para pegar o path dele.
	public static String getRootPath() 
	{
		if (rootPath == null)
		{
			File currentDirFile = new File("");
			rootPath = currentDirFile.getAbsolutePath().replace("bin", "");
			if (!rootPath.endsWith("/") && !rootPath.endsWith("\\")) rootPath = rootPath + "/";					
		}
		return rootPath;
	}    
}
