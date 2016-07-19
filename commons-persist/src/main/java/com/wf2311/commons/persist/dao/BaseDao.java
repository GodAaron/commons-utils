package com.wf2311.commons.persist.dao;

import com.wf2311.commons.domain.Pager;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 指定泛型的数据访问接口, 指定泛型的通用的数据访问接口。
 *
 * @user wf2311
 * @time 2015/11/27 9:33
 */
public interface BaseDao<T> extends GenericDao {
    /**
     * 根据ID获取实体对象
     *
     * @param id 主键
     * @return T 实体对象
     */
    T get(Serializable id);

    /**
     * 保存一个实体对象，并返回存入数据库后的实体对象
     *
     * @param t 实体对象
     * @return 存入数据库后的实体对象
     */
    T saveEntity(T t);

    /**
     * 根据ID删除实体对象
     *
     * @param id 主键
     */
    void deleteById(Serializable id);

    /**
     * 根据ID列表删除
     *
     * @param ids 主键集合
     */
    void deleteAll(Collection<Serializable> ids);

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     * @return 持久队列集合
     */
    @SuppressWarnings("unchecked")
    List<T> find(final Criterion... criterions);

    /**
     * 按属性查找对象列表, 匹配方式为相等.
     *
     * @param propertyName 键
     * @param value        值
     * @return 持久队列集合
     */
    List<T> findBy(final String propertyName, final Object value);

    /**
     * 按属性查找唯一对象, 匹配方式为相等.
     *
     * @param propertyName 键
     * @param value        值
     * @return 实体对象
     */
    T findUniqueBy(final String propertyName, final Object value);

    /**
     * 按HQL查询唯一对象.
     *
     * @param hql    hql语句
     * @param values 数量可变的参数,按顺序绑定.
     * @return 实体对象
     */
    T findUnique(final String hql, final Object... values);

    /**
     * 按属性查询, 返回第一个对象
     *
     * @param criterions 数量可变的Criterion.
     * @return 结果集的第一个对象
     */
    T findFirst(final Criterion... criterions);

    /**
     * 按属性查询, 返回第一个对象
     *
     * @param propertyName 键
     * @param value        值
     * @return 结果集的第一个对象
     */
    T findFirst(final String propertyName, final Object value);

    /**
     * 按属性查询, 返回第一个对象
     *
     * @param hql    hql语句
     * @param values 数量可变的参数,按顺序绑定.
     * @return 结果集的第一个对象
     */
    T findFirst(final String hql, final Object... values);

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param hql    hql语句
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    int batchExecute(final String hql, final Object... values);

    /**
     * 创建Criteria查询对象
     *
     * @return Criteria
     */
    Criteria createCriteria();

    /**
     * 根据Criterion条件创建Criteria.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param criterions 数量可变的Criterion.
     * @return Criteria
     */
    Criteria createCriteria(final Criterion... criterions);

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

    // TODO: 2015/12/10 分页范围模糊查询

    /**
     * 查询全部实体对象
     *
     * @return 结果列表
     */
    List<T> getAll();
}
