package com.xmxe.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WebFluxController {

	/**
	 * 普通接口
	 * @return
	 */
	@GetMapping("hello")
	public String hello(){
		return getHelloStr();
	}

	@GetMapping("/monoHello")
	public Mono<String> monoHello() {
		long start = System.currentTimeMillis();
		Mono<String> hello = Mono.fromSupplier(() -> getHelloStr());
		System.out.println("WebFlux 接口耗时：" + (System.currentTimeMillis() - start));
		return hello;
	}

	@GetMapping(value = "/flux",produces = MediaType.TEXT_EVENT_STREAM_VALUE)//Content-Type:text/event-stream
	public Flux<String> flux() {
		Flux<String> flux = Flux.fromArray(new String[]{"javaboy","itboyhub","www.javaboy.org","itboyhub.com"}).map(s -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "my->data->" + s;
		});
		return flux;
	}

	private String getHelloStr() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "hello";
	}

}
