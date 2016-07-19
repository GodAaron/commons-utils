package com.wf2311.commons.exception.orm;

import com.wf2311.commons.exception.WfException;

/**
 * 实体类主键未赋值
 *
 * @author wf2311
 * @time 2016/04/22 17:29.
 */
public class NullPrimaryException extends WfException {
    public NullPrimaryException() {
        super("主键未赋值");
        this.code=-1;
    }
}
