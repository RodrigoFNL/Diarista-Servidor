package conf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class BasicPersistence <E>
{	
	private EntityManager manager;		

	@SuppressWarnings("rawtypes")
	private Class entityClass;	

	@PostConstruct
	public void inicialize() 
	{
		if(this.manager == null)this.manager = DaoUtil.getEntityManager();
	}
	
	public abstract void changeClass();
	public void setEntityClass(Class<?> entityClass) 
	{
		this.entityClass = entityClass;
	}			

	public Boolean save(E entity)
	{	
		try
		{
			manager.persist(entity);
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean update(E entity)
	{
		try
		{
			manager.merge(entity);
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public E findById(String cpf)
	{	
		try
		{
			Object user = manager.find(entityClass, cpf);
			return (E) user;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}


	public EntityManager getManager() {
		return manager;
	}
	
	@SuppressWarnings("unchecked")
	public E findByColumn(String column, Object value)
	{					
		try
		{					
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT U.* FROM ").append(entityClass.getSimpleName()).append(" U WHERE ").append(column).append(" = :column");
		
			Query query = (Query) manager.createNativeQuery(sql.toString(), entityClass);			
			query.setParameter("column", value);			
			List<Object> objects = query.getResultList();			
			Object retorno = objects != null && objects.size() > 0? objects.get(0) : null;	
			return (E) retorno;			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
}










