package com.xmxe.controller;

import com.xmxe.component.CustomCompoenet;
import com.xmxe.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElasticSearchController {

	private ElasticSearchService elasticSearchService;

	private CustomCompoenet customCompoenet;

	@Autowired
	public ElasticSearchController(ElasticSearchService elasticSearchService){
		this.elasticSearchService = elasticSearchService;
	}

	@Autowired
	public void methodInjection(CustomCompoenet customCompoenet){
		customCompoenet.setA(2);
		customCompoenet.setB("qw");
		this.customCompoenet = customCompoenet;
	}


	@RequestMapping("hello")
	public String Hello(){
		elasticSearchService.serviceMethod();
		System.out.println(customCompoenet.toString());
		return "hello gradle";
	}
}
