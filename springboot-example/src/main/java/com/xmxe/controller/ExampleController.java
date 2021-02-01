package com.xmxe.controller;

import com.xmxe.component.CustomCompoenet;
import com.xmxe.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	private ExampleService exampleService;

	private CustomCompoenet customCompoenet;

	// 构造器注入
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


	@RequestMapping("hello")
	public String Hello(){
		exampleService.serviceMethod();
		System.out.println(customCompoenet.toString());
		return "hello gradle";
	}
}
