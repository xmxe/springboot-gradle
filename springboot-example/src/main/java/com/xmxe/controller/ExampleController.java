package com.xmxe.controller;

import com.xmxe.component.CustomCompoenet;
import com.xmxe.component.CustomValidator;
import com.xmxe.component.StringToListPropertyEditor;
import com.xmxe.entity.User;
import com.xmxe.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@RestController
public class ExampleController {

	private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

	private final ExampleService exampleService;

	private CustomCompoenet customCompoenet;

	// 构造器注入保证依赖不可变（final关键字）,保证依赖不为空（省去了我们对其检查）,保证返回客户端（调用）的代码的时候是完全初始化的状态,避免了循环依赖,提升了代码的可复用性,
	// 推荐使用构造器注入，因为类在加载时先加载构造方法后加载@Autowired 不使用构造器注入如果想在构造方法里使用bean的某个方法 会出现NPE异常,因为bean还没有被加载
	@Autowired
	public ExampleController(ExampleService exampleService){
		this.exampleService = exampleService;
	}

	// 方法注入
	@Autowired
	public void methodInjection(CustomCompoenet customCompoenet){
		customCompoenet.setA(2);
		customCompoenet.setB("qw");
		this.customCompoenet = customCompoenet;
	}


	@RequestMapping("hello/{text}")
	public String hello(@PathVariable("text") String text){
		logger.info("text={}",text);
		exampleService.serviceMethod();
		logger.info(customCompoenet.toString());
		return "hello gradle";
	}

	@GetMapping("binder")// ip:port/binder?date=2020-12-12 10:54:45&text=11_22_33
	public String binder(Date date,String[] text){
		logger.info("date={}",date);
		logger.info("array={}", Arrays.toString(text));
		return "binder";
	}

	@GetMapping("webbinder")
	public String webbinder(String webbinder){
		return webbinder;
	}

	@GetMapping("user")
	public String webbinder(@Validated User user){
		return user.toString();
	}

	/**
	 * InitBinder methods should return void
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		// 将传过来的参数转换为Date格式
		parseDate(webDataBinder);
		// 带"_"的String转数组
		string2array(webDataBinder);
	}

	/**
	 * 将传过来的参数转换为Date格式
	 */
	private void parseDate(WebDataBinder webDataBinder){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// allowEmpty = true 允许date字段为null
		CustomDateEditor dateEditor = new CustomDateEditor(df, true);
		webDataBinder.registerCustomEditor(Date.class, dateEditor);
	}

	/**
	 * 参数带"_"的String转数组
	 */
	private void string2array(WebDataBinder webDataBinder){
		webDataBinder.registerCustomEditor(String[].class, new StringToListPropertyEditor());
	}

	@InitBinder(value = {"webbinder","user"})// 若指定了value值，那么只有方法参数名（或者模型名）匹配上了此注解方法才会执行（若不指定，都执行）。
	public void initBinder_(WebDataBinder webDataBinder){
		webDataBinder.setValidator(new CustomValidator());
//		webDataBinder.addValidators();
		// 禁用属性
//		webDataBinder.setDisallowedFields("address");
		// 在属性中去除pre_前缀 访问http://localhost:9081/user?pre_name=1&pre_age=19返回age=19 name=1 address=null
		webDataBinder.setFieldDefaultPrefix("pre_");
//		webDataBinder.setFieldMarkerPrefix("mark_");
//		logger.info(webDataBinder.getFieldMarkerPrefix());
//		webDataBinder.addCustomFormatter();

	}
}
