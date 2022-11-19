package com.sjl.common.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * sql工具类
 * 
 * @author Kelly
 * @version 1.0.0
 * @filename SqlUtils.java
 * @time 2020年4月4日 下午7:15:14 
 * @copyright(C) 2020 song
 */
public class SqlUtils {
	/**
	 * 组装in语句到主干上
	 * 
	 * @param params List<Object>，sql执行的？参数List
	 * @param sqlBuffer StringBuffer
	 * @param column String，表字段，当column=user_name，生成如下：user_name in (?,?)
	 * @param values Object[]，问号（?）的数组值，
	 */
	public static void setInSql(List<Object> params, StringBuffer sqlBuffer, String column, Object[] values) {
		sqlBuffer.append(" ").append("and").append(" ").append(column).append(" ").append("in (");
		for (Object obj : values) {
			if (obj != null) {
				sqlBuffer.append("?,");
				params.add(obj);
			}
		}
		sqlBuffer.delete(sqlBuffer.length() - 1, sqlBuffer.length());
		sqlBuffer.append(")");
	}

	/**
	 * 组装in语句到主干上
	 * 
	 * @param params sql执行的？参数List List<Object>
	 * @param sqlBuffer StringBuffer
	 * @param column 表字段，当column=user_name，生成如下：user_name in (?,?)
	 * @param valuesString String，以英文逗号（,）分隔的字符串
	 */
	public static void setInSql(List<Object> params, StringBuffer sqlBuffer, String column, String valuesString) {
		String[] values = valuesString.split(",");
		setInSql(params, sqlBuffer, column, values);
	}

	/**
	 * 字符串参数转成整型数组
	 * @param valuesString
	 * @return
	 */
	public static Integer[] getIntegerValue(String valuesString){
		List<Integer> integerValues = new ArrayList<Integer>();
		if(!StringUtils.isEmpty(valuesString)){
			if(valuesString.indexOf(",") > -1){
				String[] values = valuesString.split(",");
				for (String value : values) {
					if(!StringUtils.isEmpty(value)){
						integerValues.add(Integer.valueOf(value.trim()));
					}
				}
			}else{
				integerValues.add(Integer.valueOf(valuesString.trim()));
			}
			return integerValues.toArray(new Integer[integerValues.size()]);
		}
		return null;
	}

	/**
	 *
	 * @param sqlBuffer StringBuffer
	 * @param column String，表字段，当column=user_name，生成如下：user_id in (1, 2)
	 * @param values Integer[]，数组值
	 */
	public static void getInSql(StringBuffer sqlBuffer, String column, Integer[] values) {
		if(values == null || values.length < 1){
			throw new RuntimeException("参数值不能为空");
		}
		sqlBuffer.append(" ").append("and").append(" ").append(column).append(" ").append("in (");
		for (Integer value : values) {
			if(value != null){
				sqlBuffer.append(" ").append(value).append(",");
			}
		}
		sqlBuffer.delete(sqlBuffer.length() - 1, sqlBuffer.length());
		sqlBuffer.append(" ").append(")");
	}


	/**
	 *
	 * @param sqlBuffer StringBuffer
	 * @param column String，表字段，当column=user_name，生成如下：user_name in ('a','b')
	 * @param values String[]，数组值
	 */
	public static void getInSql(StringBuffer sqlBuffer, String column, String[] values) {
		if(values == null || values.length < 1){
			throw new RuntimeException("参数值不能为空");
		}
		sqlBuffer.append(" ").append("and").append(" ").append(column).append(" ").append("in (");
		for (String value : values) {
			if(value != null){
				sqlBuffer.append(" '").append(value).append("',");
			}
		}
		sqlBuffer.delete(sqlBuffer.length() - 1, sqlBuffer.length());
		sqlBuffer.append(" ").append(")");
	}
}
