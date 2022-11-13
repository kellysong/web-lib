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
	 int ERROR_OK = 0;
	/**
	 * 请求没有数据或者没有资源，或者资源已经是最新了
	 */
	 int ERROR_NO_DATA = -1;
	
	/**
	 * 数据已存在
	 */
	 int ERROR_DATA_EXIST = -2;
	/**
	 * 请求参数为空
	 */
	 int ERROR_NULL_PARAMETER = -3;
	/**
	 * 请求参数无效
	 */
	 int ERROR_INVALID_PARAMETER = -4;
	
	/**
	 * 请求超时
	 */
	 int ERROR_TIMEOUT = -5;

	/**
	 * 请求没有权限
	 */
	 int ERROR_NO_ACCESS = -6;

	/**
	 * 文件已经移除
	 */
	 int ERROR_FILE_DELETE = -7;


	/**
	 * 存在绑定的数据，无法操作
	 */
	 int ERROR_EXIST_BIND_DATA = -8;


	/**
	 * 修改的记录不存在
	 */
	 int ERROR_UPDATE_NO_RECORD = -9;
	
	/**
	 * 请求失败或者异常
	 */
	 int ERROR_FAIL = -99;

}
