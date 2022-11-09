package com.sjl.common.page;

/**
 * mysql数据库的分页实现
 * @author song
 */
public class MysqlSqlPageHandleImpl implements SqlPageHandle {

	@Override
	public String handlerPagingSQL(String oldSQL, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer(oldSQL);
		if (pageSize > 0) {
			int startIndex = (pageNo - 1) * pageSize;
			if (startIndex <= 0) {
				sql.append(" limit ").append(pageSize);
			} else {
				sql.append(" limit ").append(startIndex).append(",").append(pageSize);
			}
		}
		return sql.toString();
	}

}
