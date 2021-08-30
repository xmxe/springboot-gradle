package com.xmxe.component;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BaseGlobalInterceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 全局应用拦截器
 * 如果我们需要对整个系统的的http请求执行统一的拦截处理，可以自定义实现全局拦截器BaseGlobalInterceptor, 并配置成spring容器中的bean
 *
 */
@Component
public class SourceInterceptor extends BaseGlobalInterceptor {

	// 例如我们需要在整个系统发起的http请求中，都带上来源信息
	@Override
	public Response doIntercept(Chain chain) throws IOException {
		Request request = chain.request();
		Request newReq = request.newBuilder()
				.addHeader("source", "test")
				.build();
		return chain.proceed(newReq);
	}
}