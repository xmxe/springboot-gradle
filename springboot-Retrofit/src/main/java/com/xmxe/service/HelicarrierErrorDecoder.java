package com.xmxe.service;

import com.github.lianjiatech.retrofit.spring.boot.core.ErrorDecoder;
import com.github.lianjiatech.retrofit.spring.boot.exception.RetrofitException;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 错误解码器。ErrorDecoder.
 * 当请求发生异常或者收到无效响应结果的时候，将HTTP相关信息解码到异常中，无效响应由业务自己判断
 *
 * 当请求发生异常或者收到无效响应结果的时候，将HTTP相关信息解码到异常中，无效响应由业务自己判断。 一般情况下，每个服务对应的无效响应各不相同，
 * 可以自定义对应的ErrorDecoder，然后配置在这里
 *
 */
public class HelicarrierErrorDecoder implements ErrorDecoder {
	/**
	 * 当无效响应的时候，将HTTP信息解码到异常中，无效响应由业务自行判断。
	 * When the response is invalid, decode the HTTP information into the exception, invalid response is determined by business.
	 *
	 * @param request  request
	 * @param response response
	 * @return If it returns null, the processing is ignored and the processing continues with the original response.
	 */
	@Override
	public RuntimeException invalidRespDecode(Request request, Response response) {
		if (!response.isSuccessful()) {
			throw RetrofitException.errorStatus(request, response);
		}
		return null;
	}

	/**
	 * 当请求发生IO异常时，将HTTP信息解码到异常中。
	 * When an IO exception occurs in the request, the HTTP information is decoded into the exception.
	 *
	 * @param request request
	 * @param cause   IOException
	 * @return RuntimeException
	 */
	@Override
	public RuntimeException ioExceptionDecode(Request request, IOException cause) {
		return RetrofitException.errorExecuting(request, cause);
	}

	/**
	 * 当请求发生除IO异常之外的其它异常时，将HTTP信息解码到异常中。
	 * When the request has an exception other than the IO exception, the HTTP information is decoded into the exception.
	 *
	 * @param request request
	 * @param cause   Exception
	 * @return RuntimeException
	 */
	@Override
	public RuntimeException exceptionDecode(Request request, Exception cause) {
		return RetrofitException.errorUnknown(request, cause);
	}
}

