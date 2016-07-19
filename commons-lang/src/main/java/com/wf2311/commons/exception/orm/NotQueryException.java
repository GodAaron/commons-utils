package com.wf2311.commons.exception.orm;

import com.wf2311.commons.exception.WfException;

/**
 * 数据库未查询到相应数据异常
 * @author wf2311
 * @time 2016/05/13 16:51.
 */
public class NotQueryException extends WfException {
    public NotQueryException() {
        super("未查询到相应数据");
        this.code = -1;
    }
}
