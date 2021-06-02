package com.sjl.common.page;

/**
 * TODO
 * 
 * @author Kelly
 * @version 1.0.0
 * @filename SqlServerSqlPageHandleImpl.java
 * @time 2019年10月14日 上午10:56:20
 * @copyright(C) 2019 song
 */
public class SqlServerSqlPageHandleImpl implements SqlPageHandle {

	/**
	 * @param oldSql  语句格式：select *,row_number() over (order by id asc) n from 表名
	 *             
	 */
	@Override
	public String handlerPagingSQL(String oldSql, int pageNo, int pageSize) {

		if (pageSize > 0) {
			int startIndex = (pageNo - 1) * pageSize;

			if (startIndex <= 0) {
				oldSql = "select * from (" + oldSql + " ) t where t.n<=" + startIndex;
			} else {
				oldSql = "select * from (" + oldSql + ") t where t.n>" + startIndex + " and t.n <="
						+ (startIndex + pageSize);
			}
		}
		return oldSql;
	}

}
