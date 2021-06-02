package com.sjl.common.page;

/**
 * oracle分页实现
 * @author Kelly
 * @version 1.0.0
 * @filename OracleSQLPageHandleImpl.java
 * @time 2019年5月12日 下午3:01:01
 * @copyright(C) 2019 song
 */
public class OracleSqlPageHandleImpl implements SqlPageHandle {


	public String handlerPagingSQL(String oldSQL, int pageNo, int pageSize) {
		if (pageSize > 0) {
			int startIndex = (pageNo - 1) * pageSize; 
			if (startIndex <= 0) {
				oldSQL = "select * from (" + oldSQL + ") where rownum<="
						+ startIndex;
			} else {
				oldSQL = " select row_.*,rownum rownum_ from (" + oldSQL
						+ ") row_ where rownum<=" + startIndex;
				oldSQL = "select * from (" + oldSQL + ") where rownum_>"
						+ pageNo;
			}
		}
		return oldSQL;
	}

}