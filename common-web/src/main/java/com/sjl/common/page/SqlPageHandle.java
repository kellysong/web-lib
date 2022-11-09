package com.sjl.common.page;

/**
 * 分页处理接口,根据不同的数据库，调用不同的实现
 *
 * @author song
 */
public interface SqlPageHandle {

    /**
     * 将传入的SQL做分页处理
     *
     * @param oldSql 原SQL
     * @param pageNo 第几页，用来计算first 这个值由（pageNo-1）*pageSize
     * @param pageSize 每页数量
     */
     String handlerPagingSQL(String oldSql, int pageNo, int pageSize);

}