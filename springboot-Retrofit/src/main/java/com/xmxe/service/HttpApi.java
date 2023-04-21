package com.xmxe.service;


import com.github.lianjiatech.retrofit.spring.boot.annotation.Intercept;
import com.github.lianjiatech.retrofit.spring.boot.annotation.OkHttpClientBuilder;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.github.lianjiatech.retrofit.spring.boot.retry.RetryRule;
import com.xmxe.anno.Sign;
import com.xmxe.component.TimeStampInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.concurrent.TimeUnit;

/**
 * 接口必须使用@RetrofitClient注解标记
 */
@RetrofitClient(baseUrl = "${http.baseUrl}", poolName="test1")
/**
 * 配置拦截器
 */
@Intercept(handler = TimeStampInterceptor.class, include = {"/api/**"}, exclude = "/api/exclude")
/**
 * 自定义注解拦截器
 * 这样就能在指定url的请求上，自动加上签名信息了
 */
@Sign(accessKeyId = "${http.accessKeyId}", accessKeySecret = "${http.accessKeySecret}", exclude = {"/api/exclude"})
public interface HttpApi {

	@GET("api/{text}")
	String hello(@Path("text") String text,@Query("id") Long id);

	/**
	 * TimeStampInterceptor拦截器排除的接口
	 */
	@GET("api/exclude")
	String exclude();

	/**
	 * 请求重试
	 * retrofit-spring-boot-starter支持请求重试功能，只需要在接口或者方法上加上@Retry注解即可。@Retry支持重试次数maxRetries、重试时间间隔intervalMs以及重试规则retryRules配置。
	 * 重试规则支持三种配置：
	 * RESPONSE_STATUS_NOT_2XX：响应状态码不是2xx时执行重试；
	 * OCCUR_IO_EXCEPTION：发生IO异常时执行重试；
	 * OCCUR_EXCEPTION：发生任意异常时执行重试；
	 * 默认响应状态码不是2xx或者发生IO异常时自动进行重试。需要的话，你也可以继承BaseRetryInterceptor实现自己的请求重试拦截器，然后将其配置上去。
	 */
	@GET("api/retry")
	@Retry(maxRetries = 2,intervalMs = 100,retryRules = RetryRule.OCCUR_EXCEPTION)
	String retry();

	/**
	 * 自定义注入OkHttpClient
	 * 通常情况下，通过@RetrofitClient注解属性动态创建OkHttpClient对象能够满足大部分使用场景。
	 * 但是在某些情况下，用户可能需要自定义OkHttpClient，这个时候可以在接口上定义返回类型是OkHttpClient.Builder的静态方法来实现。
	 * 方法必须使用@OkHttpClientBuilder注解标记！
	 */
	@OkHttpClientBuilder
	static OkHttpClient.Builder okhttpClientBuilder() {
		return new OkHttpClient.Builder()
				.connectTimeout(1, TimeUnit.SECONDS)
				.readTimeout(1, TimeUnit.SECONDS)
				.writeTimeout(1, TimeUnit.SECONDS);

	}
}
/*
 * 请求方式	@GET @HEAD @POST @PUT @DELETE @OPTIONS
 * 请求头	@Header @HeaderMap @Headers
 * Query参数	@Query @QueryMap @QueryName
 * path参数	@Path
 * form-encoded参数	@Field @FieldMap @FormUrlEncoded
 * 文件上传	@Multipart @Part @PartMap
 * url参数	@Url
 */