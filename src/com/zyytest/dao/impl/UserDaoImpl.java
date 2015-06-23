package com.zyytest.dao.impl;

import org.springframework.stereotype.Repository;

import com.zyytest.dao.UserDao;
import com.zyytest.dao.impl.common.BaseDaoImpl;
import com.zyytest.model.User;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	public User findByName(String username) {
		return this.findUniqueResult("from User where username=?", username);
	}
	
}
