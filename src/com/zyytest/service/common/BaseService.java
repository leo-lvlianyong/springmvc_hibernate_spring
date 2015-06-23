
package com.zyytest.service.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.zyytest.dao.common.BaseDao;
import com.zyytest.model.common.PageModel;

public class BaseService<T> {
	private BaseDao<T> dao;
	
	/**这个方法一定要在子类中调用，传入真实的Dao实例*/
	public void setDao(BaseDao<T> dao) {
		this.dao = dao;
	}
	
	public void add(T entity) {
		dao.add(entity);
	}
	public void update(T entity) {
		dao.update(entity);
		
	}
	public void delete(T entity) {
		dao.delete(entity);
	}
	public void delete(Serializable id) {
		dao.delete(id);
	}
	
	public T get(Serializable id){
		return dao.get(id);
	}
	public T load(Serializable id) {
		return dao.load(id);
	}
	
	public List<T> findAll(){
		return dao.findAll();
	}
	public List<T> findAll(String orderByProperty, boolean desc) {
		return dao.findAll(orderByProperty, desc);
	}
	
	public PageModel<T> findByPager() {
		return dao.findByPager();
	}
	public PageModel<T> findByPager(int pageNo, int pageSize, String orderByProperty, boolean desc) {
		return dao.findByPager(pageNo, pageSize, orderByProperty, desc);
	}
	
	public List<T> find(String hql, Object... values) {
		return dao.find(hql, values);
	}
	public PageModel<T> find(int pageNo, int pageSize, String hql, Object... values) {
		return dao.find(pageNo, pageSize, hql, values);
	}
	
	public List<T> find(String hql, Map<String,?> values) {
		return dao.find(hql, values);
	}
	public PageModel<T> find(int pageNo, int pageSize, String hql, Map<String,?> values) {
		return dao.find(pageNo, pageSize, hql, values);
	}
	
	public List<T> getBatch(Serializable ids){
		return dao.getBatch(ids);
	}
	
	public int batchExecute(String hql, Object... values){
		return dao.batchExecute(hql, values);
	}
}
