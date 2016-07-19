package com.wf2311.commons.persist.interceptor;

import com.wf2311.commons.web.BaseUserInfo;
import com.wf2311.commons.web.RequestUserInfo;
import org.aspectj.lang.JoinPoint;

/**
 * @author wf2311
 * @time 2016/06/08 14:13.
 */
public abstract class Interceptor {
    abstract void aspect();

    abstract void before(JoinPoint joinPoint);

    abstract void aop(JoinPoint joinPoint);

    public BaseUserInfo getCurrentUserInfo() {
        return RequestUserInfo.getUserInfo();
    }
}
