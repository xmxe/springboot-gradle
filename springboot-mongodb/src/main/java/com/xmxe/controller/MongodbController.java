package com.xmxe.controller;

import com.xmxe.util.MongodbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongodbController {
	@Autowired
	MongodbUtil mongodbUtil;
	@GetMapping("test")
	public void test(){
//		mongodbUtil.save(null);
	}
}