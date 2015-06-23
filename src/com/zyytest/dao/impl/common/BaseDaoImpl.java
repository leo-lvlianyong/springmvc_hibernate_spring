
package com.zyytest.dao.impl.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zyytest.dao.common.BaseDao;
import com.zyytest.model.SystemContext;
import com.zyytest.model.common.PageModel;

/**
 * 使用Hibernate实现的泛型通用Dao
 */
@SuppressWarnings("unchecked")
@Repository
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	/** 当前操作到的实体类的类型信息实例 */
	protected Class<T> clazz;
	protected Serializable oid;
	
	/** Session工厂 */
	protected SessionFactory sessionFactory;
	
	/**构造方法*/
	public BaseDaoImpl(){
		//通过反射机制获取T的类型信息
		ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
		this.clazz = (Class)type.getActualTypeArguments()[0];
	}
	
	/** 取得SessionFactory对象 */
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		
		this.oid = this.getId();
	}
	
	/** 取得当前Session */
	protected Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	////////////////////////////// CRUD操作 //////////////////////////////////////
	
	/* (non-Javadoc)
	 * @see com.tjitcast.dao.base.GenericDao#add(java.lang.Object)
	 */
	public void add(T entity) {
		getSession().save(entity);
		
	}
	
	/* (non-Javadoc)
	 * @see com.tjitcast.dao.base.GenericDao#delete(java.lang.Object)
	 */
	public void delete(T entity) {
		getSession().delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.tjitcast.dao.base.GenericDao#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		getSession().delete(getSession().load(clazz, id));
	}
	
	/* (non-Javadoc)
	 * @see com.tjitcast.dao.base.GenericDao#update(java.lang.Object)
	 */
	public void update(T entity) {
		getSession().update(entity);
		getSession().merge(entity);

	}

	/* (non-Javadoc)
	 * @see com.tjitcast.dao.base.GenericDao#get(java.io.Serializable)
	 */
	public T get(Serializable id) {
		return (T)getSession().get(clazz, id);
	}
	
	/* (non-Javadoc)
	 * @see com.tjitcast.dao.base.GenericDao#load(java.io.Serializable)
	 */
	public T load(Serializable id) {
		return (T)getSession().load(clazz, id);
	}
	
	public long countResult() {
		long result = 0;
		Long count = (Long)createQuery("select count(t) from "+ clazz.getSimpleName() + " t").uniqueResult();
		if(count != null){
			result = count.longValue();
		}
		return result;
	}

	public int countHqlResult(String hql, Object... values) {
		int result = 0;
		result = Integer.parseInt(createQuery(hql, values).uniqueResult().toString());
		return result;
	}
	
	


	/* (non-Javadoc)
	 * @see com.tjitcast.dao.base.GenericDao#getAll()
	 */
	public List<T> findAll() {
		return  createCriteria().list();
	}
	
	public List<T> findAll(String orderByProperty, boolean desc){
		Criteria c = createCriteria();
		if(desc){
			c.addOrder(Order.desc(orderByProperty));
		}else{
			c.addOrder(Order.asc(orderByProperty));
		}
		return c.list();
	}

	/* (non-Javadoc)
	 * @see com.tjitcast.dao.base.GenericDao#getByPager(int, int)
	 */
	public PageModel<T> findByPager() {
		int pageSize = SystemContext.getSize();
		int pageNo = SystemContext.getOffset();
		PageModel<T> pm = new PageModel<T>();
		pm.setPageNo(pageNo);
		pm.setPageSize(pageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("select count(").append(oid).append(") from ").append(clazz.getSimpleName());
			Long count = (Long)createQuery(sb.toString()).uniqueResult();
			
			if(count !=null && count.intValue() > 0){
				pm.setRecordCount(count.intValue());
				List<T> list = createCriteria()
								   .setMaxResults(pageSize)
								   .setFirstResult(pageNo)
								   .list();
				pm.setDatas(list);
			}
		
		return pm;
	}
	
	public PageModel<T> findByPager(int pageNo, int pageSize, String orderByProperty, boolean desc){
		PageModel<T> pm = new PageModel<T>();
		pm.setPageNo(pageNo);
		pm.setPageSize(pageSize);
		StringBuilder sb = new StringBuilder();
		sb.append("select count(").append(oid).append(") from ").append(clazz.getSimpleName());
			Long count = (Long)createQuery(sb.toString()).uniqueResult();
			
			if(count !=null && count.intValue() > 0){
				pm.setRecordCount(count.intValue());
				
				Criteria c = createCriteria();
				if(desc){
					c.addOrder(Order.desc(orderByProperty));
				}else{
					c.addOrder(Order.asc(orderByProperty));
				}
				List<T> list = c.setMaxResults(pageSize)
								   .setFirstResult((pageNo - 1)*pageSize)
								   .list();
				
				pm.setDatas(list);
			}
		return pm;
	}
	
	public List<T> find(String hql, Object... values) {
		return  createQuery(hql, values).list();
	}
	
	public List<Object[]> find2(String hql, Object... values){
		return createQuery(hql, values).list();
	}
	
	public List<T> find(String hql, Map<String,?> values) {
			return createQuery(hql, values).list();
	}
	public PageModel<T> find(int pageNo, int pageSize, String hql, Map<String,?> values) {
		PageModel<T> pm = new PageModel<T>();
		pm.setPageNo(pageNo);
		pm.setPageSize(pageSize);
		
		StringBuilder sb = new StringBuilder(hql);
		sb.delete(0, sb.indexOf("from")+4)
			.delete(sb.indexOf(" order by"),sb.length())
			.insert(0, ")")
			.insert(0, oid)
			.insert(0, "select count(");
			Long count = (Long)createQuery(sb.toString(), values).uniqueResult();
			
			if(count !=null && count.intValue() > 0){
				pm.setRecordCount(count.intValue());
			
				List<T> list = createQuery(hql, values)
									.setMaxResults(pageSize)
									.setFirstResult((pageNo - 1)*pageSize)
									.list();
				
				pm.setDatas(list);
			}
	
		
		return pm; 
	}
	
	
	public PageModel<T> find(int pageNo, int pageSize, String hql, Object... values) {
		PageModel<T> pm = new PageModel<T>();
		pm.setPageNo(pageNo);
		pm.setPageSize(pageSize);
		
		StringBuilder sb = new StringBuilder(hql);
		sb.delete(0, sb.indexOf("from"))
			.insert(0, "select count(*) ");
			Long count = (Long)createQuery(sb.toString(), values).uniqueResult();
			
			if(count !=null && count.intValue() > 0){
				pm.setRecordCount(count.intValue());
			
				List<T> list = createQuery(hql, values)
									.setMaxResults(pageSize)
									.setFirstResult((pageNo - 1)*pageSize)
									.list();
				
				pm.setDatas(list);
			}
		return pm; 
	}
	
	public T findUniqueResult(String hql, Object... values) {
		return  (T)createQuery(hql, values).uniqueResult();
	}
	public T findUniqueResult(String hql, Map<String,?> values) {
		return (T)createQuery(hql, values).uniqueResult();
	}
	
	////////////////////////////// 批量操作  //////////////////////////////////////
	public List<T> getBatch(Serializable... ids) {
		int length = ids == null ? 0 : ids.length;
		StringBuilder sb = new StringBuilder("from ");
		sb.append(clazz.getSimpleName());
		for(int i = 0 ; i < length; i++){
				if(i == 0){
					sb.append(" where ").append(oid).append(" in(");
				}
				if(i > 0){
					sb.append(",");
				}
				sb.append(ids[i]);
				if(i == length -1){
					sb.append(")");
				}
			}
			return createQuery(sb.toString()).list();
	}

	
	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @param values 命名参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(String hql, Object... values) {
		return createQuery(hql, values).executeUpdate();
	}
	
	///////////////////////////////一些公用方法////////////////////////////////////
	/** 获取当前实体类对象的标识符名 */
	protected String getId(){
		return getSessionFactory().getClassMetadata(clazz).getIdentifierPropertyName();
	}
	/**
	 * 初始化延迟加载的对象
	 * @param 延迟加载的对象
	 */
	protected void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}
	
	/** 刷新当前的Session */
	protected void flush(){
		getSession().flush();
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * @param hql HQL语句
	 * @param values 命名参数,按名称绑定.
	 */
	protected Query createQuery(String hql, Map<String, ?> values) {
		Query query = getSession().createQuery(hql);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * @param hql HQL语句
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	protected Query createQuery(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/**
	 * 根据Criterion条件创建Criteria.
	 * @param criterions 数量可变的Criterion.
	 */
	protected Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(clazz);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @param values 命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	protected int batchExecute(String hql, Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}
	
	
}
