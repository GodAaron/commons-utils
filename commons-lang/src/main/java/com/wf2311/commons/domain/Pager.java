package com.wf2311.commons.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *基本分页组件
 * @author wf2311
 * @date 2016/03/10 18:46:07
 */
public class Pager<T> implements Serializable {
    /**
     * 查询所有数据时page值
     */
    public static final int GET_ALL_PAGE = -111;
    /**
     * 查询所有数据时row值
     */
    public static final int GET_ALL_ROW = -1111;
    /**
     * 默认每页数据集大小
     */
    protected static final int PAGE_SIZE = 10;

    /**
     * 查询起始页码
     */
    protected int pageIndex = 1;
    /**
     * 当pageSize小于0时,为查询所有
     */
    protected int pageSize = PAGE_SIZE;
    /**
     * 符合查询条件数据总数
     */
    protected long totalCount;
    /**
     * 当前页第一个数据在totalCount中的位置
     */
    protected int firstIndex;
    /**
     * 当前查询页中的数据
     */
    protected List<T> content = Collections.EMPTY_LIST;

    /**
     * 是否统计总数(默认开启)
     */
    protected boolean count = true;

    /**
     * 当前页码大于总页码时，是否修改其值为总页码
     */
    protected boolean repair = true;

    /**
     * 生成分页实体，每页数据集大小为10
     *
     * @param pageIndex 当前页码
     */
    public Pager(int pageIndex) {
        this(pageIndex, PAGE_SIZE);
    }

    /**
     * 生成分页实体
     *
     * @param pageIndex 当前页面
     * @param pageSize  每页数据集大小
     */
    public Pager(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.content = new ArrayList<T>();
    }

    /**
     * <p>此方法用于处理以下情况:当前页码大于总页码时，修改其值为总页码</p>
     * <strong>注意:</strong>
     * <red>此方法必须在先设置了totalCount的值后，并且查询content之前使用</red>
     */
    public void repair() {
        //当前页码大于总页码时，修改其值为总页码
        if (this.pageIndex > getTotalPage()) {
            this.pageIndex = getTotalPage();
        }
    }

    /**
     * 是否启用repair方法,@link{this#repair()}
     *
     * @return
     */
    public boolean isRepair() {
        return repair;
    }

    public void setRepair(boolean repair) {
        this.repair = repair;
    }

    /**
     * @return 当前页码
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * @return 每页数据集(最大)大小
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @return 总页码
     */
    public int getTotalPage() {
        int pages = (int) (totalCount / pageSize);
        if (totalCount % pageSize != 0) {
            pages++;
        }
        return pages;
    }

    /**
     * @return 当前页面的数据集实际大小
     */
    public int getSize() {
        return content.size();
    }

    /**
     * 上一个有效页码
     * @return 当前页的上一页的页码，如果当前页为第一页时返回1
     */
    public int getPrev(){
        return (pageIndex > 1 ? (pageIndex - 1) : 1);
    }

    /**
     * 下一个有效页码
     * @return 当前页的下一页的页码，如果当前页 为最后一页时返回最后一页
     */
    public int getNext(){
        int tp = getTotalPage();
        return (pageIndex < tp ? (pageIndex + 1) : tp);
    }

    /**
     * @return 总数据集大小
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总数据集大小
     *
     * @param totalCount 总数据集大小
     */
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return 当前数据的第一条在所有数据总的位置
     */
    public int getFirstIndex() {
        this.firstIndex = (getPageIndex() - 1) * getPageSize();
        return firstIndex >= 0 ? firstIndex : 0;
    }

    /**
     * 设置当前数据的第一条在所有数据总的位置
     * <p>一般情况不会使用</p>
     *
     * @param firstIndex
     */
    public void setFirstIndex(int firstIndex) {
        this.firstIndex = firstIndex;
    }

    /**
     * @return 获取当前页面的数据集内容
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * 设置当前页面的数据集内容
     *
     * @param content 数据集内容
     */
    public void setContent(List<T> content) {
        this.content = content;
    }

    /**
     * 为当前页面的数据集添加内容
     *
     * @param mc 要添加的数据集内容
     */
    public void addContent(T mc) {
        content.add(mc);
    }

    /**
     * 是否统计总数(默认开启)
     */
    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }
}
