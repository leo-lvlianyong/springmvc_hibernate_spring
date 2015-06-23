package com.zyytest.dao;

import com.zyytest.dao.common.BaseDao;
import com.zyytest.model.User;

public interface UserDao extends BaseDao<User>{

	public User findByName(String username);
}
