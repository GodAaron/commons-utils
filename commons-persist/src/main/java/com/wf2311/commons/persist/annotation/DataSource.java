package com.wf2311.commons.persist.annotation;

import java.lang.annotation.*;

/**
 *
 * @author wf2311
 * @date 2016/03/10 23:28.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value() default "";
}
