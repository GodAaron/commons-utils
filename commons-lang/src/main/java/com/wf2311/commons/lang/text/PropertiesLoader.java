package com.wf2311.commons.lang.text;

import org.apache.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * 配置文件读取工具
 * @author wf2311
 * @date 2016/03/15 09:04.
 */
public class PropertiesLoader {

    private static Logger logger = Logger.getLogger(PropertiesLoader.class);

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    private final Properties properties;

    public PropertiesLoader(String... resourcesPaths) {
        properties = loadProperties(resourcesPaths);
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * 取出Property，但以System的Property优先,取不到返回空.
     *
     * @param key 关键字
     * @return string value
     */
    private String getValue(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return null;
    }

    /**
     * 取出String类型的Property，但以System的Property优先,如果都为Null则抛出异常.
     *
     * @param key 关键字
     * @return string value
     */
    public String getProperty(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }

    /**
     * 取出String类型的Property，但以System的Property优先.如果都为Null则返回Default值.
     *
     * @param key 关键字
     * @param defaultValue 默认值
     * @return string value
     */
    public String getProperty(String key, String defaultValue) {
        String value = getValue(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     *
     * @param key 关键字
     * @return Integer value
     */
    public Integer getInteger(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Integer.valueOf(value);
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     *
     * @param key 关键字
     * @param defaultValue 默认值
     * @return Integer value
     */
    public Integer getInteger(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }

    /**
     * 取出Long类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     *
     * @param key 关键字
     * @return Long value
     */
    public Long getLong(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Long.valueOf(value);
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     *
     * @param key          关键字
     * @param defaultValue 默认值
     * @return Integer value
     */
    public Long getLong(String key, Long defaultValue) {
        String value = getValue(key);
        return value != null ? Long.valueOf(value) : defaultValue;
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     *
     * @param key 关键字
     * @return Double value
     */
    public Double getDouble(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Double.valueOf(value);
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     *
     * @param key 关键字
     * @param defaultValue 默认值
     * @return Double value
     */
    public Double getDouble(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Double.valueOf(value) : defaultValue;
    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null抛出异常,如果内容不是true/false则返回false.
     *
     * @param key 关键字
     * @return Boolean value
     */
    public Boolean getBoolean(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Boolean.valueOf(value);
    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null则返回Default值,如果内容不为true/false则返回false.
     *
     * @param key 关键字
     * @param defaultValue 默认值
     * @return Boolean value
     */
    public Boolean getBoolean(String key, boolean defaultValue) {
        String value = getValue(key);
        return value != null ? Boolean.valueOf(value) : defaultValue;
    }

    /**
     * 载入多个文件, 文件路径使用Spring Resource格式.
     *
     * @param resourcesPaths 文件路径
     * @return Properties 对象
     */
    private Properties loadProperties(String... resourcesPaths) {
        Properties props = new Properties();

        for (String location : resourcesPaths) {

            InputStream is = null;
            try {
                Resource resource = resourceLoader.getResource(location);
                is = resource.getInputStream();
                props.load(is);
            } catch (IOException ex) {
                logger.error("Could not load properties from path:" + location + ", " + ex.getMessage());
            } finally {
                if(is!=null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return props;
    }
}
