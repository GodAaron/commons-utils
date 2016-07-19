package com.wf2311.commons.persist.mongo.dao.impl;

import com.mongodb.WriteResult;
import com.wf2311.commons.domain.Pager;
import com.wf2311.commons.persist.annotation.Repository;
import com.wf2311.commons.persist.lang.Condition;
import com.wf2311.commons.persist.lang.Order;
import com.wf2311.commons.persist.mongo.dao.BaseMongoDao;
import com.wf2311.commons.persist.mongo.lang.MongoCondition;
import com.wf2311.commons.persist.mongo.lang.MongoOrderUtil;
import com.wf2311.commons.persist.utils.BaseUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author wf2311
 * @date 2016/03/20 21:01.
 */
public abstract class BaseMongoDaoImpl<T, PK extends Serializable> implements BaseMongoDao<T, PK> {
    public abstract MongoTemplate mongoTemplate();

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
     * 统计符合条件的实体对象
     *
     * @param conditions
     * @return
     */
    @Override
    public Long getCount(Collection<Condition> conditions) {
        return getCount(getQuery(conditions));
    }

    /**
     * 统计符合条件的实体对象
     *
     * @param query
     * @return
     */
    @Override
    public Long getCount(Query query) {
        return this.mongoTemplate().count(query, this.entityClass);
    }

    /**
     * 按主键取得一个实体对象
     *
     * @param id
     * @return
     */
    @Override
    public T get(PK id) {
        return this.mongoTemplate().findById(id, this.entityClass);
    }

    /**
     * 取得所有实体对象
     *
     * @return
     */
    @Override
    public List<T> getAll() {
        return this.mongoTemplate().findAll(this.entityClass);
    }

    /**
     * 按查询条件取得实体对象
     *
     * @param query
     * @return
     */
    @Override
    public List<T> get(Query query) {
        return this.mongoTemplate().find(query, this.entityClass);
    }

    /**
     * 按具有唯一性的属性取得一个实体对象(实际上是取得符合条件的第一个)
     *
     * @param propertyName
     * @param value
     * @return
     */
    @Override
    public T getObjectByUniqueProperty(String propertyName, Object value) {
        List<T> ts = mongoTemplate().find(new Query(where(propertyName).is(value)), this.entityClass);
        return BaseUtils.checkNotNull(ts) ? ts.get(0) : null;
    }

    /**
     * 按条件取得一个实体对象
     *
     * @param conditions
     * @return
     */
    @Override
    public T getOne(Collection<Condition> conditions) {
        List<T> ts = getByCondition(conditions, null);
        return BaseUtils.checkNotNull(ts) ? ts.get(0) : null;
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
        Query query = getQuery(conditions);
        query.with(getSort(orders));
        return mongoTemplate().find(query, this.entityClass);
    }

    @Override
    public Pager<T> findByPager(Pager<T> pager, Collection<Condition> conditions, Collection<Order> orders) {
        Query query = getQuery(conditions);
        //排序
        query.with(getSort(orders));

        int count = Long.valueOf(this.mongoTemplate().count(query, this.entityClass)).intValue();
        pager.setTotalCount(count);
        if (pager.isRepair()) {
            pager.repair();
        }
        query.limit(pager.getPageSize()).skip(pager.getFirstIndex());
        List<T> contents = this.mongoTemplate().find(query, this.entityClass);
        pager.setContent(contents);
        return pager;
    }

    /**
     * 分页查找实体对象集合
     * @param pager 分页对象
     * @param query 查询条件
     * @return
     */
    @Override
    public Pager<T> findByPager(Pager<T> pager, Query query) {
        if (query == null) {
            query = new Query();
        }
        int count = Long.valueOf(this.mongoTemplate().count(query, this.entityClass)).intValue();
        pager.setTotalCount(count);
        if (pager.isRepair()) {
            pager.repair();
        }
        query.limit(pager.getPageSize()).skip(pager.getFirstIndex());
        List<T> contents = this.mongoTemplate().find(query, this.entityClass);
        pager.setContent(contents);
        return pager;
    }


    /**
     * 保存一个实体对象
     *
     * @param t
     */
    @Override
    public void insert(T t) {
        this.mongoTemplate().save(t);
    }

    /**
     * 批量保存实体对象
     *
     * @param ts
     */
    @Override
    public void insert(Collection<T> ts) {
        this.mongoTemplate().insert(ts, this.entityClass);
    }

    /**
     * 更新符合条件的第一条实体对象
     *
     * @param conditions 封装的条件
     * @param update     要更新的内容
     * @return
     */
    @Override
    public int update(Collection<Condition> conditions, Update update) {
        return this.update(getQuery(conditions), update);
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
        WriteResult wr = this.mongoTemplate().updateFirst(query, update, this.entityClass);
        return wr.getN();
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
        return this.updateMulti(getQuery(conditions), update);
    }

    /**
     * 按条件更新实体对象集合
     *
     * @param query
     * @param update
     * @return
     */
    @Override
    public int updateMulti(Query query, Update update) {
        WriteResult wr = this.mongoTemplate().updateMulti(query, update, this.entityClass);
        return wr.getN();
    }

    /**
     * 更新或者新增实体对象，如果存在符合条件的实体对象则更新，否则插入新增一个新对象
     *
     * @param query
     * @param update
     */
    @Override
    public void updateAndInsert(Query query, Update update) {
        this.mongoTemplate().upsert(query, update, this.entityClass);
    }

    /**
     * 按主键("_id")删除一个实体对象
     *
     * @param id
     */
    @Override
    public void remove(PK id) {
        Criteria criteria = Criteria.where("_id").in(id);
        if (null != criteria) {
            Query query = new Query(criteria);
            if (null != query && this.get(query) != null) {
                this.remove(query);
            }
        }
    }

    /**
     * 删除指定的实体对象
     *
     * @param t
     */
    @Override
    public void remove(T t) {
        this.mongoTemplate().remove(t);
    }

    /**
     * 按查询条件删除实体对象
     *
     * @param query
     */
    @Override
    public void remove(Query query) {
        this.mongoTemplate().remove(query, this.entityClass);
    }

    @Override
    public boolean exist(Collection<Condition> conditions) {
        return BaseUtils.checkNotNull(getByCondition(conditions, null));
    }


    private Query getQuery(Collection<Condition> conditions) {
        Query query = new Query();
        if (BaseUtils.checkNotNull(conditions)) {
            List<Criteria> criterias = generatorCriteria(conditions);
            for (Criteria c : criterias) {
                query.addCriteria(c);
            }
        }
        return query;
    }

    private Sort getSort(Collection<Order> orders) {

        if (BaseUtils.checkNotNull(orders)) {
            List<Sort.Order> os = new ArrayList<Sort.Order>();
            for (Order order : orders) {
                os.add(MongoOrderUtil.getMongoOrder(order));
            }
            return new Sort(os);
        }
        return null;
    }

    private List<Criteria> generatorCriteria(Collection<Condition> conditions) {
        List<Criteria> criterias = new ArrayList<>();
//			criterias.add(new Criteria())
        for (Condition condition : conditions) {
            criterias.add(((MongoCondition) condition).generateExpression());
        }
        return criterias;
    }
}
