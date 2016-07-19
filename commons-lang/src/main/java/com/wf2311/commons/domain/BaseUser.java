package com.wf2311.commons.domain;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 基本用户实体类
 *
 * @user wf2311
 * @time 2015/12/06 14:06
 */
public class BaseUser implements Serializable {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
      return JSON.toJSONString(this);
    }

    public BaseUser() {
    }

    public BaseUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
