package com.wf2311.commons.search.lucene5.exception;


import com.wf2311.commons.exception.WfException;

/**
 * Lucene索引操作异常
 *
 * @author wf2311
 * @date 2016/03/13 14:26.
 */
public class LuceneException extends WfException {

    private static final long serialVersionUID = 1L;

    public LuceneException(String msg) {
        super(msg);
    }

    public LuceneException(String msg, Throwable t) {
        super(msg, t);
    }
}
