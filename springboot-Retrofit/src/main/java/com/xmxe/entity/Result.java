package com.xmxe.entity;

public class Result {
	private Integer code;
	private String msg;
	private Integer body;

	public Result(){}

	public Result code(Integer code){
		this.code = code;
		return this;
	}
	public Result msg(String msg){
		this.msg = msg;
		return this;
	}
	public Result body(Integer body){
		this.body = body;
		return this;
	}
}
