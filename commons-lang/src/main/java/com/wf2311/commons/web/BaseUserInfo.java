package com.wf2311.commons.web;

import com.alibaba.fastjson.JSON;
import com.wf2311.commons.domain.BaseUser;

/**
 * @author wf2311
 * @time 2016/06/08 14:03.
 */
public class BaseUserInfo extends BaseUser {
    private Long id;
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BaseUserInfo(Long id,String username) {
        this.id = id;
        super.setUsername(username);
    }

    public BaseUserInfo(String username, String password, Long id, String token) {
        super(username, password);
        this.id = id;
        this.token = token;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
