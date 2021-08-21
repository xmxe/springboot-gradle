package com.xmxe.interceptor;

import com.xmxe.anno.MyOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomInterceptor implements HandlerInterceptor {

	Logger logger = LoggerFactory.getLogger(CustomInterceptor.class);

	/**
	 * 使用拦截器获取Controller方法名和注解信息?
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod) {
			HandlerMethod h = (HandlerMethod)handler;
			logger.info("h.getMethod()-{},h.getBeanType()-{},h.getBean()-{},h.getMethodParameters()-{},h.getReturnType()-{}",h.getMethod(),h.getBeanType(),h.getBean(),h.getMethodParameters(),h.getReturnType());
			System.out.println("用户想执行的操作是:"+h.getMethodAnnotation(MyOperation.class).value());
			//判断后执行操作...
		}
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
