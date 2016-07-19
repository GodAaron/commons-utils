package com.wf2311.commons.persist.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.io.Serializable;

/**
 * 通用数据访问接口, 非泛型的通用的数据访问接口。
 *
 * @user wf2311
 * @time 2015/11/27 9:33
 */
public interface GenericDao {
    /**
     * 保存数据对象，用于新建
     *
     * @param entity 实体对象
     */
    Serializable save(Object entity);

    /**
     * 删除数据对象
     *
     * @param entity 实体对象
     */
    void delete(Object entity);

    /**
     * 更新数据对象，用于修改
     *
     * @param entity 实体对象
     */
    void update(Object entity);

    /**
     * 创建或修改数据对象
     *
     * @param entity 实体对象
     */
    void saveOrUpdate(Object entity);

    /**
     * 从数据库重新读取对象
     *
     * @param entity 实体对象
     */
    void refresh(Object entity);

    /**
     * 如果存在则更新, 不存在则insert
     * 此处需要注意 merge会先执行get请求, 如果存在则将entity属性覆盖到持久实例, entity 本身依旧是游离态.
     *
     * @param entity 持久实例
     * @return Object
     */
    Object merge(Object entity);

    /**
     * 创建Query查询
     *
     * @param queryString sql语句
     * @param values      参数列表
     * @return Query
     */
    Query createQuery(final String queryString, final Object... values);

    /**
     * 创建带缓存的HQL查询
     *
     * @param hql hql语句
     * @return Query
     */
    Query createQuery(String hql);

    /**
     * 创建HQL查询，通过传入参数设置是否可缓存
     *
     * @param hql       hql语句
     * @param cacheable 设置是否可缓存
     * @return Query
     */
    Query createQuery(String hql, boolean cacheable);

    /**
     * 创建HQL查询，通过传入参数设置是否可缓存
     *
     * @param hql         hql语句
     * @param cacheable   设置是否可缓存
     * @param cacheRegion 缓存保存区域
     * @return Query
     */
    Query createQuery(String hql, boolean cacheable, String cacheRegion);

    /**
     * 创建带缓存的QBC查询
     *
     * @param clazz 查询的实体类型
     * @return Criteria
     */
    Criteria createCriteria(Class<?> clazz);

    /**
     * 创建Native SQL查询
     *
     * @param sql sql语句
     * @return SQLQuery
     */
    SQLQuery createSQLQuery(String sql);

}
