package com.wf2311.commons.lang.utils;


import com.wf2311.commons.lang.text.PropertiesLoader;
import org.apache.log4j.Logger;

public class BaseUtils{
//    private static final Log logger = LogFactory.getLog(BaseUtils.class);
    private static final Logger logger=Logger.getLogger(BaseUtils.class);

    public static PropertiesLoader getPropertiesLoader(){
        return new PropertiesLoader("conf.properties");
    }

    public static void main(String[] args) {
        System.out.println(BaseUtils.getPropertiesLoader().getProperty("oss.bucket"));
    }


}
