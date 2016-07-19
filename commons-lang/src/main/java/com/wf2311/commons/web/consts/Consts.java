package com.wf2311.commons.web.consts;

import com.wf2311.commons.web.BaseUserInfo;

import java.util.ResourceBundle;

/**
 * @author wf2311
 * @time 2016/06/08 14:07.
 */
public interface Consts {
    String USER_INFO = "userinfo";
    String ADMIN_NAME = "ADMIN";
    BaseUserInfo ADMIN_INFO = new BaseUserInfo(0L, ADMIN_NAME);
    ResourceBundle RESOURCES = ResourceBundle.getBundle("com.wf2311.lang.web.resources.resource");
}
