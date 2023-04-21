package com.xmxe.config;

import com.github.lianjiatech.retrofit.spring.boot.core.ServiceInstanceChooser;
import com.github.lianjiatech.retrofit.spring.boot.core.SpringCloudServiceInstanceChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微服务之间的HTTP调用,为了能够使用微服务调用，需要进行如下配置：
 * 配置ServiceInstanceChooser为Spring容器Bean
 * 用户可以自行实现ServiceInstanceChooser接口，完成服务实例的选取逻辑，并将其配置成Spring容器的Bean。对于Spring Cloud应用，
 * retrofit-spring-boot-starter提供了SpringCloudServiceInstanceChooser实现，用户只需将其配置成Spring的Bean即可。
 */
@Configuration
public class RetrofitConfiguration {

	@Bean
	@Autowired
	public ServiceInstanceChooser serviceInstanceChooser(LoadBalancerClient loadBalancerClient) {
		return new SpringCloudServiceInstanceChooser(loadBalancerClient);
	}
}