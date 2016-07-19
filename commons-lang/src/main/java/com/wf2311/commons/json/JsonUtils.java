package com.wf2311.commons.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wf2311.commons.lang.Consts;

import java.util.Arrays;

//import org.apache.log4j.Logger;

/**
 * @author wf2311
 * @date 2015/12/11 17:27
 */
public class JsonUtils {
    //    private static final Logger logger = Logger.getLogger(JsonUtils.class);
    public JSONObject jsonObject = new JSONObject();

    public String writeJson(Data data) {
        return writeJsonByFilter(data, null, null);
    }

    public String writeJsonByFilter(Data data, String[] includesProperties, String[] excludesProperties) {
        if (data.getCode() != null) {
            jsonObject.put(Consts.KEY_CODE, data.getCode());
        }
        if (data.getMessage() != null) {
            jsonObject.put(Consts.KEY_MESSAGE, data.getMessage());
        }
        SimplePropertyFilter filter = new SimplePropertyFilter();// excludes优先于includes
        if (data.getData() != null) {
            if (excludesProperties != null && excludesProperties.length > 0) {
                filter.getExcludes().addAll(Arrays.asList(excludesProperties));
            }
            if (includesProperties != null && includesProperties.length > 0) {
                filter.getIncludes().addAll(Arrays.asList(includesProperties));
            }
            jsonObject.put(Consts.KEY_DATA, JSONObject.toJSON(data.getData()));
        }
        // 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd hh24:mi:ss
        return JSON.toJSONString(jsonObject, filter, SerializerFeature.WriteDateUseDateFormat);
    }

    public String writeJsonByFilter(Object object, String[] includesProperties, String[] excludesProperties) {
        SimplePropertyFilter filter = new SimplePropertyFilter();// excludes优先于includes
        if (object != null) {
            if (excludesProperties != null && excludesProperties.length > 0) {
                filter.getExcludes().addAll(Arrays.asList(excludesProperties));
            }
            if (includesProperties != null && includesProperties.length > 0) {
                filter.getIncludes().addAll(Arrays.asList(includesProperties));
            }
        }
        // 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd hh24:mi:ss
        return JSON.toJSONString(jsonObject, filter, SerializerFeature.WriteDateUseDateFormat);
    }

    public String writeJsonByIncludesProperties(Data data, String[] includesProperties) {
        return writeJsonByFilter(data, includesProperties, null);
    }

    public String writeJsonByExcludesProperties(Data data, String[] excludesProperties) {
        return writeJsonByFilter(data, null, excludesProperties);
    }
}
