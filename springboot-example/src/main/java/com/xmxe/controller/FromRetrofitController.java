package com.xmxe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FromRetrofitController {

	@GetMapping("api/{text}")
	public String text(@PathVariable("text") String text){

		return "hello"+text;
	}

}
