package com.xmxe.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSONObject;
import com.xmxe.component.WebSocketServer;
import com.xmxe.entity.QrLoginToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * 二维码扫描登陆
 */
@Controller
public class QRLoginController {
	/**
	 * 获取登录二维码、放入Token
	 */
	@RequestMapping(value = "/getLoginQr" ,method = RequestMethod.GET)
	public void createCodeImg(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");

		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		try {
			//这里没啥操作 就是生成一个UUID插入 数据库的表里
//			String uuid = userService.createQrImg();
			String uuid = getUUID();
			response.setHeader("uuid", uuid);
			// 这里是开源工具类 hutool里的QrCodeUtil 网址：http://hutool.mydoc.io/
			QrCodeUtil.generate(uuid, 300, 300, "jpg",response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("qr_login")
	public String qr_login(){
		return "qr_login";
	}

	@GetMapping("index")
	public String index(){
		return "index";
	}

	/**
	 * 确认身份接口：确定身份以及判断是否二维码过期等
	 */
	@GetMapping(value = "/bindUserIdAndToken/{token}/{userId}")
	@ResponseBody
	public Object bindUserIdAndToken(@PathVariable("token") String token ,
	                                 @PathVariable("userId") Integer userId,
	                                 @RequestParam(required = false,value = "projId") Integer projId){

		try {
			return bindUserIdAndToken(userId,token,projId);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}

	}
	public String bindUserIdAndToken(Integer userId, String token,Integer projId) throws Exception {
		Date nowDate = new Date();
		QrLoginToken qrLoginToken = new QrLoginToken();
		qrLoginToken.setToken(token);
		// 查询token是否存在
//		qrLoginToken = qrLoginTokenMapper.selectOne(qrLoginToken);
		// 二维码创建时间
		qrLoginToken.setCreateTime(nowDate);

		if(null == qrLoginToken){
			throw new RuntimeException("错误的请求！");
		}
		// 二维码失效的范围时间 这里指定1000*60*5(5分钟)
		Date createDate = new Date(qrLoginToken.getCreateTime().getTime() + (1000 * 60 * 5));
		if(nowDate.getTime() > createDate.getTime()){//当前时间大于校验时间

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code",500);
			jsonObject.put("msg","二维码失效！");
			WebSocketServer.sendInfo(jsonObject.toJSONString(),token);
			throw new RuntimeException("二维码失效！");
		}
		qrLoginToken.setLoginTime(nowDate);
		qrLoginToken.setState(0);
		qrLoginToken.setUserId(userId);

		//更新数据 插入数据库
//		int i = qrLoginTokenMapper.updateById(qrLoginToken);
		int i = 1;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code",200);
		jsonObject.put("msg","ok");
		jsonObject.put("userId",userId);
		if(projId != null){
			jsonObject.put("projId",projId);
		}
		WebSocketServer.sendInfo(jsonObject.toJSONString(),token);

		if(i > 0 ){
			return "更新二维码登陆时间成功";
		}else{
			throw  new Exception("服务器异常！更新数据库失败");
		}
	}

	/**
	 * 获得指定数目的UUID
	 * @param number int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number){
		if(number < 1){
			return null;
		}
		String[] retArray = new String[number];
		for(int i=0;i<number;i++){
			retArray[i] = getUUID();
		}
		return retArray;
	}

	/**
	 * 获得一个UUID
	 * @return String UUID
	 */
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString();
		//去掉“-”符号
		return uuid.replaceAll("-", "");
	}
}
