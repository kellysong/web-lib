package com.sjl;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 日志测试
 * @author Kelly
 * @version 1.0.0
 * @filename LogTest.java
 * @time 2020年11月29日 下午1:08:52
 * @copyright(C) 2020 song
 */
public class LogTest {

	private static final Logger LOGGER = Logger.getLogger(LogTest.class);


	@Test
	public void testFamilyTree() {
		System.out.println("控制台日志");
		//  记录debug级别的信息
		LOGGER.debug("This is debug message.");
		//记录info级别的信息
		LOGGER.info("This is info message.");
		LOGGER.warn("This is warn message.");
		//记录error级别的信息
		LOGGER.error("This is error message.");
		LOGGER.fatal("This is fatal message.");
	}


}
