package com.wf2311.commons.json;

import com.alibaba.fastjson.serializer.PropertyFilter;
import org.hibernate.collection.internal.PersistentSet;

import java.util.HashSet;
import java.util.Set;

/**
 * 主要用于过滤不需要序列化的属性，或者包含需要序列化的属性
 *
 * @author: wf2311
 * @date: 2015/8/4 11:22
 */
public class SimplePropertyFilter implements PropertyFilter {

    private final Set<String> includes = new HashSet<>();
    private final Set<String> excludes = new HashSet<>();

    public Set<String> getIncludes() {
        return includes;
    }

    public Set<String> getExcludes() {
        return excludes;
    }

    public boolean apply(Object source, String name, Object value) {
        if (value != null && (value instanceof PersistentSet)) {// 避免hibernate对象循环引用，一切Set属性不予序列化
            return false;
        }
        if (excludes.contains(name)) {
            return false;
        }
        if (excludes.contains(source.getClass().getSimpleName() + "." + name)) {
            return false;
        }
        if (includes.size() == 0 || includes.contains(name)) {
            return true;
        }
        return includes.contains(source.getClass().getSimpleName() + "." + name);
    }

}