package com.xmxe.service;

import com.xmxe.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * (可选)@RetrofitClient设置fallback或者fallbackFactory
 * 如果@RetrofitClient不设置fallback或者fallbackFactory，当触发熔断时，会直接抛出RetrofitBlockException异常。用户可以通过设置fallback或者fallbackFactory来定制熔断时的方法返回值。
 * fallback类必须是当前接口的实现类，fallbackFactory必须是FallbackFactory<T>实现类，泛型参数类型为当前接口类型。另外，fallback和fallbackFactory实例必须配置成Spring容器的Bean。
 * fallbackFactory相对于fallback，主要差别在于能够感知每次熔断的异常原因(cause)
 */
@Slf4j
@Service
public class HttpDegradeFallback implements HttpDegradeApi {

	@Override
	public Result test() {
		Result fallback = new Result();
		fallback.code(100)
				.msg("fallback")
				.body(1000000);
		return fallback;
	}
}