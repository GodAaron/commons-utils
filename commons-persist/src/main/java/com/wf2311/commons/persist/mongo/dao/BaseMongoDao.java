package com.wf2311.commons.persist.mongo.dao;

import com.wf2311.commons.domain.Pager;
import com.wf2311.commons.persist.lang.Condition;
import com.wf2311.commons.persist.lang.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author wf2311
 * @date 2016/03/20 20:33.
 */
public interface BaseMongoDao<T, PK extends Serializable> {
    /**
     * 按主键取得一个实体对象
     *
     * @param id
     * @return
     */
    T get(PK id);

    /**
     * 按具有唯一性的属性取得一个实体对象(实际上是取得符合条件的第一个)
     *
     * @param propertyName
     * @param value
     * @return
     */
    T getObjectByUniqueProperty(String propertyName, Object value);

    /**
     * 按条件取得一个实体对象
     *
     * @param conditions
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
     * 分页查找实体对象集合
     *
     * @param pager
     * @param conditions
     * @param orders
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
     * @param t
     */
    void insert(T t);

    /**
     * 批量保存实体对象
     *
     * @param ts
     */
    void insert(Collection<T> ts);

    /**
     * 更新符合条件的第一条实体对象
     *
     * @param conditions 封装的条件
     * @param update  要更新的内容
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
     * 按主键("_id")删除一个实体对象
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
     * 按查询条件删除实体对象
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
