package com.sjl.common;

/**
 * 错误码
 * @author song
 *
 */
public interface ErrorCode {
	/**
	 * 请求成功
	 */
	public static final int ERROR_OK = 0;
	/**
	 * 请求没有数据或者没有资源，或者资源已经是最新了
	 */
	public static final int ERROR_NO_DATA = -1;
	
	/**
	 * 数据已存在
	 */
	public static final int ERROR_DATA_EXIST = -2;
	/**
	 * 请求参数为空
	 */
	public static final int ERROR_NULL_PARAMETER = -3;
	/**
	 * 请求参数无效
	 */
	public static final int ERROR_INVALID_PARAMETER = -4;
	
	/**
	 * 请求超时
	 */
	public static final int ERROR_TIMEOUT = -5;

	/**
	 * 请求没有权限
	 */
	public static final int ERROR_NO_ACCESS = -6;

	/**
	 * 文件已经移除
	 */
	public static final int ERROR_FILE_DELETE = -7;
	
	

	/**
	 * 请求失败或者异常
	 */
	public static final int ERROR_FAIL = -9;

}
