package com.wf2311.commons.logger;

/**
 * @author wf2311
 * @time 2016/05/13 11:37.
 */
public class BaseLogger {
    protected Class<?> targetClass;

    BaseLogger(Class targetClass) {
        this.targetClass = targetClass;
    }
}
