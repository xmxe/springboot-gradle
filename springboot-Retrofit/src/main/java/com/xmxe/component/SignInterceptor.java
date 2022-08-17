package com.xmxe.component;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 配合使用@Sign注解
 *
 * accessKeyId和accessKeySecret字段值会依据@Sign注解的accessKeyId()和accessKeySecret()值自动注入，
 * 如果@Sign指定的是占位符形式的字符串，则会取配置属性值进行注入 。另外，accessKeyId和accessKeySecret字段必须提供setter方法
 */
@Component
public class SignInterceptor extends BasePathMatchInterceptor {
	private String accessKeyId;
	private String accessKeySecret;
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	/**
	 * 在请求头里面动态加入accessKeyId、accessKeySecret签名信息才能正常发起http请求 ，
	 */
	@Override
	public Response doIntercept(Chain chain) throws IOException {
		Request request = chain.request();
		Request newReq = request.newBuilder()
				.addHeader("accessKeyId", accessKeyId)
				.addHeader("accessKeySecret", accessKeySecret)
				.build();
		return chain.proceed(newReq);
	}
}