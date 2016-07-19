package com.wf2311.commons.persist.dao.impl;

import com.wf2311.commons.domain.Pager;
import com.wf2311.commons.lang.Consts;
import com.wf2311.commons.persist.annotation.Repository;
import com.wf2311.commons.persist.dao.BaseDao;
import com.wf2311.commons.persist.lang.Message;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 持久层基类, 通过泛型指定实体类
 * <p>
 * 基类中的 entityClass 在使用的时候必须被赋值, 详情见 {@link com.wf2311.commons.persist.annotation.Repository} 注解
 *
 * @user wf2311
 * @time 2015/11/27 9:34
 */
public abstract class BaseDaoImpl<T> extends GenericDaoImpl implements BaseDao<T> {
    private static Logger logger = Logger.getLogger(BaseDaoImpl.class);

    /**
     * 实体类型
     */
    protected Class<T> entityClass;

    /**
     * 自动提取 entityClass
     *
     * @throws Exception 出错
     */
    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() throws Exception {
        Repository repository = this.getClass().getAnnotation(Repository.class);

        Assert.notNull(repository, this.getClass() + " 必须要使用" + Repository.class + "注解!");
        Assert.notNull(repository.entity(), this.getClass() + " 的 @Repository注解的 entity 不能为空!");

        this.entityClass = (Class<T>) repository.entity();
    }

    /**
     * 通过主键删除对象
     *
     * @param id 主键
     */
    @Override
    public void deleteById(Serializable id) {
        deleteById(entityClass, id);
    }

    /**
     * 根据ID列表删除
     *
     * @param ids 主键集合
     */
    @Override
    public void deleteAll(Collection<Serializable> ids) {
        Assert.notNull(ids, Message.IDS_NULL);
        for(Serializable id:ids){
            deleteById(id);
        }
//        ids.forEach(id -> deleteById(id));
    }

    /**
     * 通过主键获取数据对象
     *
     * @param id 主键
     * @return 持久对象
     */
    @Override
    public T get(Serializable id) {
        return get(entityClass, id);
    }

    @Override
    public T saveEntity(T t) {
        try {
            save(t);
            return t;
        } catch (Exception e) {
            logger.error("保存错误：" + e.getMessage());
            return null;
        }
    }

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     * @return 持久队列集合
     */
    @SuppressWarnings("unchecked")
    public List<T> find(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    /**
     * 按属性查找对象列表, 匹配方式为相等.
     *
     * @param propertyName 键
     * @param value        值
     * @return 持久队列集合
     */
    public List<T> findBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, Message.PROPERTYNAME_NULL);
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }

    /**
     * 按属性查找唯一对象, 匹配方式为相等.
     *
     * @param propertyName 键
     * @param value        值
     * @return 实体对象
     */
    @SuppressWarnings("unchecked")
    public T findUniqueBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, Message.PROPERTYNAME_NULL);
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(criterion).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param hql    hql语句
     * @param values 数量可变的参数,按顺序绑定.
     * @return 实体对象
     */
    @SuppressWarnings("unchecked")
    public T findUnique(final String hql, final Object... values) {
        return (T) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按属性查询, 返回第一个对象
     *
     * @param criterions 数量可变的Criterion.
     * @return 结果集的第一个对象
     */
    public T findFirst(final Criterion... criterions) {
        List<T> rets = find(criterions);
        if (rets != null && rets.size() > 0) {
            return rets.get(0);
        }
        return null;
    }

    /**
     * 按属性查询, 返回第一个对象
     *
     * @param propertyName 键
     * @param value        值
     * @return 结果集的第一个对象
     */
    public T findFirst(final String propertyName, final Object value) {
        List<T> rets = findBy(propertyName, value);
        if (rets != null && rets.size() > 0) {
            return rets.get(0);
        }
        return null;
    }

    /**
     * 按属性查询, 返回第一个对象
     *
     * @param hql    hql语句
     * @param values 数量可变的参数,按顺序绑定.
     * @return 结果集的第一个对象
     */
    @SuppressWarnings("unchecked")
    public T findFirst(final String hql, final Object... values) {
        List<T> rets = createQuery(hql, values).list();
        if (rets != null && rets.size() > 0) {
            return rets.get(0);
        }
        return null;
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param hql    hql语句
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 创建Criteria查询对象
     *
     * @return Criteria
     */
    public Criteria createCriteria() {
        return createCriteria(entityClass);
    }

    /**
     * 根据Criterion条件创建Criteria.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param criterions 数量可变的Criterion.
     * @return Criteria
     */
    public Criteria createCriteria(final Criterion... criterions) {
        Criteria criteria = session().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 创建PagerQuery查询对象
     *
     * @param pager 分页对象
     * @return PagerQuery
     */
    public PagerQuery<T> pagerQuery(Pager pager) {
        return pagerQuery(pager, entityClass, null);
    }

    /**
     * 创建PagerQuery查询对象
     *
     * @param pager      分页对象
     * @param criterions 数量可变的Criterion.
     * @return PagerQuery
     */
    public PagerQuery<T> pagerQuery(Pager pager, Criterion... criterions) {
        PagerQuery<T> q = pagerQuery(pager, entityClass, null);

        for (Criterion c : criterions) {
            q.add(c);
        }
        return q;
    }

    /**
     * 创建PagerQuery查询对象
     *
     * @param pager       分页对象
     * @param cacheRegion 缓存保存区域
     * @return PagerQuery
     */
    public PagerQuery<T> pagerQuery(Pager pager, String cacheRegion) {
        return pagerQuery(pager, entityClass, cacheRegion);
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
    public Pager pager(Pager pager, String propertyName, String order, String key) {
        PagerQuery<T> pagerQuery = pagerQuery(pager);
        pagerQuery.add(order(propertyName, order));
        if (StringUtils.isNotBlank(key)) {
            pagerQuery.add(Restrictions.like(propertyName, key, MatchMode.ANYWHERE));
        }
        return pagerQuery.getPager();
    }

    /**
     * 查询所有 (谨慎使用)
     *
     * @return 集合
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return createCriteria().list();
    }

    /**
     * @param propertyName 排序字段(实体对象属性名)
     * @param order        排序方式：asc/desc
     * @return
     */
    protected Order order(String propertyName, String order) {

        Order o = null;
        if (StringUtils.isNotBlank(propertyName)) {
            if (order.equalsIgnoreCase(Consts.DESC)) {
                o = Order.asc(propertyName);
            } else {
                o = Order.desc(propertyName);
            }
        }
        return o;
    }
}
