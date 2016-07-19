package com.wf2311.commons.persist.lang;

import com.wf2311.commons.domain.Pager;
import com.wf2311.commons.lang.plugin.MapContainer;

/**
 * 数据库分页组件,添加查询参数insertQuery 在sql中不用写limit语句
 * <P>该方法目前未想到使用场景</P>
 *
 * @author wf2311
 * @date 2016/03/20 18:35.
 */
public class QueryPager<T> extends Pager<T> {
    /**
     * 查询参数
     */
    private MapContainer query;

    /**
     * 生成分页实体，每页数据集大小为10
     *
     * @param pageIndex 当前页码
     */
    public QueryPager(int pageIndex) {
        this(pageIndex, PAGE_SIZE);
    }

    /**
     * 生成分页实体
     *
     * @param pageIndex 当前页面
     * @param pageSize  每页数据集大小
     */
    public QueryPager(int pageIndex, int pageSize) {
        super(pageIndex, pageSize);
        this.query = new MapContainer();
    }


    public Pager<T> insertQuery(String key, Object value) {
        query.put(key, value);
        return this;
    }

    public Pager<T> insertQuerys(MapContainer map) {
        if (map != null)
            query.putAll(map);

        return this;
    }

    public MapContainer getQuery() {
        return query;
    }

    public Object removeQuery(String key) {
        return query.remove(key);
    }

    public boolean isQueryAll() {
        return pageSize < 1;
    }

    /**
     * 生成查询数量sql
     *
     * @return
     */
    public String countSql(String sql) {
        // int index = query.getSql().indexOf(" from ");
        // String sql = "select count(*) " + query.getSql().substring(index);
        // index = sql.indexOf("order by");
        // sql = index == -1 ? sql : sql.substring(0, index);
        // /* 只要有group by就使用子查询 */
        // if(sql.indexOf("group by") != -1){
        // sql = "select count(*) from ( " + sql + " ) _temp_";
        // }

        return "select count(*) from ( " + sql + " ) _temp_";
    }

    /**
     * 生成分页sql
     *
     * @return
     */
    public String pageSql(String sql) {
        return sql + " limit " + (getPageIndex() - 1) * getPageSize() + "," + getPageSize();
    }
}
