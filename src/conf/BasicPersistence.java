package conf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.BasicEntity;

public abstract class BasicPersistence <E extends BasicEntity<D>, D>
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
	
	/**
	 * Busca uma Lista de DTOS no banco de dados
	 * @param status = Busca conforme o status informado [nulo buscar todos os objetos]
	 * @param orderby = Ordena uma lista, utilize a [ALIASE U] e o atributo conforme a ENTIDADE 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<?> getAllDTO(Boolean status, String orderby )
	{
		try
		{				
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT U FROM ").append(entityClass.getSimpleName()).append(" U ");
			
			if(status != null) sql.append("WHERE U.status = :status ");				
			if(orderby != null) sql.append(orderby);
			
			Query query = manager.createQuery(sql.toString());	
			
			if(status != null) query.setParameter("status", status);					
			List<E> objects = query.getResultList();			
						
			List<D> list = new ArrayList<D>();
			
			if(objects != null && objects.size() > 0)
			{
				for(E item : objects)
				{
					list.add(item.getDTO());
				}
			}			
			
			return list;
				
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<?> getAllDTO(String orderby)
	{
		return getAllDTO(null, orderby);
	}
}










