package com.wf2311.commons.persist.mongo.service;

import com.wf2311.commons.domain.Pager;
import com.wf2311.commons.persist.lang.Condition;
import com.wf2311.commons.persist.lang.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * MongoDB基本Service接口
 * @author wf2311
 * @date 2016/03/20 22:17.
 */
public interface BaseMongoService<T, PK extends Serializable> {
    /**
     * 根据主键查找id
     *
     * @param id
     * @return
     */
    T get(PK id);

    /**
     * 根据具有惟一性的属性获取实体对象
     *
     * @param propertyName 属性名称
     * @param value        属性值
     * @return 唯一实体对象
     */
    T getObjectByUniqueProperty(String propertyName, Object value);

    /**
     * 根据条件获取单个实体对象
     *
     * @param conditions 条件集合
     * @return
     */
    T getOne(Collection<Condition> conditions);

    /**
     * 按条件取得实体对象
     *
     * @param conditions
     * @param orders
     * @return
     */
    List<T> getByCondition(Collection<Condition> conditions, Collection<Order> orders);

    /**
     * 取得所有实体对象
     *
     * @return
     */
    List<T> getAll();

    /**
     * 按查询条件取得实体对象
     *
     * @param query
     * @return
     */
    List<T> get(Query query);

    /**
     * 分页查询实体类集合
     * @param conditions
     * @param pager
     * @return
     */
    Pager<T> findByPager(Pager<T> pager, Collection<Condition> conditions, Collection<Order> orders);

    /**
     * 分页查找实体对象集合
     *
     * @param pager 分页对象
     * @param query 查询条件
     * @return
     */
    Pager<T> findByPager(Pager<T> pager, Query query);

    /**
     * 保存一个实体对象
     *
     * @param object
     */
    void insert(T object);

    /**
     * 批量保存实体对象
     *
     * @param objects
     */
    void insert(Collection<T> objects);

    /**
     * 更新符合条件的第一条实体对象
     *
     * @param conditions
     * @param update
     * @return
     */
    int update(Collection<Condition> conditions, Update update);


    /**
     * 更新符合条件的第一条实体对象
     *
     * @param query  条件
     * @param update 要更新的内容
     * @return
     */
    int update(Query query, Update update);

    /**
     * 按条件更新实体对象集合
     *
     * @param query  条件
     * @param update 要更新的内容
     * @return
     */
    int updateMulti(Query query, Update update);

    /**
     * 按条件更新实体对象集合
     * @param conditions 封装的条件
     * @param update  要更新的内容
     * @return
     */
    int updateMulti(Collection<Condition> conditions, Update update);

    /**
     * 统计符合条件的实体对象
     *
     * @param conditions
     * @return
     */
    Long getCount(Collection<Condition> conditions);

    /**
     * 统计符合条件的实体对象
     *
     * @param query
     * @return
     */
    Long getCount(Query query);

    /**
     * 根据主键删除对象
     *
     * @param id
     */
    void remove(PK id);

    /**
     * 删除指定的实体对象
     *
     * @param t
     */
    void remove(T t);

    /**
     * 按查询条件删除对象
     *
     * @param query
     */
    void remove(Query query);

    /**
     * 更新或者新增实体对象，如果存在符合条件的实体对象则更新，否则插入新增一个新对象
     * @param query
     * @param update
     */
    void updateAndInsert(Query query, Update update);

    /**
     * 查询是否存在符合条件的实体对象
     *
     * @param conditions
     * @return
     */
    boolean exist(Collection<Condition> conditions);
}
