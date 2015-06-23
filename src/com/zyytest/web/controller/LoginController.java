package com.zyytest.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.zyytest.model.User;
import com.zyytest.service.UserService;
/**
 * 登录处理的控制器
 * @author leo_lvlianyong
 * 2013-06-08
 */
@Controller
@SessionAttributes("loginUser")
public class LoginController {
	
	private UserService userService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return "login"; // 这个名字决定了login.jsp的名字
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,Model model){
		User u = userService.login(username, password);
		model.addAttribute("loginUser", u);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/logout")
	public String logout(Model model,HttpSession session){
		model.asMap().remove("loginUser");
		session.invalidate();
		return "redirect:/login";
	}
	

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
}
