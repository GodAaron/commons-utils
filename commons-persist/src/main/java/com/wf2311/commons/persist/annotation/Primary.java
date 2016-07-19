package com.wf2311.commons.persist.annotation;

import java.lang.annotation.*;

/**
 * 标注主键id
 * @author wf2311
 * @date 2016/03/08 21:24.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Primary {
}
