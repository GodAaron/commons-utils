package com.wf2311.commons.persist.interceptor;

import com.wf2311.commons.id.IdCreator;
import com.wf2311.commons.persist.annotation.Domain;
import com.wf2311.commons.persist.annotation.Primary;
import com.wf2311.commons.web.BaseUserInfo;
import com.wf2311.commons.web.RequestUserInfo;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 数据时拦截，更新数据更新时间
 * @author wf2311
 * @time 2016/03/25 21:01.
 */
@Aspect //声明这是一个组件
@Component  //声明这是一个切面Bean
@Scope
public class InsertInterceptor extends Interceptor {

    private IdCreator idCreator = new IdCreator(1, 1);

    @Pointcut("@annotation(com.wf2311.commons.persist.interceptor.annotation.Insert)")
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
                    if (MethodUtils.invokeMethod(arg, "getCreated", null) == null) {
                        MethodUtils.invokeMethod(arg, "setCreated", new Date());
                    }
                    if (MethodUtils.invokeMethod(arg, "getCreator", null) == null) {
                        MethodUtils.invokeMethod(arg, "setCreator", userInfo.toString());
                    }
                    Field[] fields = cls.getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Primary.class)) {
                            String name = field.getName();
                            String getMethodName = "get" + StringUtils.capitalize(name);
                            if (MethodUtils.invokeExactMethod(arg, getMethodName, null) == null) {
                                String setMethodName = "set" + StringUtils.capitalize(name);
                                long primary = idCreator.nextId();
                                MethodUtils.invokeMethod(arg, setMethodName, primary);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
