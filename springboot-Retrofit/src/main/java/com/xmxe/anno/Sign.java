package com.xmxe.anno;

import com.github.lianjiatech.retrofit.spring.boot.annotation.InterceptMark;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import com.xmxe.component.SignInterceptor;

import java.lang.annotation.*;

/**
 * 扩展注解式拦截器
 * 有的时候，我们需要在拦截注解 动态传入一些参数，然后再执行拦截的时候需要使用这个参数。这种时候，我们可以扩展实现自定义拦截注解 。
 * 自定义拦截注解必须使用@InterceptMark标记，并且注解中必须包括include()、exclude()、handler()属性信息 。使用的步骤主要分为3步：
 * 1.自定义拦截注解
 * 2.继承BasePathMatchInterceptor编写拦截处理器
 * 3.接口上使用自定义拦截注解；
 * 例如我们需要在请求头里面动态加入accessKeyId、accessKeySecret签名信息才能正常发起http请求 ，
 * 这个时候可以自定义一个加签拦截器注解@Sign来实现 。
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@InterceptMark
public @interface Sign {
	/**
	 * 密钥key
	 * 支持占位符形式配置。
	 */
	String accessKeyId();

	/**
	 * 密钥
	 * 支持占位符形式配置。
	 */
	String accessKeySecret();

	/**
	 * 拦截器匹配路径
	 */
	String[] include() default {"/**"};

	/**
	 * 拦截器排除匹配，排除指定路径拦截
	 */
	String[] exclude() default {};

	/**
	 * 处理该注解的拦截器类
	 * 优先从spring容器获取对应的Bean，如果获取不到，则使用反射创建一个！
	 */
	Class<? extends BasePathMatchInterceptor> handler() default SignInterceptor.class;
}
