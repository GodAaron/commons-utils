package com.wf2311.commons.persist.service;


import com.wf2311.commons.domain.Pager;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @user wf2311
 * @time 2015/11/27 9:54
 */
public interface BaseService<T> {
    /**
     * 根据主键得到指定的实体对象
     *
     * @param id 主键
     * @return 指定的实体对象
     */
    T get(Serializable id);

    /**
     * 查询所有
     * @return
     */
    List<T> getAll();

    /**
     * 按属性查找唯一对象, 匹配方式为相等.
     *
     * @param propertyName 键
     * @param value        值
     * @return 实体对象
     */
    T findUniqueBy(final String propertyName, final Object value);

    /**
     * 保存一个实体对象
     *
     * @param t 实体对象
     * @return
     */
    void save(T t);

    /**
     * 保存一个实体对象，并返回操作后重新获取的实体对象
     *
     * @param t 实体对象
     * @return 保存后重新获取的实体对象信息
     */
    T saveEntity(T t);

    /**
     * 批量保存指定的实体对象
     *
     * @param ts 实体对象集合
     */
    void saveAll(Collection<T> ts);

    /**
     * 保存或更新实体对象
     *
     * @param t 指定的实体对象
     */
    void saveOrUpdate(T t);

    /**
     * 保存或更新实体对象，并返回操作后重新获取的实体对象
     *
     * @param t 指定的实体对象
     * @return 并返回操作后重新获取的实体对象
     */
    T saveOrUpdateEntity(T t);

    /**
     * 批量保存或更新指定的实体对象
     *
     * @param ts 实体对象集合
     */
    void saveOrUpdateAll(Collection<T> ts);

    /**
     * 更新一个实体对象
     *
     * @param t 指定的实体对象
     */
    void update(T t);

    /**
     * 批量更新实体对象
     *
     * @param ts 指定的实体对象集合
     */
    void updateAll(Collection<T> ts);

    /**
     * 根据主键删除一个实体对象
     *
     * @param id 指定的实体对象主键
     */
    void deleteById(Serializable id);

    /**
     * 删除一个实体对象
     *
     * @param t 指定的实体对象
     * @return 是否删除成功
     */
    void delete(T t);

    /**
     * 批量删除实体对象
     *
     * @param ts 指定的实体对象集合
     */
    void deleteAll(Collection<T> ts);

    /**
     * 分页查询实体集合
     *
     * @param pager        分页对象
     * @param propertyName 排序字段(实体对象属性名)
     * @param order        排序方式：asc/desc
     * @param key          排序字段order的模糊查询字符串
     * @return
     */
    Pager pager(Pager pager, String propertyName, String order, String key);
}
