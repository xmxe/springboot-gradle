package com.xmxe.controller;

import com.xmxe.service.HttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetrofitController {

	private final HttpApi httpApi;

	@Autowired
	public RetrofitController(HttpApi httpApi) {
		this.httpApi = httpApi;
	}

	@GetMapping("hello/{text}")
	public String hello(@PathVariable("text") String text){
		return httpApi.hello(text,1L);
	}
}
