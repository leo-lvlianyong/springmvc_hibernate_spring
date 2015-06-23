
package com.zyytest.dao.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zyytest.model.common.PageModel;

/**
 * 通用泛型Dao接口
 */
public interface BaseDao<T> {
	public void add(T entity) ;
	public void update(T entity) ;
	public void delete(T entity) ;
	public void delete(Serializable id) ;
	
	public T get(Serializable id) ;
	public T load(Serializable id) ;
	
	public long countResult() ;
	public int countHqlResult(String hql, Object... values) ;


	public List<T> findAll() ;
	public List<T> findAll(String orderByProperty, boolean desc) ;
	
	public PageModel<T> findByPager() ;
	public PageModel<T> findByPager(int pageNo, int pageSize, String orderByProperty, boolean desc) ;
	
	public List<T> find(String hql, Object... values) ;
	public PageModel<T> find(int pageNo, int pageSize, String hql, Object... values) ;
	public List<Object[]> find2(String hql, Object... values);
	
	public T findUniqueResult(String hql, Object... values) ;
	public T findUniqueResult(String hql, Map<String,?> values) ;
	
	public List<T> find(String hql, Map<String,?> values) ;
	public PageModel<T> find(int pageNo, int pageSize, String hql, Map<String,?> values) ;
	
	public int batchExecute(String hql, Object... values) ;
	public List<T> getBatch(Serializable... ids) ;
}
