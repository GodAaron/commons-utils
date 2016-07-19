package com.wf2311.commons.persist.dao.impl;

import com.wf2311.commons.domain.Pager;
import com.wf2311.commons.persist.dao.GenericDao;
import com.wf2311.commons.persist.lang.Message;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 通用持久层基类
 *
 * @user wf2311
 * @time 2015/11/27 9:34
 */
public abstract class GenericDaoImpl implements GenericDao {
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory() = sessionFactory;
//    }
//    @Resource(name = "sessionFactory")
//    protected SessionFactory sessionFactory;

    /**
     * 使用的SessionFactory
     */
    public abstract SessionFactory sessionFactory();

    /**
     * 启用Filter
     *
     * @param name filterName
     */
    protected void enableFilter(String name) {
        session().enableFilter(name);
    }

    /**
     * 获得当前上下文Session
     *
     * @return Session
     */
    protected Session session() {
        return this.sessionFactory().getCurrentSession();
    }

    /**
     * 持久化实体对象
     *
     * @param entity 实体对象
     */
    @Override
    public Serializable save(Object entity) {
        Assert.notNull(entity, Message.ENTITY_NULL);
        return session().save(entity).hashCode();
    }

    /**
     * 删除持久化对象
     *
     * @param entity 实体对象
     */
    @Override
    public void delete(Object entity) {
        Assert.notNull(entity, Message.ENTITY_NULL);
        session().delete(entity);
    }

    /**
     * 修改持久化对象
     *
     * @param entity 实体对象
     */
    @Override
    public void update(Object entity) {
        Assert.notNull(entity, Message.ENTITY_NULL);
        session().update(entity);
    }

    /**
     * 添加/修改
     *
     * @param entity 实体对象
     */
    @Override
    public void saveOrUpdate(Object entity) {
        Assert.notNull(entity, Message.ENTITY_NULL);
        session().saveOrUpdate(entity);
    }

    /**
     * refresh持久化对象
     *
     * @param entity 实体对象
     */
    @Override
    public void refresh(Object entity) {
        Assert.notNull(entity, Message.ENTITY_NULL);
        session().refresh(entity);
    }

    /**
     * merge
     *
     * @param entity 实体对象
     */
    @Override
    public Object merge(Object entity) {
        Assert.notNull(entity, Message.ENTITY_NULL);
        return session().merge(entity);
    }

    /**
     * get 查询
     *
     * @param clazz 实体类
     * @param <E>   实体类类型
     * @param id    主键
     * @return 实体对象
     */
    @SuppressWarnings("unchecked")
    public <E> E get(Class<E> clazz, Serializable id) {
        return (E) session().get(clazz, id);
    }

    /**
     * 根据主键删除
     *
     * @param clazz 实体类
     * @param id    主键
     */
    protected void deleteById(Class<?> clazz, Serializable id) {
        Session s = session();
        Object obj = s.get(clazz, id);
        if (obj != null) {
            s.delete(obj);
        }
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param hql    hql语句
     * @param values 数量可变的参数,按顺序绑定.
     * @return Query
     */
    public Query createQuery(final String hql, final Object... values) {
        Assert.hasText(hql, Message.HQL_NULL);
        Query query = session().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 创建带缓存的HQL查询
     *
     * @param hql hql语句
     * @return Query
     */
    public Query createQuery(String hql) {
        return createQuery(hql, true, null);
    }

    /**
     * 创建HQL查询，通过传入参数设置是否可缓存
     *
     * @param hql       hql语句
     * @param cacheable 设置是否可缓存
     * @return Query
     */
    public Query createQuery(String hql, boolean cacheable) {
        return createQuery(hql, cacheable, null);
    }

    /**
     * 创建HQL查询，通过传入参数设置是否可缓存
     *
     * @param hql         hql语句
     * @param cacheable   设置是否可缓存
     * @param cacheRegion 缓存保存区域
     * @return Query
     */
    public Query createQuery(String hql, boolean cacheable, String cacheRegion) {
        Query q = session().createQuery(hql).setCacheable(true);
        if (cacheable && cacheRegion != null) {
            q.setCacheRegion(cacheRegion);
        }
        return q;
    }

    /**
     * 创建Native SQL查询
     *
     * @param sql sql语句
     * @return SQLQuery
     */
    public SQLQuery createSQLQuery(String sql) {
        return session().createSQLQuery(sql);
    }

    /**
     * 创建带缓存的QBC查询
     *
     * @param clazz 查询的实体类型
     * @return Criteria
     */
    public Criteria createCriteria(Class<?> clazz) {
        return session().createCriteria(clazz).setCacheable(true);
    }

    /**
     * 创建分页查询
     *
     * @param pager 分页对象
     * @param clazz 返回实体对象类
     * @param <E>   实体类类型
     * @return PagerQuery
     */
    public <E> PagerQuery<E> pagerQuery(Pager pager, Class<E> clazz) {
        return new PagerQuery<>(pager, clazz, null);
    }

    /**
     * 创建分页查询
     *
     * @param pager       分页对象
     * @param clazz       返回实体对象类
     * @param <E>         实体类类型
     * @param cacheRegion 缓存保存区域
     * @return PagerQuery
     */
    public <E> PagerQuery<E> pagerQuery(Pager pager, Class<E> clazz, String cacheRegion) {
        return new PagerQuery<>(pager, clazz, cacheRegion);
    }

    public class PagerQuery<T> implements Serializable {
        private static final long serialVersionUID = 8307596869106859651L;
        String cacheRegion;
        Class<T> clazz;

        QueryFilter filter = new QueryFilter();
        LinkedList<Order> orderBuffer = new LinkedList<>();
        ResultTransformer resultTransformer;
        Pager pager;

        public PagerQuery(Pager pager, Class<T> clazz, String cacheRegion) {
            this.clazz = clazz;
            this.cacheRegion = cacheRegion;
            this.pager = pager;
        }

        public PagerQuery<T> add(Order order) {
            if (order != null)
                orderBuffer.add(order);
            return this;
        }

        public PagerQuery<T> asc(String field) {
            if (StringUtils.isNotBlank(field))
                add(Order.asc(field));
            return this;
        }

        public PagerQuery<T> desc(String field) {
            if (StringUtils.isNotBlank(field))
                add(Order.desc(field));
            return this;
        }

        public PagerQuery<T> alias(String field, String alias) {
            if (StringUtils.isNotBlank(field) || StringUtils.isNotBlank(alias))
                filter.alias(field, alias);
            return this;
        }

        public PagerQuery<T> add(Criterion c) {
            if (c != null)
                filter.add(c);
            return this;
        }

        public PagerQuery<T> setResultTransformer(ResultTransformer resultTransformer) {
            if (resultTransformer != null)
                this.resultTransformer = resultTransformer;
            return this;
        }

        protected Criteria criteria(boolean sortable) {
            Criteria c = createCriteria(clazz);

            filter.doFilter(c);

            if (cacheRegion != null) {
                c.setCacheRegion(cacheRegion);
            }

            if (sortable && !orderBuffer.isEmpty()) {
                for (Order order : orderBuffer) {
                    c.addOrder(order);
                }
//                orderBuffer.forEach(o -> c.addOrder(o));
            }
            return c;
        }

        /**
         * execute query
         *
         * @return result
         */
        @SuppressWarnings("unchecked")
        public List<T> list() {
            boolean hasData = true;
            // count
            if (pager.isCount()) {
                Criteria ctr = criteria(false);
                int totalRows = ((Number) ctr.setProjection(Projections.rowCount()).uniqueResult()).intValue();
                pager.setTotalCount(totalRows);
                if (totalRows <= pager.getFirstIndex()) {
                    hasData = false;
                }
            }

            // query data
            List<T> results = Collections.emptyList();
            if (hasData) {
                Criteria ctr = criteria(true);
                //当page和row的值为指定值时，不分页、查询所有数据列
                if (pager.getPageIndex() != Pager.GET_ALL_PAGE && pager.getPageSize() != Pager.GET_ALL_ROW) {
                    ctr.setFirstResult(pager.getFirstIndex()).setMaxResults(pager.getPageSize());
                }
                if (resultTransformer != null) {
                    ctr.setResultTransformer(resultTransformer);
                }
                results = ctr.list();
            }
            pager.setContent(results);
            return results;
        }

        public Pager getPager() {
            list();
            return this.pager;
        }
    }

    /**
     * PagerQuery filter
     */
    public class QueryFilter {
        LinkedList<Criterion> criterions = new LinkedList<>();
        LinkedHashMap<String, String> aliases = new LinkedHashMap<>();

        public QueryFilter add(Criterion c) {
            criterions.add(c);
            return this;
        }

        public QueryFilter alias(String field, String alias) {
            aliases.put(field, alias);
            return this;
        }

        public void doFilter(Criteria criteria) {
            if (!criterions.isEmpty()) {
                if (!aliases.isEmpty()) {
                    for (Map.Entry<String, String> map : aliases.entrySet()) {
                        criteria.createAlias(map.getKey(), map.getValue());
                    }
//                    aliases.forEach((k, v) -> criteria.createAlias(k, v));
                }
//                criterions.forEach(ct -> criteria.add(ct));
                for (Criterion criterion : criterions) {
                    criteria.add(criterion);
                }
            }
        }
    }
}
