package com.sjl.common.page;

/**
 * 分页处理接口,根据不同的数据库，调用不同的实现
 * @author song
 */
public interface SqlPageHandle {

	/**
	 * 将传入的SQL做分页处理
	 * 
	 * @param String
	 *            oldSql 原SQL
	 * @param int
	 *            pageNo 第几页，用来计算first 这个值由（pageNo-1）*pageSize
	 * @param int
	 *            pageSize 每页数量
	 */
	public String handlerPagingSQL(String oldSql, int pageNo, int pageSize);

}