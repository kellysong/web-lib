package com.sjl.common.basedao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.sjl.common.page.Page;

/**
 * 
 * @author song
 * @param <T>
 * @param <PK>
 */
interface IBaseDao<T, PK extends Serializable> {

	/**
	 * 无参数查询实体
	 * 
	 * @param sql
	 *            查询sql
	 * @return
	 */
	T queryBean(String sql);

	/**
	 * 有参数查询实体
	 * 
	 * @param sql
	 *            查询sql
	 * @param params
	 *            附加参数
	 * @return
	 */
	T queryBean(String sql, Object... params);
	
	/**
	 * 获取某个记录某列或者count、avg、sum等函数返回唯一值
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 */
	public <R> R queryRequiredType(String sql, Class<R> requiredType, Object... args);

	/**
	 * 分页查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param params
	 *            分页查询参数
	 * @param pageNo
	 *            第几页，用来计算first 这个值由（pageNo-1）*pageSize
	 * @param pageSize
	 *            每页数量
	 * @return
	 */
	Page<T> queryPagination(String sql, Object[] params, int pageNo, int pageSize);

	/**
	 * 无参数查询实体list集合
	 * 
	 * @param sql
	 *            查询sql
	 * @return
	 * @throws Exception 
	 */
	List<T> queryBeanList(String sql) throws Exception;

	/**
	 * 有参数查询实体list集合
	 * 
	 * @param sql
	 *            查询sql
	 * @param params
	 *            附加参数
	 * @return
	 */
	List<T> queryBeanList(String sql, Object... params);
	
	/**
	 * 有参数查询实体list集合
	 * @param sql 查询sql
	 * @param params  查询参数源
	 * @return
	 */
	public List<T> queryBeanList(String sql, SqlParameterSource params);

	/**
	 * 有参数查询实体map集合
	 * 
	 * @param sql
	 *            查询sql
	 * @param params
	 *            附加参数
	 * @return
	 */
	Map<String, Object> find(String sql, Object[] params);

	/**
	 * 有参数查询实体list<Map>集合
	 * 
	 * @param sql
	 *            查询sql
	 * @param params
	 *            附加参数
	 * @return
	 */
	List<Map<String, Object>> queryList(String sql, Object[] params);
	
	/**
	 * 统计总数
	 * @param sql 查询sql
	 * @return
	 */
	PK countTotal(String sql);
	
	/**
	 * 删除，更新，增加操作
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            附加参数
	 * @return
	 */
	PK update(String sql, Object... params);
	

	/**
	 * 删除，更新，增加操作
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            附加参数
	 * @return
	 */
	PK update(String sql, SqlParameterSource paramSource);
	
	/**
	 *  批量删除，更新，增加操作
	 * @param sql sql语句
	 * @param params 附加参数
	 * @return
	 */
	void batchUpdate(String sql, List<Object[]> params);
	
	/**
	 * 批量删除，更新，增加操作
	 * @param sql
	 * @param batchPreparedStatementSetter
	 * @return
	 */
	void batchUpdate(String sql, BatchPreparedStatementSetter batchPreparedStatementSetter);
}
