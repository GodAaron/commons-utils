package com.wf2311.commons.logger;

import org.apache.log4j.Logger;

/**
 * @author wf2311
 * @time 2016/05/13 11:19.
 */
public class ApacheLogger extends BaseLogger implements LoggerHelper {
    private Logger logger = Logger.getLogger(targetClass);

    ApacheLogger(Class targetClass) {
        super(targetClass);
    }

    @Override
    public void info(Object message) {
        logger.info(message);
    }

    @Override
    public void debug(Object message) {
        logger.debug(message);
    }

    public void error(Object message) {
        logger.error(message);
    }

    public void track(Object message) {
        logger.trace(message);
    }
}
