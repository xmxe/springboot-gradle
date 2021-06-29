package com.xmxe.entity;

import lombok.Data;

import java.util.Date;

@Data
public class QrLoginToken {
	// 用于确保唯一性
	String token;
	// 谁登录的
	Integer userId;
	// 登录时间
	Date loginTime;
	// 创建时间 用于判断是否过期
	Date createTime;
	// 是否二维码失效  0有效 1失效
	Integer state;

}
