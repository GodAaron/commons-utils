package com.wf2311.commons.persist.mongo.service.impl;

import com.wf2311.commons.domain.Pager;
import com.wf2311.commons.persist.lang.Condition;
import com.wf2311.commons.persist.lang.Order;
import com.wf2311.commons.persist.mongo.dao.BaseMongoDao;
import com.wf2311.commons.persist.mongo.service.BaseMongoService;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author wf2311
 * @date 2016/03/20 22:19.
 */
public abstract class BaseMongoServiceImpl<T, PK extends Serializable> implements BaseMongoService<T, PK> {
    /**
     *
     * @return
     */
    public abstract BaseMongoDao<T, PK> baseMongoDao();
    /**
     * 根据主键查找id
     *
     * @param id
     * @return
     */
    @Override
    public T get(PK id) {
        return this.baseMongoDao().get(id);
    }

    /**
     * 根据具有惟一性的属性获取实体对象(实际上是取得符合条件的第一个)
     *
     * @param propertyName 属性名称
     * @param value        属性值
     * @return 唯一实体对象
     */
    @Override
    public T getObjectByUniqueProperty(String propertyName, Object value) {
        return this.baseMongoDao().getObjectByUniqueProperty(propertyName, value);
    }

    /**
     * 根据条件获取单个实体对象
     *
     * @param conditions 条件集合
     * @return
     */
    @Override
    public T getOne(Collection<Condition> conditions) {
        return this.baseMongoDao().getOne(conditions);
    }

    /**
     * 按条件取得实体对象
     *
     * @param conditions
     * @param orders
     * @return
     */
    @Override
    public List<T> getByCondition(Collection<Condition> conditions, Collection<Order> orders) {
        return this.baseMongoDao().getByCondition(conditions, orders);
    }

    /**
     * 取得所有实体对象
     *
     * @return
     */
    @Override
    public List<T> getAll() {
        return this.baseMongoDao().getAll();
    }

    /**
     * 按查询条件取得实体对象
     *
     * @param query
     * @return
     */
    @Override
    public List<T> get(Query query) {
        return this.baseMongoDao().get(query);
    }

    /**
     * 分页查找实体对象集合
     *
     * @param pager 分页对象
     * @param query 查询条件
     * @return
     */
    @Override
    public Pager<T> findByPager(Pager<T> pager, Query query) {
        return this.baseMongoDao().findByPager(pager, query);
    }

    /**
     * 分页查找实体对象集合
     *
     * @param pager
     * @param conditions
     * @param orders
     * @return
     */
    @Override
    public Pager<T> findByPager(Pager<T> pager, Collection<Condition> conditions, Collection<Order> orders) {
        return this.baseMongoDao().findByPager(pager, conditions, orders);
    }

    /**
     * 保存一个实体对象
     *
     * @param object
     */
    @Override
    public void insert(T object) {
        this.baseMongoDao().insert(object);
    }

    /**
     * 批量保存实体对象
     *
     * @param objects
     */
    @Override
    public void insert(Collection<T> objects) {
        this.baseMongoDao().insert(objects);
    }

    /**
     * 更新符合条件的第一条实体对象
     *
     * @param conditions
     * @param update
     * @return
     */
    @Override
    public int update(Collection<Condition> conditions, Update update) {
        return this.baseMongoDao().update(conditions, update);
    }

    /**
     * 更新符合条件的第一条实体对象
     *
     * @param query  条件
     * @param update 要更新的内容
     * @return
     */
    @Override
    public int update(Query query, Update update) {
        return this.baseMongoDao().update(query, update);
    }

    /**
     * 按条件更新实体对象集合
     *
     * @param query  条件
     * @param update 要更新的内容
     * @return
     */
    @Override
    public int updateMulti(Query query, Update update) {
        return this.baseMongoDao().updateMulti(query, update);
    }

    /**
     * 按条件更新实体对象集合
     *
     * @param conditions 封装的条件
     * @param update     要更新的内容
     * @return
     */
    @Override
    public int updateMulti(Collection<Condition> conditions, Update update) {
        return this.baseMongoDao().updateMulti(conditions, update);
    }

    /**
     * 统计符合条件的实体对象
     *
     * @param conditions
     * @return
     */
    @Override
    public Long getCount(Collection<Condition> conditions) {
        return this.baseMongoDao().getCount(conditions);
    }

    /**
     * 统计符合条件的实体对象
     *
     * @param query
     * @return
     */
    @Override
    public Long getCount(Query query) {
        return this.baseMongoDao().getCount(query);
    }

    /**
     * 根据主键删除对象
     *
     * @param id
     */
    @Override
    public void remove(PK id) {
        this.baseMongoDao().remove(id);
    }

    /**
     * 删除指定的实体对象
     *
     * @param t
     */
    @Override
    public void remove(T t) {
        this.baseMongoDao().remove(t);
    }

    /**
     * 按查询条件删除对象
     *
     * @param query
     */
    @Override
    public void remove(Query query) {
        this.baseMongoDao().remove(query);
    }

    /**
     * 更新或者新增实体对象，如果存在符合条件的实体对象则更新，否则插入新增一个新对象
     * @param query
     * @param update
     */
    @Override
    public void updateAndInsert(Query query, Update update) {
        this.baseMongoDao().updateAndInsert(query, update);
    }

    /**
     * 查询是否存在符合条件的实体对象
     *
     * @param conditions
     * @return
     */
    @Override
    public boolean exist(Collection<Condition> conditions) {
        return this.baseMongoDao().exist(conditions);
    }
}
