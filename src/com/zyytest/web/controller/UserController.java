package com.zyytest.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zyytest.model.User;
import com.zyytest.model.common.PageModel;
import com.zyytest.service.UserService;
/**
 * 用户管理的控制器
 * @author leo_lvlianyong
 * 2013-6-8
 */
@Controller      //表示这是一个控制器类
@RequestMapping(value="/user")  //表示控制器会获取所有以user/开头的请求
public class UserController {
	private UserService userService;
	
	//页面请求为:user/list
	@RequestMapping(value="/list")  
	public String list(Model model){   
		PageModel<User> pm = userService.findByPager();
		model.addAttribute("pm",pm);
		return "user/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		model.addAttribute(new User());
		return "user/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Validated User user,BindingResult br){
		if(br.hasErrors()){
			return "user/add";
		}
		userService.adds(user);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/{id}/update",method=RequestMethod.GET)
	public String update(@PathVariable int id,Model model){
		User user = userService.get(id);
		model.addAttribute(user);
		return "user/update";
	}
	
	@RequestMapping(value="/{id}/update",method=RequestMethod.POST)
	public String update(@PathVariable int id,@Validated User user,BindingResult br){
		if(br.hasErrors()) {
			return "user/update";
		}
		User u = userService.get(id);
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u.setNickname(user.getNickname());
		u.setEmail(user.getEmail());
		userService.update(u);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/{id}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable int id) {
		userService.delete(id);
		return "redirect:/user/list";
	}
	
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


}
