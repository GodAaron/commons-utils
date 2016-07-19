package com.wf2311.commons.persist.lang;

import java.util.List;

/**
 * @author: wf2311
 * @date: 2015/9/22 8:40
 */
public class EasyUIPager<T> {
    private int total;
    private List<T> rows;

    public EasyUIPager() {
    }

    public EasyUIPager(int total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
