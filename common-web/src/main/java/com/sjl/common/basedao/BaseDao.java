package com.sjl.common.basedao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.sjl.common.exception.BaseDaoException;
import com.sjl.common.page.Page;
import com.sjl.common.page.SqlPageHandle;

/**
 * JdbcTemplate basedao
 * 
 * @author song
 *
 * @param <T>
 */
@Repository("baseDao")
public class BaseDao<T> implements IBaseDao<T, Integer> {
	private static final Logger LOGGER = Logger.getLogger(BaseDao.class);
	private static boolean DEBUG = false;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Resource
	protected SqlPageHandle sQLPageHandle;

	protected Class<T> entityClass;

	@Override
	public T queryBean(String sql) {
		T t;
		try {
			t = jdbcTemplate.queryForObject(sql, getRowMapper());
		} catch (DataAccessException | InstantiationException | IllegalAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {// 修复size == 0
				return null;
			}
			LOGGER.error("queryBean异常:" + sql, e);
			throw new BaseDaoException("queryBean异常",e);//抛给全局异常
		}
		return t;
	}

	// 验证通过,jdbcTemplet.queryforobject 如果返回为null，或者多个对象。会报异常！！！
	@Override
	public T queryBean(String sql, Object... params) {
		T t;
		try {
			t = jdbcTemplate.queryForObject(sql, params, getRowMapper());
		} catch (DataAccessException | InstantiationException | IllegalAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {
				return null;
			}
			LOGGER.error("queryBean异常:" + sql, e);
			throw new BaseDaoException("queryBean异常",e);
		}
		return t;
	}

	// 验证通过
	@Override
	public <R> R queryRequiredType(String sql, Class<R> requiredType, Object... args) {
		R t;
		try {
			t = jdbcTemplate.queryForObject(sql, requiredType, args);
		} catch (Exception e) {
			if (e instanceof EmptyResultDataAccessException) {
				return null;
			}
			LOGGER.error("queryRequiredType异常:" + sql, e);
			throw new BaseDaoException("queryRequiredType查询异常",e);
		}
		return t;
	}

	// 验证通过
	@Override
	public List<T> queryBeanList(String sql) {
		List<T> t = Collections.emptyList();
		try {
			t = jdbcTemplate.query(sql, getRowMapper());
		} catch (Exception e) {
			LOGGER.error("queryBeanList异常:" + sql, e);
			throw new BaseDaoException("queryBeanList异常",e);
		}
		return t;
	}

	// 验证通过
	@Override
	public List<T> queryBeanList(String sql, Object... params) {
		List<T> t = Collections.emptyList();
		try {
			t = jdbcTemplate.query(sql, params, getRowMapper());
		} catch (Exception e) {
			LOGGER.error("queryBeanList异常:" + sql, e);
			throw new BaseDaoException("queryBeanList异常",e);
		}
		return t;
	}

	// 验证通过
	@Override
	public List<T> queryBeanList(String sql, SqlParameterSource params) {
		List<T> t = Collections.emptyList();
		try {
			t = namedParameterJdbcTemplate.query(sql, params, getRowMapper());
		} catch (Exception e) {
			LOGGER.error("queryBeanList异常:" + sql, e);
			throw new BaseDaoException("queryBeanList异常",e);
		}
		return t;
	}

	@Override
	public Map<String, Object> find(String sql, Object[] params) {
		return jdbcTemplate.queryForMap(sql, params);
	}

	@Override
	public List<Map<String, Object>> queryList(String sql, Object[] params) {
		return jdbcTemplate.queryForList(sql, params);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer countTotal(String sql) {
		int count = jdbcTemplate.queryForInt(sql);
		return count;
	}

	// 验证通过
	@Override
	public Page<T> queryPagination(String sql, Object[] params, int pageNo, int pageSize) {
		if(isDEBUG()){
			LOGGER.info("分页查询："+ sql);
		}
		
		List<T> list = Collections.emptyList();
		List<T> totalList = Collections.emptyList();
		// 将SQL语句进行分页处理
		String newSql = sQLPageHandle.handlerPagingSQL(sql, pageNo, pageSize);
		if (params == null || params.length <= 0) {
			// 数据库表字段和实体类自动对应，可以使用BeanPropertyRowMapper
			// https://blog.csdn.net/qq_22339269/article/details/82978717
			totalList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(getEntityClass()));
			list = jdbcTemplate.query(newSql, new BeanPropertyRowMapper<T>(getEntityClass()));
		} else {
			totalList = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<T>(getEntityClass()));
			list = jdbcTemplate.query(newSql, params, new BeanPropertyRowMapper<T>(getEntityClass()));
		}
		// 根据参数的个数进行差别查询
		Page<T> page = new Page<T>(pageNo, pageSize, totalList == null || totalList.isEmpty() ? 0 : totalList.size(),
				list);
		return page;
	}

	// 验证通过
	@Override
	public Integer update(String sql, Object... params) {
		int ret = -1;
		try {
			if (params == null || params.length == 0) {
				ret = jdbcTemplate.update(sql);
			} else {
				ret = jdbcTemplate.update(sql, params);
			}
		} catch (Exception e) {
			LOGGER.error("update异常:" + sql, e);
			throw new BaseDaoException("update异常",e);
		}
		return ret;
	}

	@Override
	public Integer update(String sql, SqlParameterSource paramSource) {
		int ret = namedParameterJdbcTemplate.update(sql, paramSource);
		return ret;
	}

	// 验证通过
	@Override
	public void batchUpdate(String sql, List<Object[]> params) {
		try {
			jdbcTemplate.batchUpdate(sql, params);
		} catch (DataAccessException e) {
			LOGGER.error("batchUpdate异常:" + sql, e);
			throw new BaseDaoException("batchUpdate异常",e);
		}
	}

	@Override
	public void batchUpdate(String sql, BatchPreparedStatementSetter batchPreparedStatementSetter) {
		try {
			jdbcTemplate.batchUpdate(sql, batchPreparedStatementSetter);
		} catch (DataAccessException e) {
			LOGGER.error("batchUpdate异常:" + sql, e);
			throw new BaseDaoException("batchUpdate异常",e);
		}
	}

	@SuppressWarnings("unchecked")
	private RowMapper<T> getRowMapper() throws InstantiationException, IllegalAccessException {
		return (RowMapper<T>) getEntityClass().newInstance();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Class getEntityClass() {
		if (entityClass == null) {
			// clazz.getGenericSuperclass();
			// 得到泛型父类 ParameterizedType是参数化类型
			// getActualTypeArguments获取参数化类型的数组，可能有多个
			Type genericSuperclass = this.getClass().getGenericSuperclass();
			if(isDEBUG()){
				LOGGER.info("genericSuperclass:" + genericSuperclass.getTypeName());
			}
			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
			if(isDEBUG()){
				LOGGER.info("TypeName:" + pt.getTypeName());
			}
			entityClass = (Class<T>) pt.getActualTypeArguments()[0];
		}
		return entityClass;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	// 下面方法是扩展，使用
	// BeanPropertyRowMapper自动映射实体类，实体类无须实现RowMapper接口，前提是表的列名和类的属性名必须一致或者下划线分隔的列名也行

	public List<T> queryBeanList(String sql, Object[] params, Class<T> tClass) {
		List<T> resultList = Collections.emptyList();
		try {
			if (params != null && params.length > 0)
				resultList = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<T>(tClass));
			else
				// BeanPropertyRowMapper是自动映射实体类的
				resultList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(tClass));
		} catch (Exception e) {
			LOGGER.error("update异常:"+sql, e);
			throw new BaseDaoException("update异常",e);
		}
		return resultList;
	}

	public int update(String sql, final Object[] params, Class<T> tClass) {
		int num = -1;
		try {
			if (params == null || params.length == 0)
				num = jdbcTemplate.update(sql);
			else
				num = jdbcTemplate.update(sql, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						for (int i = 0; i < params.length; i++)
							ps.setObject(i + 1, params[i]);
					}
				});
		} catch (Exception e) {
			LOGGER.error("update异常:"+sql, e);
			throw new BaseDaoException("update异常",e);
		}
		return num;

	}

	/**
	 * @param sql
	 * @param args
	 * @param classT 注意该参数，jdbcTemplate.queryForObject传入的不能是自定义的classType，
	 *            如果是自定义的，需要经过new
	 *            BeanPropertyRowMapper<T>(classT)转换，默认支持的只有比如String，int等类型
	 * @param <T>
	 * @return
	 */
	public T queryBean(String sql, Object[] args, Class<T> classT) {
		if (sql == null || sql.length() <= 0) {
			return null;
		}
		if (args == null || args.length <= 0) {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(classT));
		}
		return jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<T>(classT));
	}

	public static void setDEBUG(boolean dEBUG) {
		DEBUG = dEBUG;
	}

	public static boolean isDEBUG() {
		return DEBUG;
	}
	
	
}
