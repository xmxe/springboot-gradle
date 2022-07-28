package com.xmxe.service;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.xmxe.entity.Result;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.concurrent.CompletableFuture;


/**
 * 调用适配器
 * Retrofit可以通过调用适配器CallAdapterFactory将Call<T>对象适配成接口方法的返回值类型。
 * retrofit-spring-boot-starter扩展2种CallAdapterFactory实现：
 *
 * 1.BodyCallAdapterFactory
 * 默认启用，可通过配置retrofit.enable-body-call-adapter=false关闭
 * 同步执行http请求，将响应体内容适配成接口方法的返回值类型实例。
 * 除了Retrofit.Call<T>、Retrofit.Response<T>、java.util.concurrent.CompletableFuture<T>之外，其它返回类型都可以使用该适配器。
 * 2.ResponseCallAdapterFactory
 * 默认启用，可通过配置retrofit.enable-response-call-adapter=false关闭
 * 同步执行http请求，将响应体内容适配成Retrofit.Response<T>返回。
 * 如果方法的返回值类型为Retrofit.Response<T>，则可以使用该适配器。
 * Retrofit自动根据方法返回值类型选用对应的CallAdapterFactory执行适配处理！加上Retrofit默认的CallAdapterFactory，可支持多种形式的方法返回值类型：
 *
 * Call<T>: 不执行适配处理，直接返回Call<T>对象
 * CompletableFuture<T>: 将响应体内容适配成CompletableFuture<T>对象返回
 * Void: 不关注返回类型可以使用Void。如果http状态码不是2xx，直接抛错！
 * Response<T>: 将响应内容适配成Response<T>对象返回
 * 其他任意Java类型：将响应体内容适配成一个对应的Java类型对象返回，如果http状态码不是2xx，直接抛错！
 */
@RetrofitClient(serviceId = "${jy-helicarrier-api.serviceId}", path = "/m/count", errorDecoder = HelicarrierErrorDecoder.class)
@Retry
public interface ApiCountService {

	/**
	 * Call<T>
	 * 不执行适配处理，直接返回Call<T>对象
	 */
	@GET("person")
	Call<Result> getPersonCall(@Query("id") Long id);

	/**
	 *  CompletableFuture<T>
	 *  将响应体内容适配成CompletableFuture<T>对象返回
	 */
	@GET("person")
	CompletableFuture<Result> getPersonCompletableFuture(@Query("id") Long id);

	/**
	 * Void
	 * 不关注返回类型可以使用Void。如果http状态码不是2xx，直接抛错！
	 */
	@GET("person")
	Void getPersonVoid(@Query("id") Long id);

	/**
	 *  Response<T>
	 *  将响应内容适配成Response<T>对象返回
	 */
	@GET("person")
	Response<Result> getPersonResponse(@Query("id") Long id);

	/**
	 * 其他任意Java类型
	 * 将响应体内容适配成一个对应的Java类型对象返回，如果http状态码不是2xx，直接抛错！
	 */
	@GET("person")
	Result getPerson(@Query("id") Long id);
}
/**
 * 我们也可以通过继承CallAdapter.Factory扩展实现自己的CallAdapter ！
 * retrofit-spring-boot-starter支持通过retrofit.global-call-adapter-factories配置全局调用适配器工厂，
 * 工厂实例优先从Spring容器获取，如果没有获取到，则反射创建。默认的全局调用适配器工厂是[BodyCallAdapterFactory, ResponseCallAdapterFactory]！
 */