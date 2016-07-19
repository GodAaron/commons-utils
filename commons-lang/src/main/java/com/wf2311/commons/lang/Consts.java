package com.wf2311.commons.lang;


import java.io.Serializable;

/**
 * 自定义常量
 *
 * @author wf2311
 * @date 2015/12/02 14:16
 */
public interface Consts extends Serializable {

    /**
     * 基本数据类型数组
     */
    Class[] BASE_TYPE = new Class[]{byte.class, char.class, boolean.class, short.class, int.class, long.class, float.class, double.class};
    /**
     * 基本包装类型数组
     */
    Class[] PACKAGE_TYPE = new Class[]{Byte.class, Character.class, Boolean.class, Short.class, Integer.class, Long.class, Float.class, Double.class};

    /**
     * utf8格式
     */
    String UTF8 = "utf8";
    /**
     * gbk格式
     */
    String GBK = "gbk";
    /**
     * gb2312格式
     */
    String GB2312 = "gb2312";

    //常用消息字符串
    /**
     * 代码
     */
    String KEY_CODE = "code";
    /**
     * 结果
     */
    String KEY_RESULT = "result";
    /**
     * 消息
     */
    String KEY_MSG = "msg";
    /**
     * 消息
     */
    String KEY_MESSAGE = "message";
    /**
     * 对象
     */
    String KEY_OBJ = "obj";
    /**
     * 对象
     */
    String KEY_OBJECT = "object";
    /**
     * 数据
     */
    String KEY_DATA = "data";
    /**
     * 个人信息
     */
    String KEY_PROFILE = "profile";
    /**
     * 日期
     */
    String KEY_DATE = "date";
    /**
     * 时间
     */
    String KEY_TIME = "time";
    /**
     * 登录
     */
    String KEY_LOGIN = "login";
    /**
     * 注册
     */
    String KEY_REGISTER = "register";
    /**
     * 登录
     */
    String KEY_SIGN_IN = "SIGN IN";
    /**
     * 注册
     */
    String KEY_SIGN_UP = "SIGN IP";
    /**
     * 注销
     */
    String KEY_LOGOUT = "logout";
    /**
     * 发送
     */
    String KEY_POST = "post";
    /**
     * 接收
     */
    String KEY_GET = "get";
    /**
     * 网址
     */
    String KEY_URL = "url";
    /**
     * 当前用户
     */
    String KEY_CURRENT_USER = "CURRENT_USER";

    /**
     * 分页对象
     */
    String PAGER = "pager";


    //sql相关
    /**
     * 正序
     */
    String ASC = "asc";
    /**
     * 倒叙
     */
    String DESC = "desc";

    /**
     * 成功代号
     */
    int CODE_SUCCESS = 1;

    /**
     * 失败代号
     */
    int CODE_FAILURE = -1;

    /**
     * 初始代号
     */
    int CODE_GENERAL = 0;


    /**
     * 用户
     */
    String USER = "user";

    /**
     * 角色
     */
    String ROLE = "role";

    /**
     * 部门
     */
    String DEPARTMENT = "department";

    /**
     * 权限
     */
    String PERMISSION = "permission";
    /**
     * 排序
     */
    String SEQUENCE = "sequence";

    /**
     * 预置超级管理员的ID
     */
    long ADMIN_ID = 0;

    /**
     * 预置超级管理员的角色名
     */
    String ROLE_ADMIN = "Admin";

    /**
     * 预置超级管理员的角色名
     */
    String ROLE_ADMINISTRATOR = "Administrator";

    /**
     * 忽略值
     */
    int IGNORE = -1;

    /**
     * 零
     */
    int ZERO = 0;

    /**
     * 正常状态
     */
    int STATUS_NORMAL = 1;

    /**
     * 禁用状态
     */
    int STATUS_CLOSED = -1;

    /**
     * 删除状态
     */
    int STATUS_REMOVED = -10;

    /**
     * 未审核
     */
    int AUDIT_INIT = 0;

    /**
     * 审核通过
     */
    int AUDIT_PASSED = 1;

    /**
     * 已驳回\不通过
     */
    int AUDIT_REJECTED = -2;
}
