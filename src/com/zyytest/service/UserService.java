package com.zyytest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyytest.dao.UserDao;
import com.zyytest.model.User;
import com.zyytest.model.UserException;
import com.zyytest.service.common.BaseService;
@Service
public class UserService extends BaseService<User>{
	
	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		super.setDao(this.userDao);
	}
	
	public void adds(User user){
		User u = userDao.findByName(user.getUsername());
		if(u!=null) throw new UserException("要添加的用户已经存在");
		userDao.add(user);
	}
	
	public User login(String username, String password) {
		User u = userDao.findByName(username);
		if(u==null) throw new UserException("登录用户不存在");
		if(!u.getPassword().equals(password)) throw new UserException("用户密码不正确");
		return u;
	}
	
}
