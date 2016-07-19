package com.wf2311.commons.persist.service.impl;

import com.wf2311.commons.domain.Pager;
import com.wf2311.commons.persist.dao.BaseDao;
import com.wf2311.commons.persist.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @user wf2311
 * @time 2015/11/27 9:55
 */
@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    public abstract BaseDao<T> baseDao();

    /**
     * 根据主键得到指定的实体对象
     *
     * @param id 主键
     * @return 指定的实体对象
     */
    @Override
    public T get(Serializable id) {
        return baseDao().get(id);
    }


    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<T> getAll() {
        return baseDao().getAll();
    }

    /**
     * 按属性查找唯一对象, 匹配方式为相等.
     *
     * @param propertyName 键
     * @param value        值
     * @return 实体对象
     */
    @Override
    public T findUniqueBy(String propertyName, Object value) {
        return baseDao().findUniqueBy(propertyName, value);
    }

    /**
     * 保存一个实体对象
     *
     * @param t 实体对象
     * @return
     */
    @Override
    public void save(T t) {
        baseDao().save(t);
    }

    /**
     * 保存一个实体对象，并返回操作后重新获取的实体对象
     *
     * @param t 实体对象
     * @return 保存后重新获取的实体对象信息
     */
    @Override
    public T saveEntity(T t) {
        return baseDao().saveEntity(t);
    }

    /**
     * 批量保存指定的实体对象
     *
     * @param ts 实体对象集合
     */
    @Override
    public void saveAll(Collection<T> ts) {
        for (T t : ts) {
            save(t);
        }
    }

    /**
     * 保存或更新实体对象
     *
     * @param t 指定的实体对象
     */
    @Override
    public void saveOrUpdate(T t) {
        baseDao().saveOrUpdate(t);
    }

    /**
     * 保存或更新实体对象，并返回操作后重新获取的实体对象
     *
     * @param t 指定的实体对象
     * @return 并返回操作后重新获取的实体对象
     */
    @Override
    public T saveOrUpdateEntity(T t) {
        baseDao().saveOrUpdate(t);
        return t;
    }

    /**
     * 批量保存或更新指定的实体对象
     *
     * @param ts 实体对象集合
     */
    @Override
    public void saveOrUpdateAll(Collection<T> ts) {
        for (T t : ts) {
            saveOrUpdate(t);
        }
    }

    /**
     * 更新一个实体对象
     *
     * @param t 指定的实体对象
     */
    @Override
    public void update(T t) {
        baseDao().update(t);
    }

    /**
     * 批量更新实体对象
     *
     * @param ts 指定的实体对象集合
     */
    @Override
    public void updateAll(Collection<T> ts) {
        for (T t : ts) {
            update(t);
        }
    }

    /**
     * 根据主键删除一个实体对象
     *
     * @param id 指定的实体对象主键
     */
    @Override
    public void deleteById(Serializable id) {
        baseDao().deleteById(id);
    }

    /**
     * 删除一个实体对象
     *
     * @param t 指定的实体对象
     */
    @Override
    public void delete(T t) {
        baseDao().delete(t);
    }

    /**
     * 批量删除实体对象
     *
     * @param ts 指定的实体对象集合
     */
    @Override
    public void deleteAll(Collection<T> ts) {
        for (T t : ts) {
            delete(t);
        }
    }

    /**
     * 分页查询实体集合
     *
     * @param pager        分页对象
     * @param propertyName 排序字段(实体对象属性名)
     * @param order        排序方式：asc/desc
     * @param key          排序字段order的模糊查询字符串
     * @return
     */
    @Override
    public Pager pager(Pager pager, String propertyName, String order, String key) {
        return baseDao().pager(pager, propertyName, order, key);
    }
}
