package com.sjl.common.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjl.common.ApiResponse;
import com.sjl.common.ErrorCode;

/**
 * 全局Controller层异常处理类
 * 
 * @author Kelly
 * @version 1.0.0
 * @filename GlobalExceptionResolver.java
 * @time 2020年4月4日 下午9:18:53 
 * @copyright(C) 2020 song
 */
@ControllerAdvice
public class GlobalExceptionResolver {
	private static final Logger LOGGER = Logger.getLogger(GlobalExceptionResolver.class);

	/**
	 * 处理所有异常
	 *
	 * @param e 异常
	 * @return json结果
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ApiResponse handleException(Exception e) {
		// 打印异常堆栈信息
		LOGGER.error("发生全局异常",e);
		return ApiResponse.build(ErrorCode.ERROR_FAIL, e.getMessage());
	}

	/**
	 * 处理baedao sql异常
	 *
	 * @param e basedao异常
	 * @return json结果
	 */
	@ExceptionHandler(BaseDaoException.class)
	@ResponseBody
	public ApiResponse handleBaseDaoException(BaseDaoException e) {
		LOGGER.error("发生全局dao异常",e);
		return ApiResponse.build(ErrorCode.ERROR_FAIL, e.getMessage() + ":" + e.getCause());
	}

}
