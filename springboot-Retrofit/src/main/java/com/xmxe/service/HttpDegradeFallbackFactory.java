package com.xmxe.service;

import com.github.lianjiatech.retrofit.spring.boot.degrade.FallbackFactory;
import com.xmxe.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HttpDegradeFallbackFactory implements FallbackFactory<HttpDegradeApi> {

	/**
	 * Returns an instance of the fallback appropriate for the given cause
	 *
	 * @param cause fallback cause
	 * @return 实现了retrofit接口的实例。an instance that implements the retrofit interface.
	 */
	@Override
	public HttpDegradeApi create(Throwable cause) {
		log.error("触发熔断了! ", cause.getMessage(), cause);
		return new HttpDegradeApi() {
			@Override
			public Result  test() {
				Result fallback = new Result();
				fallback.code(100)
						.msg("fallback")
						.body(1000000);
				return fallback;
			}
		};
	}
}