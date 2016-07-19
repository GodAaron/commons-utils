package com.wf2311.commons.persist.interceptor;

import com.wf2311.commons.persist.annotation.Domain;
import com.wf2311.commons.web.BaseUserInfo;
import com.wf2311.commons.web.RequestUserInfo;
import org.apache.commons.beanutils.MethodUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 更新数据时拦截，更新数据更新时间
 *
 * @author wf2311
 * @time 2016/03/25 21:02.
 */
@Aspect //声明这是一个组件
@Component  //声明这是一个切面Bean
public class UpdateInterceptor extends Interceptor {
    @Pointcut("@annotation(com.wf2311.commons.persist.interceptor.annotation.Update)")
    public void aspect() {
    }

    @Before("aspect()")
    public void before(JoinPoint joinPoint) {
        aop(joinPoint);
    }

    public void aop(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        BaseUserInfo userInfo = getCurrentUserInfo();
        for (Object arg : args) {
            Class cls = arg.getClass();
            if (cls.isAnnotationPresent(Domain.class)) {
                try {
                    if (MethodUtils.invokeMethod(arg, "getModified", null) == null) {
                        MethodUtils.invokeMethod(arg, "setModified", new Date());
                    }
                    MethodUtils.invokeMethod(arg, "setModifier", userInfo.toString());
//                    if (MethodUtils.invokeMethod(arg, "getModifier", null) == null) {
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
