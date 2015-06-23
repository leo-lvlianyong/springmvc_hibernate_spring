package com.zyytest.web.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.zyytest.model.SystemContext;

/**
 * 处理分页的过滤器，接收pagesize和offset两个参数，并设置到SystemContext相应的属性中
 * @author leo_lvlianyong
 *
 */
public class SystemContextFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		int offset = 0;
		try {
			offset = Integer.parseInt(req.getParameter("pager.offset"));
		} catch (NumberFormatException e) {
		}
		try {
			//起始页
			SystemContext.setOffset(offset);
			//一页显示多少条
			SystemContext.setSize(3);
			chain.doFilter(req, resp);
		}finally {
			SystemContext.removeOffset();
			SystemContext.removeSize();
		}
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
