package com.wf2311.commons.web;

import com.wf2311.commons.web.consts.Consts;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wf2311
 * @time 2016/06/08 14:19.
 */
public class RequestUserInfo {
    public static BaseUserInfo getUserInfo() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            BaseUserInfo userInfo=  (BaseUserInfo) request.getAttribute(Consts.USER_INFO);
            if (userInfo==null){
                userInfo=Consts.ADMIN_INFO;
            }
            return userInfo;
        } catch (Exception e) {
            return Consts.ADMIN_INFO;
        }
    }
}
