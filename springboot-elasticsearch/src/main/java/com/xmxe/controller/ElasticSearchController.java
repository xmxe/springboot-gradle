package com.xmxe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElasticSearchController {

	@RequestMapping("hello")
	public String Hello(){
		return "hello gradle";
	}
}
