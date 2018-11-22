package com.elab.core.dao;

import com.elab.core.bean.PageModel;
import com.elab.core.dao.row.ThirdRowMapper;
import com.elab.core.exception.CoreException;
import com.elab.core.serialization.SerializeFactory;
import com.elab.core.sql.SqlOperator;
import com.elab.core.sql.config.SqlKit;
import com.elab.core.utils.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 增删改查统一封装类
 *
 * @author liuhx on 2016/12/11 14:13
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class BaseDao {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private final String sqlText = " SQL - ";
    private final String sqlParamsText = " params - ";

    @Autowired
    public JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {

        if (namedParameterJdbcTemplate == null) {
            namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        }
        return namedParameterJdbcTemplate;
    }

    /**
     * 查找分页信息，面向过程，把数据库中的字段原封不动的返回出去
     *
     * @param pageModel 分页对象
     * @param sqlname   需要用的数据库名称、xml中的sqlGroup.sql
     * @param params    需要替换的参数
     * @return 多条结果集
     * @throws CoreException
     */
    public PageModel findPagingList(PageModel pageModel, String sqlname, LinkedHashMap<String, Object> params) throws CoreException {
        int totalRow = 0;
        String sql = "";
        LinkedHashMap linkedHashMap = null;
        LinkedHashMap<String, Object> originalParams = params;//原始入参
        try {
            sql = SqlOperator.findListForPaging(sqlname, originalParams);
            linkedHashMap = MapUtils.removeNullValue(originalParams);
            sql = MapUtils.questionMarkReplace(sql, linkedHashMap);
            logger.debug(sqlText + sql);
            totalRow = getNamedParameterJdbcTemplate().queryForList(sql, linkedHashMap).size();
            String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(linkedHashMap);
            logger.debug(sqlParamsText + sqlParams);
            pageModel.setRowTotal(totalRow);
        } catch (EmptyResultDataAccessException e) {
        } catch (Exception e1) {
        }
        sql = sql.concat(" limit " + (pageModel.getCount() - 1) * pageModel.getPageSize()).concat(", ").concat(pageModel.getPageSize() + "");
        logger.debug(sqlText + sql);
        String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(linkedHashMap);
        logger.debug(sqlParamsText + sqlParams);
        List<Map<String, Object>> listSqlRow = getNamedParameterJdbcTemplate().queryForList(sql, linkedHashMap);
        pageModel.setResultSet(listSqlRow);

        return pageModel;
    }

    /**
     * 查找分页信息，面向对象，把数据库中的字符串转换为java命名规范的驼峰法
     *
     * @param pageModel 分页对象
     * @param sqlname   需要用的数据库名称、xml中的sqlGroup.sql
     * @param params    需要替换的参数
     * @param clazz     结果集对象实体封装
     * @return 多条结果集
     * @throws CoreException
     */
    public <T> PageModel<T> findPagingList(PageModel pageModel, String sqlname, LinkedHashMap<String, Object> params, Class<T> clazz) throws CoreException {
        int totalRow = 0;
        String sql = "";
        LinkedHashMap linkedHashMap = null;
        LinkedHashMap<String, Object> originalParams = params;//原始入参
        try {
            sql = SqlOperator.findListForPaging(sqlname, originalParams);
            linkedHashMap = MapUtils.removeNullValue(originalParams);
            sql = MapUtils.questionMarkReplace(sql, linkedHashMap);
            String countSql = "select count(1) from (" + sql + ") c";
            totalRow = getNamedParameterJdbcTemplate().queryForObject(countSql, linkedHashMap, Integer.class);
            pageModel.setRowTotal(totalRow);
        } catch (EmptyResultDataAccessException e) {
        } catch (Exception e1) {
        }
        sql = sql.concat(" limit " + (pageModel.getCount() - 1) * pageModel.getPageSize()).concat(", ").concat(pageModel.getPageSize() + "");
        logger.debug(sqlText + sql);
        String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(linkedHashMap);
        logger.debug(sqlParamsText + sqlParams);

        List<T> listSqlRow = getNamedParameterJdbcTemplate().query(sql, linkedHashMap, new ThirdRowMapper(clazz));
        pageModel.setResultSet(listSqlRow);
        return pageModel;
    }

    /**
     * 查找多条结果集
     *
     * @param sqlname 需要用的数据库名称、xml中的sqlGroup.sql
     * @param params  需要替换的参数
     * @return 多条结果集
     */
    public List<Map<String, Object>> findList(String sqlname, LinkedHashMap<String, Object> params) throws CoreException {
        try {
            LinkedHashMap<String, Object> originalParams = params;//原始入参
            String sql = SqlOperator.query(sqlname, originalParams);
            LinkedHashMap linkedHashMap = MapUtils.removeNullValue(originalParams);
            sql = MapUtils.questionMarkReplace(sql, linkedHashMap);
            logger.debug(sqlText + sql);
            String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(linkedHashMap);
            logger.debug(sqlParamsText + sqlParams);
            List<Map<String, Object>> listSqlRow = getNamedParameterJdbcTemplate().queryForList(sql, linkedHashMap);
            return listSqlRow;
        } catch (Exception e) {
            throw new CoreException(e.getMessage());
        }
    }

    /**
     * 查找多条结果集
     *
     * @param sqlname 需要用的数据库名称、xml中的sqlGroup.sql
     * @param params  需要替换的参数
     * @return 多条结果集
     */
    public <T> List<T> findList(String sqlname, LinkedHashMap<String, Object> params, Class<T> clazz) throws CoreException {
        try {
            LinkedHashMap<String, Object> originalParams = params;//原始入参
            String sql = SqlOperator.query(sqlname, originalParams);
            LinkedHashMap linkedHashMap = MapUtils.removeNullValue(originalParams);
            sql = MapUtils.questionMarkReplace(sql, linkedHashMap);
            logger.debug(sqlText + sql);
            String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(linkedHashMap);
            logger.debug(sqlParamsText + sqlParams);

            List<T> list = getNamedParameterJdbcTemplate().query(sql, linkedHashMap, new ThirdRowMapper(clazz));
            return list;
        } catch (Exception e) {
            throw new CoreException(e.getMessage());
        }
    }

    /**
     * 查找集合数据
     *
     * @param sqlname   sql对应编号
     * @param params    参数
     * @param rowMapper 实现RowMapper对象传入即可实现手动赋值
     * @param <T>
     * @return
     * @throws CoreException
     */
    public <T> List<T> findList(String sqlname, LinkedHashMap<String, Object> params, RowMapper<T> rowMapper) throws CoreException {
        try {
            LinkedHashMap<String, Object> originalParams = params;//原始入参
            String sql = SqlOperator.query(sqlname, originalParams);
            LinkedHashMap linkedHashMap = MapUtils.removeNullValue(originalParams);
            sql = MapUtils.questionMarkReplace(sql, linkedHashMap);
            logger.debug(sqlText + sql);
            String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(linkedHashMap);
            logger.debug(sqlParamsText + sqlParams);
            List<T> list = getNamedParameterJdbcTemplate().query(sql, linkedHashMap, rowMapper);
            return list;
        } catch (Exception e) {
            throw new CoreException(e.getMessage());
        }
    }

    /**
     * 查找单条记录
     *
     * @param sqlname 需要用的数据库名称、xml中的sqlGroup.sql
     * @param params  需要替换的参数
     * @return 单条结果集
     */
    public Map<String, Object> findUnique(String sqlname, LinkedHashMap<String, Object> params) throws CoreException {
        try {
            LinkedHashMap<String, Object> originalParams = params;//原始入参
            String sql = SqlOperator.query(sqlname, originalParams);
            LinkedHashMap linkedHashMap = MapUtils.removeNullValue(originalParams);
            sql = MapUtils.questionMarkReplace(sql, linkedHashMap);
            logger.debug(sqlText + sql);
            String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(linkedHashMap);
            logger.debug(sqlParamsText + sqlParams);
            Map<String, Object> sqlRow = getNamedParameterJdbcTemplate().queryForMap(sql, linkedHashMap);
            return sqlRow;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedHashMap();
        } catch (Exception e) {
            throw new CoreException(e.getMessage());
        }
    }

    /**
     * 查找单条记录
     *
     * @param sqlname 需要用的数据库名称、xml中的sqlGroup.sql
     * @param params  需要替换的参数
     * @param clazz   实体类
     * @return 单条结果集
     */
    public <T> T findUnique(String sqlname, LinkedHashMap<String, Object> params, Class<T> clazz) throws CoreException {
        try {
            LinkedHashMap<String, Object> originalParams = params;//原始入参
            String sql = SqlOperator.query(sqlname, originalParams);
            LinkedHashMap linkedHashMap = MapUtils.removeNullValue(originalParams);
            sql = MapUtils.questionMarkReplace(sql, linkedHashMap);
//            Map<String, Object> sqlRow = getNamedParameterJdbcTemplate().queryForMap(sql, linkedHashMap);
            logger.debug(sqlText + sql);
            String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(linkedHashMap);
            logger.debug(sqlParamsText + sqlParams);

            T obj = (T) getNamedParameterJdbcTemplate().queryForObject(sql, linkedHashMap, new ThirdRowMapper(clazz));
            return obj;
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new CoreException(e.getMessage());
        }
    }

    /**
     * 新增数据
     *
     * @param sqlname 需要用的数据库名称、xml中的sqlGroup.sql
     * @param params  需要替换的参数
     * @return 数据库表的主键id
     */
    public int executeInsert(String sqlname, LinkedHashMap<String, Object> params) throws CoreException {
        try {
            LinkedHashMap<String, Object> originalParams = params;//原始入参
            String sql = SqlOperator.executeInsert(sqlname, originalParams);
            LinkedHashMap linkedHashMap = MapUtils.removeNullValue(originalParams);
            sql = MapUtils.questionMarkReplace(sql, linkedHashMap);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            logger.debug(sqlText + sql);
            String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(linkedHashMap);
            logger.debug(sqlParamsText + sqlParams);
            getNamedParameterJdbcTemplate().update(sql, new MapSqlParameterSource(linkedHashMap), keyHolder);
            int id = keyHolder.getKey().intValue();
            return id;
        } catch (Exception e) {
            throw new CoreException(e.getMessage());
        }
    }

    /**
     * 修改数据
     *
     * @param sqlname 需要用的数据库名称、xml中的sqlGroup.sql
     * @param params  需要替换的参数
     * @return 修改记录数
     */
    public int executeUpdate(String sqlname, LinkedHashMap<String, Object> params) throws CoreException {
        try {
            LinkedHashMap<String, Object> originalParams = params;//原始入参
            String sql = SqlOperator.executeUpdate(sqlname, originalParams);
//            LinkedHashMap linkedHashMap = MapUtils.removeNullValue(params);
            sql = MapUtils.questionMarkReplace(sql, originalParams);
            logger.debug(sqlText + sql);
            String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(originalParams);
            logger.debug(sqlParamsText + sqlParams);
            int count = getNamedParameterJdbcTemplate().update(sql, originalParams);
            logger.debug(" 成功条数 : " + count);
            return count;
        } catch (Exception e) {
            throw new CoreException(e.getMessage());
        }
    }


    public int executeUpdate(String sqlname, Object obj) throws CoreException {
        try {
            String sql = SqlKit.sql(sqlname);
            SqlParameterSource ps = new BeanPropertySqlParameterSource(obj);
            int count = getNamedParameterJdbcTemplate().update(sql, ps);
            logger.debug(sqlText + sql);
            String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(obj);
            logger.debug(sqlParamsText + sqlParams + " 成功条数 : " + count);
            return count;
        } catch (Exception e) {
            throw new CoreException(e.getMessage());
        } finally {
        }
    }


    /**
     * 普通删除数据,传绝对值，不允许传null值
     *
     * @param sqlname 需要用的数据库名称、xml中的sqlGroup.sql
     * @param params  需要替换的参数
     * @return 修改记录数
     */
    public int executeDelete(String sqlname, LinkedHashMap<String, Object> params) throws CoreException {
        try {
            String sql = SqlOperator.executeDelete(sqlname, params);
            LinkedHashMap linkedHashMap = MapUtils.removeNullValue(params);
            sql = MapUtils.questionMarkReplace(sql, linkedHashMap);
            logger.debug(sqlText + sql);
            String sqlParams = SerializeFactory.getJsonSerializer().ToSerializedString(linkedHashMap);
            logger.debug(sqlParamsText + sqlParams);
            int count = getNamedParameterJdbcTemplate().update(sql, linkedHashMap);
            return count;
        } catch (Exception e) {
            throw new CoreException(e.getMessage());
        }
    }
}
