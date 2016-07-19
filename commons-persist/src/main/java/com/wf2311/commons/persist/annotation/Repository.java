package com.wf2311.commons.persist.annotation;

import java.lang.annotation.*;

/**
 * 扩展自 spring @Repository
 * <p>
 * 使用此注解时, 要开启注解配置  &lt;context:annotation-config /&gt;
 *
 * @author wf2311
 * @date 2015/11/27 9:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@org.springframework.stereotype.Repository
public @interface Repository {

    /**
     * Repository在spring容器中的实例名称
     *
     * @return string
     */
    String value() default "";

    /**
     * Repository处理的实体类
     *
     * @return class
     */
    Class<?> entity();
}
