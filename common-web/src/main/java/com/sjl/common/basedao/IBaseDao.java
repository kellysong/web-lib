package com.sjl.common.basedao;

import com.sjl.common.page.Page;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @param <T>
 * @param <PK>
 * @author song
 */
interface IBaseDao<T, PK extends Serializable> {

    /**
     * 无参数查询实体
     *
     * @param sql 查询sql
     * @return
     */
    T queryBean(String sql);

    /**
     * 有参数查询实体
     *
     * @param sql    查询sql
     * @param params 附加参数
     * @return
     */
    T queryBean(String sql, Object... params);

    /**
     * 使用自定义Class查询
     *
     * @param sql
     * @param params
     * @param classT
     * @return
     */
    <T> T queryBean(String sql, Object[] params, Class<T> classT);

    /**
     * 使用自定义RowMapper查询
     *
     * @param sql
     * @param params
     * @param rowMapper
     * @return
     */
    <T> T queryBean(String sql, Object[] params, RowMapper<T> rowMapper);


    /**
     * 获取某个记录某列或者count、avg、sum等函数返回唯一值
     *
     * @param sql
     * @param requiredType 是基本的classType（比如String，int等类型），如果不是基本的，需要经过newBeanPropertyRowMapper<T>(classT)转换
     * @param args
     * @return
     */
    <R> R queryRequiredType(String sql, Class<R> requiredType, Object... args);

    /**
     * 分页查询
     *
     * @param sql      查询sql
     * @param params   分页查询参数
     * @param pageNo   第几页，用来计算first 这个值由（pageNo-1）*pageSize
     * @param pageSize 每页数量
     * @return
     */
    Page<T> queryPagination(String sql, Object[] params, int pageNo, int pageSize);


    /**
     * 使用自定义Class查询
     *
     * @param sql
     * @param params
     * @param pageNo
     * @param pageSize
     * @return
     */
    <T> Page<T> queryPagination(String sql, Object[] params, int pageNo, int pageSize, Class<T> classT);

    /**
     * 使用自定义RowMapper查询
     *
     * @param sql
     * @param params
     * @param pageNo
     * @param pageSize
     * @param rowMapper
     * @return
     */
    <T> Page<T> queryPagination(String sql, Object[] params, int pageNo, int pageSize, RowMapper<T> rowMapper);


    /**
     * 无参数查询实体list集合
     *
     * @param sql 查询sql
     * @return
     */
    List<T> queryBeanList(String sql);

    /**
     * 有参数查询实体list集合
     *
     * @param sql    查询sql
     * @param params 附加参数
     * @return
     */
    List<T> queryBeanList(String sql, Object... params);

    /**
     * 使用自定义Class查询
     *
     * @param sql
     * @param params
     * @param classT
     * @return
     */
    <T> List<T> queryBeanList(String sql, Object[] params, Class<T> classT);

    /**
     * 使用自定义RowMapper查询
     *
     * @param sql
     * @param params
     * @param rowMapper
     * @return
     */
    <T> List<T> queryBeanList(String sql, Object[] params, RowMapper<T> rowMapper);


    /**
     * 有参数查询实体list集合
     *
     * @param sql    查询sql
     * @param params 查询参数源
     * @return
     */
    List<T> queryBeanList(String sql, SqlParameterSource params);

    /**
     * 有参数查询实体map集合
     *
     * @param sql    查询sql
     * @param params 附加参数
     * @return
     */
    Map<String, Object> find(String sql, Object[] params);

    /**
     * 有参数查询实体list<Map>集合
     *
     * @param sql    查询sql
     * @param params 附加参数
     * @return
     */
    List<Map<String, Object>> queryList(String sql, Object[] params);

    /**
     * 统计总数
     *
     * @param sql 查询sql
     * @return
     */
    PK countTotal(String sql);

    /**
     * 删除，更新，增加操作
     *
     * @param sql    sql语句
     * @param params 附加参数
     * @return
     */
    PK update(String sql, Object... params);


    /**
     * 删除，更新，增加操作
     *
     * @param sql         sql语句
     * @param paramSource 附加参数
     * @return
     */
    PK update(String sql, SqlParameterSource paramSource);

    /**
     * 批量删除，更新，增加操作
     *
     * @param sql    sql语句
     * @param params 附加参数
     * @return
     */
    void batchUpdate(String sql, List<Object[]> params);

    /**
     * 批量删除，更新，增加操作
     *
     * @param sql
     * @param batchPreparedStatementSetter
     * @return
     */
    void batchUpdate(String sql, BatchPreparedStatementSetter batchPreparedStatementSetter);
}
