package com.xmxe.anno;

import com.github.lianjiatech.retrofit.spring.boot.degrade.DegradeStrategy;

import java.lang.annotation.*;

/**
 * 配置降级规则（可选）
 * retrofit-spring-boot-starter支持注解式配置降级规则，通过@Degrade注解来配置降级规则,@Degrade注解可以配置在接口或者方法上，配置在方法上的优先级更高。
 * 如果应用项目已支持通过配置中心配置降级规则，可忽略注解式配置方式
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Degrade {

	/**
	 * RT threshold or exception ratio threshold count.
	 * 阈值或异常比率阈值计数
	 */
	double count();

	/**
	 * Degrade recover timeout (in seconds) when degradation occurs.
	 * 发生降级时降级恢复超时（以秒为单位）
	 */
	int timeWindow() default 5;

	/**
	 * Degrade strategy (0: average RT, 1: exception ratio).
	 * 降级策略（0：平均 RT，1：异常率）
	 */
	DegradeStrategy degradeStrategy() default DegradeStrategy.AVERAGE_RT;
}