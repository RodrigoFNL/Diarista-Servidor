package dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import conf.Application;
import util.StringUtil;

public abstract class PersistenceBasic<T>
{	
    private EntityManager entityManager;
	
	public EntityManager getEntityManager() 
	{
		return entityManager;
	}

	public static String DEFAULT_ORDER = "id";
	private Class<T> entityClass;	
	private String defaultOrder;
		
	public abstract void inicialize();
	
	@PostConstruct
    public void init() 
	{
		entityManager = Application.getEntityManager();
    }
	    
    
    public int getCountModels() throws Exception {
    	int cont = getCountObjs();
        return cont;
    }
		
	public int getCountObjs() throws Exception {
		verificaEntity();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
		criteria.select(cb.count(criteria.from(entityClass)));
		return entityManager.createQuery(criteria).getSingleResult().intValue();
    }
	
	public List<T> getObjs() throws Exception {
    	return findAllOrderedByNome(getDefaultOrder());
    }
    
	protected T find(Object arg1) throws Exception {
		verificaEntity();
        return (T) entityManager.find(entityClass, arg1);
    }
    
	public List<T> findAll() throws Exception  {
		verificaEntity();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(entityClass);
		criteria.select(criteria.from(entityClass));
        return entityManager.createQuery(criteria).getResultList();
    }
 
    public T findById(Long id) throws Exception {
    	verificaEntity();
    	return entityManager.find(entityClass, id);
    }
    
    public T findById(Class<T> entityClass, Long id) throws Exception {
    	verificaEntity();
    	return entityManager.find(entityClass, id);
    }
    
    public T findById(BigInteger id) throws Exception {
    	verificaEntity();
    	return entityManager.find(entityClass, id);
    }
    

	public List<T> findAllOrderedByNome(String column) throws Exception {
		verificaEntity();
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    	CriteriaQuery<T> criteria = cb.createQuery(entityClass);
    	Root<T> entity = criteria.from(entityClass);
    	criteria.select(entity);
    	criteria.orderBy(cb.asc(entity.get(column)));
        return entityManager.createQuery(criteria).getResultList();
    }
	
	
	public List<T> findAllByColumn(String column, Object value) throws Exception {
		verificaEntity();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<T> criteria = cb.createQuery(entityClass);
	    Root<T> entity = criteria.from(entityClass);
	    criteria.select(entity).where(cb.equal(entity.get(column), value));
        return entityManager.createQuery(criteria).getResultList();
    }
	
	public List<T> findAllByColumnOrder(String column, Object value, String order) throws Exception {
		verificaEntity();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<T> criteria = cb.createQuery(entityClass);
	    Root<T> entity = criteria.from(entityClass);
	    criteria.select(entity).where(cb.equal(entity.get(column), value));
	    criteria.orderBy(cb.asc(entity.get(order)));
        return entityManager.createQuery(criteria).getResultList();
    }
	
	//busca por colunas com atributos em long
	public T findByColumn(String column, Long attribute, Class<T> classModel)   
	{
		if(attribute == null) return null;			
		entityClass = classModel;
		try
		{
	 	verificaEntity();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(entityClass);
        Root<T> entity = criteria.from(entityClass);
        criteria.select(entity).where(cb.equal(entity.get(column), attribute));        
        return entityManager.createQuery(criteria).getSingleResult();
		}
		catch (Exception e) 
		{
			return null;
		}
	}
    
    public T findByColumn(String column, String value) throws Exception {
    	verificaEntity();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(entityClass);
        Root<T> entity = criteria.from(entityClass);
        criteria.select(entity).where(cb.equal(entity.get(column), value));
        return entityManager.createQuery(criteria).getSingleResult();
    }
    
    @SuppressWarnings("unchecked")
   public T findByColumn(String column, String value, Class<?> classEntity) throws Exception 
    {
       setEntityClass((Class<T>) classEntity);
   		verificaEntity();
       CriteriaBuilder cb = entityManager.getCriteriaBuilder();
       CriteriaQuery<T> criteria = cb.createQuery(entityClass);
       Root<T> entity = criteria.from(entityClass);
       criteria.select(entity).where(cb.equal(entity.get(column), value));
       return entityManager.createQuery(criteria).getSingleResult();
   }
 
    public void detach(Object arg1) throws Exception {
    	verificaEntity();
        entityManager.detach(arg1);
    }
    
    public Object merge(Object arg1) throws Exception {
    	verificaEntity();
        entityManager.merge(arg1);
        return arg1;
    }
    
    public void save(Object arg1) throws Exception { 
    	verificaEntity();
        entityManager.persist(arg1);
    }
    
    public void update(Object arg1) throws Exception {
    	verificaEntity();
        entityManager.merge(arg1);
        entityManager.flush();
    }
    
    public void deletar(Object arg1) throws Exception {
    	verificaEntity();
        arg1 = entityManager.merge(arg1);
        entityManager.remove(arg1);
        entityManager.flush();
    }
    
//    public void saveHis(Object arg1, Historico.Classes cl, Usuario usuario) throws Exception { 
//    	verificaEntity();
//        entityManager.persist(arg1);
//        saveHistorico(Permissao.Acoes.INSERIR.getValue(), cl.getValue(), ((ModelEntity)arg1).getId(), usuario, ((ModelEntity)arg1).getAuditoria());
//    }
//    
//    public void updateHis(Object arg1, Historico.Classes cl, Usuario usuario) throws Exception {
//    	verificaEntity();
//        entityManager.merge(arg1);
//        entityManager.flush();
//        saveHistorico(Permissao.Acoes.EDITAR.getValue(), cl.getValue(), ((ModelEntity)arg1).getId(), usuario, ((ModelEntity)arg1).getAuditoria());
//    }
//    
//    public void deletarHis(Object arg1, Historico.Classes cl, Usuario usuario) throws Exception {
//    	verificaEntity();
//        arg1 = entityManager.merge(arg1);
//        entityManager.remove(arg1);
//        entityManager.flush();
//        saveHistorico(Permissao.Acoes.EXCLUIR.getValue(), cl.getValue(), ((ModelEntity)arg1).getId(), usuario, ((ModelEntity)arg1).getAuditoria());
//    }
    
    public Query createQueryJPAQL(String JPAQL) throws Exception {
    	verificaEntity();
        return entityManager.createQuery(JPAQL);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
   public Query createQueryJPAQL(String JPAQL, Class entity) throws Exception 
    {
   		verificaEntity();   	
   		return entityManager.createQuery(JPAQL, entity);
   }
    
    @SuppressWarnings("unchecked")
	public List<T> findAllObjectJPAQL(String JPAQL, Map<String, Object> parameters) throws Exception {
    	List<T> result = null;
        Query query = createQueryJPAQL(JPAQL);     
        if (parameters != null && !parameters.isEmpty()) {
            populateQueryParameters(query, parameters);
        }     
        result = query.getResultList();
        return result;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAllObjectJPAQL(String JPAQL, Map<String, Object> parameters, Class entity) throws Exception {
    	List<T> result = null;
        Query query = createQueryJPAQL(JPAQL, entity);     
        if (parameters != null && !parameters.isEmpty()) {
            populateQueryParameters(query, parameters);
        }     
        result = query.getResultList();
        return result;
    }
    
    
    @SuppressWarnings("unchecked")
	public List<T> findAllObjectJPAQL(String JPAQL, Map<String, Object> parameters, Integer max) throws Exception {
    	List<T> result = null;
        Query query = createQueryJPAQL(JPAQL);
     
        if (parameters != null && !parameters.isEmpty()) {
            populateQueryParameters(query, parameters);
        }
            
       query.setFirstResult(0);
       query.setMaxResults(max);
     
        result = query.getResultList();

        return result;
    }
    
    public Object findOneObjectJPAQL(String JPAQL, Map<String, Object> parameters) throws Exception {
    	Object result = null;
        Query query = createQueryJPAQL(JPAQL);
     
        if (parameters != null && !parameters.isEmpty()) {
            populateQueryParameters(query, parameters);
        }
    
        result = query.getSingleResult();

        return result;
    }
	
    public Object findOneObjectJPAQL(String JPAQL, Map<String, Object> parameters, Class<T> classEntity) throws Exception 
    {
       setEntityClass(classEntity);
       Query query = createQueryJPAQL(JPAQL);
    
       if (parameters != null && !parameters.isEmpty())
       {
           populateQueryParameters(query, parameters);
       }
   
       Object result = query.getSingleResult();
       return result;
   }
	
    public Object findOneObject(String namedQuery, Map<String, Object> parameters) throws Exception {
    	verificaEntity();
	    Object result = null;
        Query query = entityManager.createNamedQuery(namedQuery);
 
        if (parameters != null && !parameters.isEmpty()) {
            populateQueryParameters(query, parameters);
        }
 
        result = (Object) query.getSingleResult();

        return result;
    }
    
    public void executeUpdateJPAQL(String JPAQL, Map<String, Object> parameters) throws Exception {
        Query query = createQueryJPAQL(JPAQL);
     
        if (parameters != null && !parameters.isEmpty()) {
            populateQueryParameters(query, parameters);
        }
    
        query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
	public List<T> findAllObject(String namedQuery, Map<String, Object> parameters) throws Exception {
    	verificaEntity();
    	List<T> result = null;
        Query query = entityManager.createNamedQuery(namedQuery);
     
        if (parameters != null && !parameters.isEmpty()) {
            populateQueryParameters(query, parameters);
        }
     
        result = query.getResultList();

        return result;
    }
    
   
	public Integer executeUpdateNative(String sqlString, Map<String, Object> parameters) throws Exception {
		verificaEntity();
        Query query = entityManager.createNativeQuery(sqlString);

        if (parameters != null && !parameters.isEmpty()) {
            populateQueryParameters(query, parameters);
        }
     
        return query.executeUpdate();
    }
		
	public SQLQuery getSQLQuery(String sql) throws Exception 
	{
		verificaEntity();
		Session session = (Session) entityManager.getDelegate();     
	    return session.createSQLQuery(sql);	
	}
	
	public Integer executCountNative(String sqlString, Map<String, Object> parameters) throws Exception {
		verificaEntity();
    	Query query = entityManager.createNativeQuery(sqlString);

        if (parameters != null && !parameters.isEmpty()) {
            populateQueryParameters(query, parameters);
        }
        Integer result = 0;
        		
        Object value = query.getSingleResult();
        
        if (value instanceof BigInteger) {
        	BigInteger vl = (BigInteger) query.getSingleResult();
        	result = vl.intValue();
        } else {
        	result = (Integer) query.getSingleResult();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
	public Boolean existRecord(String namedQuery, Map<String, Object> parameters) throws Exception {
    	verificaEntity();
    	List<T> result = null;
    	Boolean exist = false;
        Query query = entityManager.createNamedQuery(namedQuery);
 
        if (parameters != null && !parameters.isEmpty()) {
            populateQueryParameters(query, parameters);
        }
        
        query.setMaxResults(1);
 
        result = query.getResultList();
        
        if (result != null && !result.isEmpty()) {
        	exist = true;
        }

        return exist;
    }	
	
    public void populateQueryParameters(Query query, Map<String, Object> parameters) {
        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
    
    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
//    private void saveHistorico(int id_acao, int id_classe, long id_object, Usuario usuario, String st_descricao) throws Exception { 
//    	verificaEntity();
//    	Historico historico = new Historico();
//    	historico.setId_acao(id_acao);
//    	historico.setId_classe(id_classe);
//    	historico.setId_object(id_object);
//    	historico.setId_usuario(usuario.getId());
//    	historico.setDescricao(st_descricao);
//    	historico.setSt_usuario(usuario.getNome());
//    	historico.setDt_cadastro(new Timestamp(System.currentTimeMillis()));
//        entityManager.persist(historico);
//    }

	public String getDefaultOrder() {
		if (StringUtil.isEmpty(defaultOrder)) {
			defaultOrder = DEFAULT_ORDER;
		}
		return defaultOrder;
	}

	public void setDefaultOrder(String defaultOrder) {
		this.defaultOrder = defaultOrder;
	}
	
	private void verificaEntity() {
		if (entityManager == null) {
			entityManager = Application.getEntityManager();
		}
	}    
	
}
