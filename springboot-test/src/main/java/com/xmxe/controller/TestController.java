package com.xmxe.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

	@GetMapping("test/{id}")
	public String testGet(@PathVariable("id") Integer id){
		String str = "get请求"+id;
		return str;
	}

	@DeleteMapping("test/{id}")
	public String testDelete(@PathVariable("id") Integer id){
		String str = "delete请求"+id;
		return str;
	}

	@PutMapping("test/{id}")
	public String testPut(@PathVariable("id") Integer id){
		String str = "put请求"+id;
		return str;
	}
}
