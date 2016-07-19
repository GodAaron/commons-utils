package com.wf2311.commons.json;

import com.alibaba.fastjson.serializer.NameFilter;

import java.util.HashMap;

/**
 * 自定义属性名序列化<p/>
 * names中key为原属性名，value为新属性名
 *
 * @author wf2311
 * @date 2015/12/14 13:28
 */
public class SimpleNameFilter implements NameFilter {
    private HashMap<String, String> names = new HashMap<>();

    public SimpleNameFilter(HashMap<String, String> names) {
        this.names = names;
    }

    public HashMap<String, String> getNames() {
        return names;
    }

    @Override
    public String process(Object object, String name, Object value) {
        if (this.names.containsKey(name)) {
            return this.names.get(name);
        }
        return name;
    }
}
