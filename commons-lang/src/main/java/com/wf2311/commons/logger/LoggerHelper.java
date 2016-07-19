package com.wf2311.commons.logger;

/**
 * @author wf2311
 * @time 2016/05/13 11:18.
 */
public interface LoggerHelper {
    Class<?> targetCls = null;

    void info(Object message);

    void debug(Object message);
}
