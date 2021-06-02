package com.sjl.common.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Springmvc get乱码问题解决
 * 
 * @author Kelly
 * @version 1.0.0
 * @filename SpringCharacterEncodingFilter.java
 * @time 2020年4月6日 下午8:42:17 
 * @copyright(C) 2020 song
 */
public class SpringCharacterEncodingFilter extends CharacterEncodingFilter {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.CharacterEncodingFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 解决get乱码
		EncodingRequest encodingRequest = new EncodingRequest((HttpServletRequest) request);

		super.doFilterInternal(encodingRequest, response, filterChain);
	}

	private static class EncodingRequest extends HttpServletRequestWrapper {

		private HttpServletRequest request;

		private boolean hasEncode = false;

		public EncodingRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}

		@Override
		public String getParameter(String name) {
			// 通过getParameterMap方法完成
			String[] values = getParameterValues(name);
			if (values == null) {
				return null;
			}
			return values[0];
		}

		@Override
		public String[] getParameterValues(String name) {
			// 通过getParameterMap方法完成
			Map<String, String[]> parameterMap = getParameterMap();
			String[] values = parameterMap.get(name);
			return values;
		}

		@Override
		public Map getParameterMap() {
			Map<String, String[]> parameterMap = request.getParameterMap();
			String method = request.getMethod();
			if (method.equalsIgnoreCase("post")) {
				return parameterMap;
			}

			// get提交方式 手动转码 , 这里的转码只进行一次 所以通过 hasEncode 布尔类型变量控制
			if (!hasEncode) {
				Set<String> keys = parameterMap.keySet();
				for (String key : keys) {
					String[] values = parameterMap.get(key);
					if (values == null) {
						continue;
					}
					for (int i = 0; i < values.length; i++) {
						String value = values[i];
						// 解决get
						try {
							value = new String(value.getBytes("ISO-8859-1"), "utf-8");
							// values是一个地址
							values[i] = value;
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
					// 一次转码完成后，设置转码状态为true
					hasEncode = true;
				}
			}
			return parameterMap;
		}
	}
}
