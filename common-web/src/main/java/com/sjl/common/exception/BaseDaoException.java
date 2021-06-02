package com.sjl.common.exception;

/**
 * TODO
 * @author Kelly
 * @version 1.0.0
 * @filename BaseDaoException.java
 * @time 2020年4月4日 下午9:19:28
 * @copyright(C) 2020 song
 */
public class BaseDaoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2004656106173661925L;

	public BaseDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseDaoException(String message) {
		super(message);
	}
	

}
