package com.wf2311.commons.web;

import com.alibaba.fastjson.JSONObject;
import com.wf2311.commons.lang.StringUtils;
import com.wf2311.commons.lang.date.DateUtils;
import com.wf2311.commons.lang.utils.IpUtils;
import com.wf2311.commons.web.consts.Code;
import com.wf2311.commons.web.consts.Consts;
import com.wf2311.commons.web.consts.e.BaseCookieEnum;
import com.wf2311.commons.web.consts.e.BaseSessionEnum;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author wf2311
 * @time 2016/06/03 14:55.
 */
public class BaseController implements Code {
    private Logger logger = Logger.getLogger(BaseController.class);
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected Cookie[] cookies;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.cookies = request.getCookies();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseModel<String> exceptionHandler(Exception ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        logger.error(ex.getMessage(), ex);

        String message = ex.getMessage();
        if (StringUtils.isEmpty(message)) {
            message = "系统错误，请联系管理员获得更多帮助。";
        }

        BaseModel<String> rtn = new BaseModel<>();
        rtn.setCode(RTN_FAILURE);
        rtn.setMsg(message);
        return rtn;
    }

    /**
     * 将请求的数据流转为JSONObject对象
     *
     * @return
     */
    protected JSONObject convertRequestBody() {
        return ControllerHelper.convertRequestBody(request);
    }

    /**
     * 获取请求的数据流
     *
     * @return
     */
    protected String getStreamParams() {
        return ControllerHelper.getStreamParams(request);
    }

    /**
     * 获取请求的数据流(合并为一行)
     *
     * @return
     */
    protected String getStreamParamSingleLine() {
        return ControllerHelper.getStreamParamsSingleLine(request);
    }

    protected String getParam(String param){
        return request.getParameter(param);
    }

    /**
     * 获取整型值，如果参数不存在或不符合规则就返回null
     *
     * @param param 参数名称
     * @return 返回整型值
     */
    protected Integer getInt(String param) {
        return getInt(param, null);
    }

    /**
     * 获取整型值，如果参数不存在或不符合规则就用默认值替代
     *
     * @param param 参数名称
     * @param def   默认值
     * @return 返回整型值
     */
    protected Integer getInt(String param, Integer def) {
        String value = getParam(param);
        if (StringUtils.isInteger(value)) {
            return Integer.parseInt(value);
        } else {
            return def;
        }
    }

    /**
     * 获取Double值，如果参数不存在或不符合规则就返回null
     *
     * @param param 参数名称
     * @return 返回整型值
     */
    protected Double getDouble(String param){
        return getDouble(param,null);
    }

    /**
     * 获取Double值，如果参数不存在或不符合规则就用默认值替代
     *
     * @param param 参数名称
     * @param def   默认值
     * @return 返回整型值
     */
    protected Double getDouble(String param, Double def) {
        String value = getParam(param);
        if (StringUtils.isDouble(value)) {
            return Double.parseDouble(value);
        } else {
            return def;
        }
    }

    /**
     * 获取Date值，如果参数不存在或不符合规则就返回null
     *
     * @param param 参数名称
     * @return 返回整型值
     */
    protected Date getDate(String param){
        return getDate(param,null);
    }

    /**
     * 获取Date值，如果参数不存在或不符合规则就用默认值替代
     *
     * @param param 参数名称
     * @param def   默认值
     * @return 返回整型值
     */
    protected Date getDate(String param,Date def){
        String value=getParam(param);
        Date date= DateUtils.parseToDate(value);
        if (date==null){
            date=def;
        }
        return date;
    }


    /**
     * 获取基本的用户信息
     *
     * @return
     */
    protected BaseUserInfo getUserInfo() {
        return (BaseUserInfo) request.getAttribute(Consts.USER_INFO);
    }

    /**
     * 获取客户端设备类型
     *
     * @return
     */
    protected String getDeviceType() {
        String userAgent = request.getHeader("user-agent");
        String deviceName = this.getAgent(userAgent);
        if (deviceName.startsWith("i")) {
            return "ios";
        } else if (deviceName.startsWith("A")) {
            return "Android";
        } else {
            return "other device";
        }
    }

    private String getAgent(String agent) {

        String[] keywords = {"Android", "iPhone", "iPod", "iPad"};

        if (!agent.contains("Windows NT")
                || (agent.contains("Windows NT") && agent.contains("compatible; MSIE 9.0;"))) {

            if (!agent.contains("Windows NT") && !agent.contains("Macintosh")) {
                for (String item : keywords) {
                    if (agent.contains(item)) {
                        return item;
                    }
                }
            }
        }

        return "";
    }

    /**
     * 读取服务器主机信息
     *
     * @return 返回主机信息
     */
    protected String getHost() {
        return ControllerHelper.getHost(request);
    }

    /**
     * 获取客户端ip
     *
     * @return
     */
    protected String getAgentIp() {
        return IpUtils.getIpAddr(request);
    }

    /**
     * 获取服务端ip
     *
     * @return
     */
    protected String getHostIp() {
        return ControllerHelper.getHostIp();
    }

    /**
     * 获取项目路径
     *
     * @return 返回项目路径，例如：http://www.ip.com/projectName 后面没有反斜杠，后面接地址或参数必须加/
     */
    protected String getRootUrl() {
        return ControllerHelper.getUrl(request);
    }

    /**
     * 获取请求域名，域名不包括http请求协议头
     *
     * @return 返回域名地址
     */
    protected Object getDomain() {
        return ControllerHelper.getDomain(request);
    }

    /**
     * 根据当前请求的特征，判断该请求是否来自手机终端，主要检测特殊的头信息，以及user-Agent这个header
     *
     * @return 如果是手机端返回true否则返回false
     */
    protected boolean isMobileDevice(){
        return ControllerHelper.isMobileDevice(request);
    }

    /**
     * 获取session的值
     *
     * @param key 键名
     * @return session中获取的对象
     */
    protected Object getSession(String key) {
        return ControllerHelper.getSession(request, key);
    }

    /**
     * 获取session的值
     *
     * @param key 枚举类中的值
     * @return session中获取的对象
     */
    protected Object getSession(BaseSessionEnum key) {
        return ControllerHelper.getSession(request, key);
    }

    /**
     * 设置session的值
     *
     * @param key   键名
     * @param value session值
     */
    protected void setSession(String key, Object value) {
        ControllerHelper.setSession(request, key, value);
    }

    /**
     * 设置session的值
     *
     * @param key   枚举类中的值
     * @param value session值
     */
    protected void setSession(BaseSessionEnum key, Object value) {
        ControllerHelper.setSession(request, key, value);
    }

    /**
     * 移除session的值
     *
     * @param key 键名
     */
    protected void removeSession(String key) {
        ControllerHelper.removeSession(request, key);
    }

    /**
     * 移除session的值
     *
     * @param key 枚举类中的值
     */
    protected void removeSession(BaseSessionEnum key) {
        ControllerHelper.removeSession(request, key);
    }

    /**
     * 获取Cookie的值
     *
     * @param key 键名
     * @return Cookie中获取的对象
     */
    protected String getCookie(String key) {
        return ControllerHelper.getCookie(request, key);
    }

    /**
     * 获取Cookie的值
     *
     * @param key 枚举类中的值
     * @return Cookie中获取的对象
     */
    protected String getCookie(BaseCookieEnum key) {
        return ControllerHelper.getCookie(request, key);
    }

    /**
     * 设置Cookie值
     *
     * @param key    键名
     * @param value  存储对象
     * @param path   存储路径
     * @param maxAge cookie生命周期 以秒为单位
     */
    protected void setCookie(String key, Object value, String path, Integer maxAge) {
        ControllerHelper.setCookie(request, response, key, value, path, maxAge);
    }

    /**
     * 设置Cookie值
     *
     * @param key    枚举类中的值
     * @param value  存储对象
     * @param path   存储路径
     * @param maxAge cookie生命周期 以秒为单位
     */
    protected void setCookie(BaseCookieEnum key, Object value, String path, Integer maxAge) {
        ControllerHelper.setCookie(request, response, key, value, path, maxAge);
    }

    /**
     * 设置Cookie值
     *
     * @param key    键名
     * @param value  存储对象
     * @param maxAge cookie生命周期 以秒为单位
     */
    protected void setCookie(String key, Object value, Integer maxAge) {
        setCookie(key, value, ControllerHelper.DEFAULT_COOKIE_PATH, maxAge);
    }

    /**
     * 设置Cookie值
     *
     * @param key    枚举类中的值
     * @param value  存储对象
     * @param maxAge cookie生命周期 以秒为单位
     */
    protected void setCookie(BaseCookieEnum key, Object value, Integer maxAge) {
        setCookie(key, value, ControllerHelper.DEFAULT_COOKIE_PATH, maxAge);
    }

    /**
     * 设置Cookie值
     *
     * @param key   键名
     * @param value 存储对象
     */
    protected void setCookie(String key, Object value) {
        setCookie(key, value, null);
    }

    /**
     * 设置Cookie值
     *
     * @param key   枚举类中的值
     * @param value 存储对象
     */
    protected void setCookie(BaseCookieEnum key, Object value) {
        setCookie(key, value, null);
    }

    /**
     * 通过spring的webapplicationcontext上下文对象读取bean对象
     *
     * @param sc       上下文servletConext对象
     * @param beanName 要读取的bean的名称
     * @return 返回获取到的对象。获取不到返回null
     */
    protected Object getBean(ServletContext sc, String beanName) {
        return WebApplicationContextUtils.getWebApplicationContext(sc).getBean(beanName);
    }

    /**
     * 读取国际化资源文件
     *
     * @param key
     *            键值
     * @return 返回获取到的字符串
     */
    protected String getResString(String key) {
        return Consts.RESOURCES.getString(key);
    }

    /**
     * 读取国际化资源文件，优先模块对应的资源文件，如果模块资源文件找不到就会优先基础层
     *
     * @param key
     *            键值
     * @param rb
     *            模块对应资源文件
     * @return 返回获取到的字符串
     */
    protected String getResString(String key, ResourceBundle rb) {
        try {
            return rb.getString(key);
        } catch (MissingResourceException e) {
            return Consts.RESOURCES.getString(key);
        }
    }

    /**
     * 读取国际化资源文件
     *
     * @param key
     *            键值
     * @param fullStrs
     *            需填充的值
     * @return 返回获取到的字符串
     */
    protected String getResString(String key, String... fullStrs) {
        String temp = this.getResString(key);
        for (int i = 0; i < fullStrs.length; i++) {
            temp = temp.replace("{" + i + "}", fullStrs[i]);
        }
        return temp;
    }

    /**
     * 读取国际化资源文件，优先模块对应的资源文件，如果模块资源文件找不到就会优先基础层
     *
     * @param key
     *            键值
     * @param rb
     *            模块对应资源文件
     * @return 返回获取到的字符串
     */
    protected String getResString(String key, ResourceBundle rb, String... fullStrs) {
        String temp = "";
        try {
            temp = rb.getString(key);
        } catch (MissingResourceException e) {
            temp = getResString(key);
        }
        for (int i = 0; i < fullStrs.length; i++) {
            temp = temp.replace("{" + i + "}", fullStrs[i]);
        }
        return temp;
    }

}
