package com.sjl.common;

/**
 * 响应基类
 * 
 * @author Kelly
 * @version 1.0.0
 * @filename BaseResponse.java
 * @time 2019年4月23日 上午11:26:30
 * @copyright(C) 2019 song
 
 */
public class BaseResponse {
	private int code;// 结果码
	private String msg;// 结果描述信息


	public BaseResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
