package cn.jing.concurrency;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.jing.concurrency.example.threadLocal.RequestHolder;

import java.io.IOException;

/**
 * function:过滤器 (利用ThreadLocal实例保存登录用户信息)
 * 
 * @author liangjing
 *
 */
@Slf4j
public class HttpFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		log.info("do filter, {}, {}", Thread.currentThread().getId(), request.getServletPath());
		// 通过filter过滤器来存放数据
		RequestHolder.add(Thread.currentThread().getId());
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
