package com.sjl.common.exception;

/**
 * TODO
 * @author Kelly
 * @version 1.0.0
 * @filename CustomerException.java
 * @time 2020年4月12日 下午7:18:56
 * @copyright(C) 2020 song
 */
public class CustomerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8569377157007064059L;

	public CustomerException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerException(String message) {
		super(message);
	}

}
