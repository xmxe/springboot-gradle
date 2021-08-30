package com.xmxe.component;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 注解式拦截器
 * 很多时候，我们希望某个接口下的某些http请求执行统一的拦截处理逻辑。为了支持这个功能，retrofit-spring-boot-starter提供了注解式拦截器
 * 做到了基于url路径的匹配拦截 。使用的步骤主要分为2步：
 * 1.继承BasePathMatchInterceptor编写拦截处理器
 * 2.接口上使用@Intercept进行标注。如需配置多个拦截器，在接口上标注多个@Intercept注解即可！
 */
@Component
public class TimeStampInterceptor extends BasePathMatchInterceptor {

	// 匹配http请求后增加timestamp查询参数
	@Override
	public Response doIntercept(Chain chain) throws IOException {
		Request request = chain.request();
		HttpUrl url = request.url();
		long timestamp = System.currentTimeMillis();
		HttpUrl newUrl = url.newBuilder()
				.addQueryParameter("timestamp", String.valueOf(timestamp))
				.build();
		Request newRequest = request.newBuilder()
				.url(newUrl)
				.build();
		return chain.proceed(newRequest);
	}
}