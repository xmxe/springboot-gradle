package com.xmxe.service;

import com.xmxe.entity.Result;

/**
 * 熔断时统一返回数据
 */
public interface HttpDegradeApi {
	Result test();
}
