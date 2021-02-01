package com.xmxe.component;

import org.springframework.stereotype.Component;

@Component
public class CustomCompoenet {
	private Integer a;
	private String b;

	public void setA(Integer a) {
		this.a = a;
	}

	public Integer getA() {
		return a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	@Override
	public String toString(){
		return "a=="+ a + " b--"+ b;
	}
}
