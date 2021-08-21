package com.xmxe.controller;

import com.xmxe.anno.MyOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@MyOperation("test clsss")//
public class InterceptorController {

	@MyOperation("test method")
	@RequestMapping("test")
	public String test() {
		return "Hello,2018!";
	}

}
