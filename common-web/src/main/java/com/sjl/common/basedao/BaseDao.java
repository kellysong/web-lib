package com.sjl.common.basedao;

import com.sjl.common.exception.BaseDaoException;
import com.sjl.common.page.Page;
import com.sjl.common.page.SqlPageHandle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * JdbcTemplate BaseDao
 * <p>BeanPropertyRowMapper自动映射实体类，实体类无须实现RowMapper接口，前提是表的列名和类的属性名必须一致或者下划线分隔的列名也行</p>
 * <p>不能直接注入BaseDao，继承使用</p>
 *
 * @param <T>
 * @author song
 */
public class BaseDao<T> implements IBaseDao<T> {
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
        return (T) queryBean(sql, null, getEntityClass());
    }


    @Override
    public T queryBean(String sql, Object... params) {
        return (T) queryBean(sql, params, getEntityClass());
    }

    @Override
    public <T> T queryBean(String sql, Object[] params, Class<T> classT) {
        return queryBean(sql, params, new BeanPropertyRowMapper<T>(classT));
    }

    @Override
    public <T> T queryBean(String sql, Object[] params, RowMapper<T> rowMapper) {
        T t;
        try {
            if (params == null || params.length == 0) {
                t = jdbcTemplate.queryForObject(sql, rowMapper);
            } else {
                t = jdbcTemplate.queryForObject(sql, params, rowMapper);
            }
        } catch (Exception e) {
            if (e instanceof EmptyResultDataAccessException) {
                return null;
            }
            if (isDEBUG()) {
                LOGGER.error("queryBean异常:" + sql, e);
            }

            throw new BaseDaoException("queryBean异常", e);
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
            if (isDEBUG()) {
                LOGGER.error("queryRequiredType异常:" + sql, e);
            }

            throw new BaseDaoException("queryRequiredType查询异常", e);
        }
        return t;
    }

    // 验证通过
    @Override
    public List<T> queryBeanList(String sql) {
        return queryBeanList(sql, null, getEntityClass());
    }

    // 验证通过
    @Override
    public List<T> queryBeanList(String sql, Object... params) {
        return queryBeanList(sql, params, getEntityClass());
    }

    @Override
    public <T> List<T> queryBeanList(String sql, Object[] params, Class<T> classT) {
        return queryBeanList(sql, params, new BeanPropertyRowMapper<T>(classT));
    }


    @Override
    public <T> List<T> queryBeanList(String sql, Object[] params, RowMapper<T> rowMapper) {
        List<T> resultList = Collections.emptyList();
        try {
            if (params != null && params.length > 0)
                resultList = jdbcTemplate.query(sql, params, rowMapper);
            else
                resultList = jdbcTemplate.query(sql, rowMapper);
        } catch (Exception e) {
            if (isDEBUG()) {
                LOGGER.error("queryBeanList异常:" + sql, e);
            }
            throw new BaseDaoException("queryBeanList异常", e);
        }
        return resultList;
    }


    // 验证通过
    @Override
    public List<T> queryBeanList(String sql, SqlParameterSource params) {
        List<T> t = Collections.emptyList();
        try {
            t = namedParameterJdbcTemplate.query(sql, params, getBeanRowMapper());
        } catch (Exception e) {
            if (isDEBUG()) {
                LOGGER.error("queryBeanList异常:" + sql, e);
            }
            throw new BaseDaoException("queryBeanList异常", e);
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
    public int countTotal(String sql) {
        int count = jdbcTemplate.queryForInt(sql);
        return count;
    }

    // 验证通过
    @Override
    public Page<T> queryPagination(String sql, Object[] params, int pageNo, int pageSize) {
        return queryPagination(sql, params, pageNo, pageSize, getEntityClass());
    }

    @Override
    public <T> Page<T> queryPagination(String sql, Object[] params, int pageNo, int pageSize, Class<T> classT) {
        return queryPagination(sql, params, pageNo, pageSize, new BeanPropertyRowMapper<T>(classT));
    }

    @Override
    public <T> Page<T> queryPagination(String sql, Object[] params, int pageNo, int pageSize, RowMapper<T> rowMapper) {
        if (isDEBUG()) {
            LOGGER.info("分页查询：" + sql);
        }

        List<T> list = Collections.emptyList();
        List<T> totalList = Collections.emptyList();
        // 将SQL语句进行分页处理
        String newSql = sQLPageHandle.handlerPagingSQL(sql, pageNo, pageSize);
        if (params == null || params.length <= 0) {
            // 数据库表字段和实体类自动对应，可以使用BeanPropertyRowMapper
            // https://blog.csdn.net/qq_22339269/article/details/82978717
            totalList = jdbcTemplate.query(sql, rowMapper);
            list = jdbcTemplate.query(newSql, rowMapper);
        } else {
            totalList = jdbcTemplate.query(sql, params, rowMapper);
            list = jdbcTemplate.query(newSql, params, rowMapper);
        }
        // 根据参数的个数进行差别查询
        Page<T> page = new Page<T>(pageNo, pageSize, totalList == null || totalList.isEmpty() ? 0 : totalList.size(),
                list);
        return page;
    }


    // 验证通过
    @Override
    public int update(String sql, Object... params) {
        int ret = -1;
        try {
            if (params == null || params.length == 0) {
                ret = jdbcTemplate.update(sql);
            } else {
                ret = jdbcTemplate.update(sql, params);
            }
        } catch (Exception e) {
            LOGGER.error("update异常:" + sql, e);
            throw new BaseDaoException("update异常", e);
        }
        return ret;
    }


    @Override
    public long updateWithKey(String sql, Object... params) {
        if (params == null) {
            throw new NullPointerException("params 不能为空");
        }
        //自动生成int array of JDBC types
        int[] types = new int[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            if (param == null) {
                types[i] = SqlTypeValue.TYPE_UNKNOWN;
                continue;
            }
            types[i] = StatementCreatorUtils.javaTypeToSqlParameterType(param.getClass());
        }
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(sql, types);
        //设置PreparedStatement是否返回自动生成的主键
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        //获取PreparedStatementCreator
        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(String sql, SqlParameterSource paramSource) {
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
            throw new BaseDaoException("batchUpdate异常", e);
        }
    }

    @Override
    public void batchUpdate(String sql, BatchPreparedStatementSetter batchPreparedStatementSetter) {
        try {
            jdbcTemplate.batchUpdate(sql, batchPreparedStatementSetter);
        } catch (DataAccessException e) {
            LOGGER.error("batchUpdate异常:" + sql, e);
            throw new BaseDaoException("batchUpdate异常", e);
        }
    }


    private RowMapper<T> getBeanRowMapper() {
        return new BeanPropertyRowMapper<T>(getEntityClass());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Class getEntityClass() {
        if (entityClass == null) {
            // clazz.getGenericSuperclass();
            // 得到泛型父类 ParameterizedType是参数化类型
            // getActualTypeArguments获取参数化类型的数组，可能有多个
            Type genericSuperclass = this.getClass().getGenericSuperclass();
            if (isDEBUG()) {
                LOGGER.info("genericSuperclass:" + genericSuperclass.getTypeName());
            }
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            if (isDEBUG()) {
                LOGGER.info("TypeName:" + pt.getTypeName());
            }
            entityClass = (Class<T>) pt.getActualTypeArguments()[0];
        }
        return entityClass;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }


    public static void setDEBUG(boolean dEBUG) {
        DEBUG = dEBUG;
    }

    public static boolean isDEBUG() {
        return DEBUG;
    }


}
