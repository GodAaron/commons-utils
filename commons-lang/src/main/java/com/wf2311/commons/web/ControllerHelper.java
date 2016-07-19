package com.wf2311.commons.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wf2311.commons.lang.StringUtils;
import com.wf2311.commons.web.consts.e.BaseCookieEnum;
import com.wf2311.commons.web.consts.e.BaseSessionEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wf2311
 * @time 2016/06/28 11:01.
 */
public class ControllerHelper {
    public static final String DEFAULT_COOKIE_PATH = "/";
    private static final Logger logger = Logger.getLogger(ControllerHelper.class);
    /**
     * Wap网关Via头信息中特有的描述信息
     */
    public static String mobileGateWayHeaders[] = new String[]{"ZXWAP", // 中兴提供的wap网关的via信息，例如：Via=ZXWAP
            // GateWayZTE
            // Technologies，
            "chinamobile.com", // 中国移动的诺基亚wap网关，例如：Via=WTP/1.1
            // GDSZ-PB-GW003-WAP07.gd.chinamobile.com (Nokia
            // WAP Gateway 4.1 CD1/ECD13_D/4.1.04)
            "monternet.com", // 移动梦网的网关，例如：Via=WTP/1.1
            // BJBJ-PS-WAP1-GW08.bj1.monternet.com. (Nokia
            // WAP
            // Gateway 4.1 CD1/ECD13_E/4.1.05)
            "infoX", // 华为提供的wap网关，例如：Via=HTTP/1.1 GDGZ-PS-GW011-WAP2
            // (infoX-WISG
            // Huawei Technologies)，或Via=infoX WAP Gateway V300R001
            // Huawei Technologies
            "XMS 724Solutions HTG", // 国外电信运营商的wap网关，不知道是哪一家
            "wap.lizongbo.com", // 自己测试时模拟的头信息
            "Bytemobile",// 貌似是一个给移动互联网提供解决方案提高网络运行效率的，例如：Via=1.1 Bytemobile OSN
            // WebProxy/5.1
    };
    /**
     * 电脑上的IE或Firefox浏览器等的User-Agent关键词
     */
    public static String[] pcHeaders = new String[]{"Windows 98", "Windows ME", "Windows 2000", "Windows XP",
            "Windows NT", "Ubuntu"};
    /**
     * 手机浏览器的User-Agent里的关键词
     */
    public static String[] mobileUserAgents = new String[]{"Nokia", // 诺基亚，有山寨机也写这个的，总还算是手机，Mozilla/5.0
            // (Nokia5800
            // XpressMusic)UC
            // AppleWebkit(like
            // Gecko)
            // Safari/530
            "SAMSUNG", // 三星手机
            // SAMSUNG-GT-B7722/1.0+SHP/VPP/R5+Dolfin/1.5+Nextreaming+SMM-MMS/1.2.0+profile/MIDP-2.1+configuration/CLDC-1.1
            "MIDP-2", // j2me2.0，Mozilla/5.0 (SymbianOS/9.3; U; Series60/3.2
            // NokiaE75-1 /110.48.125 Profile/MIDP-2.1
            // Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML like
            // Gecko) Safari/413
            "CLDC1.1", // M600/MIDP2.0/CLDC1.1/Screen-240X320
            "SymbianOS", // 塞班系统的，
            "MAUI", // MTK山寨机默认ua
            "UNTRUSTED/1.0", // 疑似山寨机的ua，基本可以确定还是手机
            "Windows CE", // Windows CE，Mozilla/4.0 (compatible; MSIE 6.0;
            // Windows CE; IEMobile 7.11)
            "iPhone", // iPhone是否也转wap？不管它，先区分出来再说。Mozilla/5.0 (iPhone; U; CPU
            // iPhone OS 4_1 like Mac OS X; zh-cn) AppleWebKit/532.9
            // (KHTML like Gecko) Mobile/8B117
            "iPad", // iPad的ua，Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X;
            // zh-cn) AppleWebKit/531.21.10 (KHTML like Gecko)
            // Version/4.0.4 Mobile/7B367 Safari/531.21.10
            "Android", // Android是否也转wap？Mozilla/5.0 (Linux; U; Android
            // 2.1-update1; zh-cn; XT800 Build/TITA_M2_16.22.7)
            // AppleWebKit/530.17 (KHTML like Gecko) Version/4.0
            // Mobile Safari/530.17
            "BlackBerry", // BlackBerry8310/2.7.0.106-4.5.0.182
            "UCWEB", // ucweb是否只给wap页面？ Nokia5800
            // XpressMusic/UCWEB7.5.0.66/50/999
            "ucweb", // 小写的ucweb貌似是uc的代理服务器Mozilla/6.0 (compatible; MSIE 6.0;)
            // Opera ucweb-squid
            "BREW", // 很奇怪的ua，例如：REW-Applet/0x20068888 (BREW/3.1.5.20; DeviceId:
            // 40105; Lang: zhcn) ucweb-squid
            "J2ME", // 很奇怪的ua，只有J2ME四个字母
            "YULONG", // 宇龙手机，YULONG-CoolpadN68/10.14 IPANEL/2.0 CTC/1.0
            "YuLong", // 还是宇龙
            "COOLPAD", // 宇龙酷派YL-COOLPADS100/08.10.S100 POLARIS/2.9 CTC/1.0
            "TIANYU", // 天语手机TIANYU-KTOUCH/V209/MIDP2.0/CLDC1.1/Screen-240X320
            "TY-", // 天语，TY-F6229/701116_6215_V0230 JUPITOR/2.2 CTC/1.0
            "K-Touch", // 还是天语K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223
            // Release/30.07.2008 Browser/WAP2.0
            "Haier", // 海尔手机，Haier-HG-M217_CMCC/3.0 Release/12.1.2007
            // Browser/WAP2.0
            "DOPOD", // 多普达手机
            "Lenovo", // 联想手机，Lenovo-P650WG/S100 LMP/LML Release/2010.02.22
            // Profile/MIDP2.0 Configuration/CLDC1.1
            "LENOVO", // 联想手机，比如：LENOVO-P780/176A
            "HUAQIN", // 华勤手机
            "AIGO-", // 爱国者居然也出过手机，AIGO-800C/2.04 TMSS-BROWSER/1.0.0 CTC/1.0
            "CTC/1.0", // 含义不明
            "CTC/2.0", // 含义不明
            "CMCC", // 移动定制手机，K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223
            // Release/30.07.2008 Browser/WAP2.0
            "DAXIAN", // 大显手机DAXIAN X180 UP.Browser/6.2.3.2(GUI) MMP/2.0
            "MOT-", // 摩托罗拉，MOT-MOTOROKRE6/1.0 LinuxOS/2.4.20 Release/8.4.2006
            // Browser/Opera8.00 Profile/MIDP2.0 Configuration/CLDC1.1
            // Software/R533_G_11.10.54R
            "SonyEricsson", // 索爱手机，SonyEricssonP990i/R100 Mozilla/4.0
            // (compatible; MSIE 6.0; Symbian OS; 405) Opera
            // 8.65 [zh-CN]
            "GIONEE", // 金立手机
            "HTC", // HTC手机
            "ZTE", // 中兴手机，ZTE-A211/P109A2V1.0.0/WAP2.0 Profile
            "HUAWEI", // 华为手机，
            "webOS", // palm手机，Mozilla/5.0 (webOS/1.4.5; U; zh-CN)
            // AppleWebKit/532.2 (KHTML like Gecko) Version/1.0
            // Safari/532.2 Pre/1.0
            "GoBrowser", // 3g GoBrowser.User-Agent=Nokia5230/GoBrowser/2.0.290
            // Safari
            "IEMobile", // Windows CE手机自带浏览器，
            "WAP2.0"// 支持wap 2.0的
    };

    /**
     * 获取session的值
     *
     * @param request HttpServletRequest对象
     * @param key     键名
     * @return session中获取的对象
     */
    public static Object getSession(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    /**
     * 获取session的值
     *
     * @param request HttpServletRequest对象
     * @param key     枚举类中的值
     * @return session中获取的对象
     */
    public static Object getSession(HttpServletRequest request, BaseSessionEnum key) {
        return getSession(request, key.toString());
    }

    /**
     * 设置session的值
     *
     * @param request HttpServletRequest对象
     * @param value   session值
     * @param key     键名
     */
    public static void setSession(HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    /**
     * 设置session的值
     *
     * @param request HttpServletRequest对象
     * @param value   session值
     * @param key     枚举类中的值
     */
    public static void setSession(HttpServletRequest request, BaseSessionEnum key, Object value) {
        setSession(request, key.toString(), value);
    }

    /**
     * 移除session的值
     *
     * @param request HttpServletRequest对象
     * @param key     键名
     */
    public static void removeSession(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }

    /**
     * 移除session的值
     *
     * @param request HttpServletRequest对象
     * @param key     枚举类中的值
     */
    public static void removeSession(HttpServletRequest request, BaseSessionEnum key) {
        removeSession(request, key.toString());
    }

    /**
     * 获取Cookie的值
     *
     * @param request HttpServletRequest对象
     * @param key     键名
     * @return Cookie中获取的对象
     */
    public static String getCookie(HttpServletRequest request, String key) {
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(key)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 获取Cookie的值
     *
     * @param request HttpServletRequest对象
     * @param key     枚举类中的值
     * @return Cookie中获取的对象
     */
    public static String getCookie(HttpServletRequest request, BaseCookieEnum key) {
        return getCookie(request, key.toString());
    }

    /**
     * 设置Cookie值
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @param key      键名
     * @param value    存储对象
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String key,
                                 Object value) {
        setCookie(request, response, key, value, DEFAULT_COOKIE_PATH, null);
    }

    /**
     * 设置Cookie值
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @param key      枚举类中的值
     * @param value    存储对象
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, BaseCookieEnum key,
                                 Object value) {
        setCookie(request, response, key, value, DEFAULT_COOKIE_PATH, null);
    }

    /**
     * 设置Cookie值
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @param key      枚举类中的值
     * @param value    存储对象
     * @param maxAge   cookie生命周期 以秒为单位
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String key, Object value,
                                 int maxAge) {
        setCookie(request, response, key, value, DEFAULT_COOKIE_PATH, maxAge);
    }

    /**
     * 设置Cookie值
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @param key      枚举类中的值
     * @param value    存储对象
     * @param maxAge   cookie生命周期 以秒为单位
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, BaseCookieEnum key, Object value,
                                 int maxAge) {
        setCookie(request, response, key.toString(), value, DEFAULT_COOKIE_PATH, maxAge);
    }

    /**
     * 设置Cookie值
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @param key      键名
     * @param value    存储对象
     * @param path     存储路径
     * @param maxAge   cookie生命周期 以秒为单位
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String key, Object value, String path,
                                 Integer maxAge) {
        request.getSession().setAttribute(key, value);
        Cookie cookie = new Cookie(key, value.toString());
        cookie.setPath(path);
        cookie.setValue(value.toString());
        if (maxAge != null && maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 设置Cookie值
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @param key      枚举类中的值
     * @param value    存储对象
     * @param path     存储路径
     * @param maxAge   cookie生命周期 以秒为单位
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, BaseCookieEnum key, Object value, String path,
                                 Integer maxAge) {
        setCookie(request, response, key.toString(), value, path, maxAge);
    }

    /**
     * 将requestBody请求参数转为JSONObject对象
     *
     * @param request HttpServletRequest对象
     * @return
     */
    public static JSONObject convertRequestBody(HttpServletRequest request) {
        String params = getStreamParams(request);
        if (params == null) {
            return null;
        }
        return JSON.parseObject(params);
    }

    /**
     * 获取请求的数据流
     *
     * @param request HttpServletRequest对象
     * @return
     */
    public static String getStreamParams(HttpServletRequest request) {
        String param;
        try {
            request.setCharacterEncoding("UTF-8");
            BufferedReader br = request.getReader();
            String buffer;
            StringBuffer buff = new StringBuffer();
            while ((buffer = br.readLine()) != null) {
                buff.append(buffer + "\n");
            }
            br.close();
            param = buff.toString();
            logger.info(param);
            return param;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获取请求的数据流，主要提供给微信平台接口使用
     *
     * @param request HttpServletRequest对象
     * @return 返回请求的数据流字符串，例如：微信平台会返回JSON或xml字符串
     */
    public static String readStreamParameter(HttpServletRequest request) {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }


    /**
     * 获取请求的数据流(合并为一行)
     *
     * @param request HttpServletRequest对象
     * @return
     */
    protected static String getStreamParamsSingleLine(HttpServletRequest request) {
        String param = getStreamParams(request);
        if (param != null) {
            return param.replaceAll("\n", "");
        }
        return null;
    }

    /**
     * 获取整型值
     *
     * @param request HttpServletRequest对象
     * @param param   参数名称
     * @return 返回整型值，没找到返回null
     */
    public static Integer getInt(HttpServletRequest request, String param) {
        String value = request.getParameter(param);
        if (StringUtils.isInteger(value)) {
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }

    /**
     * 获取整型值
     *
     * @param request HttpServletRequest对象
     * @param param   参数名称
     * @param def     默认值，如果参数不存在或不符合规则就用默认值替代
     * @return 返回整型值
     */
    public static Integer getInt(HttpServletRequest request, String param, int def) {
        String value = request.getParameter(param);
        if (StringUtils.isInteger(value)) {
            return Integer.parseInt(value);
        } else {
            return def;
        }
    }

    /**
     * 获取布尔值
     *
     * @param request HttpServletRequest对象
     * @param param   参数名称
     * @return 返回布尔值，没找到返回null
     */
    public static Boolean getBoolean(HttpServletRequest request, String param) {
        String value = request.getParameter(param);
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取项目路径
     *
     * @param request HttpServletRequest对象
     * @return 返回项目路径，例如：http://www.ip.com/projectName 后面没有反斜杠，后面接地址或参数必须加/
     */
    public static String getUrl(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() == 80) {
            basePath += path;
        } else {
            basePath += ":" + request.getServerPort() + path;
        }
        return basePath;
    }

    /**
     * 获取请求域名，域名不包括http请求协议头
     *
     * @param request HttpServletRequest对象
     * @return 返回域名地址
     */
    public static String getDomain(HttpServletRequest request) {
        String path = request.getContextPath();
        String domain = request.getServerName();
        if (request.getServerPort() == 80) {
            domain += path;
        } else {
            domain += ":" + request.getServerPort() + path;
        }
        return domain;
    }

    /**
     * 读取服务器主机信息
     *
     * @param request HttpServletRequest对象
     * @return 返回主机信息
     */
    public static String getHost(HttpServletRequest request) {
        String basePath = request.getServerName();
        if (request.getServerPort() != 80) {
            basePath += ":" + request.getServerPort();
        }
        return basePath;
    }

    /**
     * 读取服务器主机ip信息
     *
     * @return 返回主机IP，异常将会获取不到ip
     */
    public static String getHostIp() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            return addr.getHostAddress();// 获得本机IP
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据当前请求的特征，判断该请求是否来自手机终端，主要检测特殊的头信息，以及user-Agent这个header
     *
     * @param request HttpServletRequest对象,http请求
     * @return 如果是手机端返回true否则返回false
     */
    public static boolean isMobileDevice(HttpServletRequest request) {
        boolean b = false;
        boolean pcFlag = false;
        boolean mobileFlag = false;
        String via = request.getHeader("Via");
        String userAgent = request.getHeader("user-agent");
        for (int i = 0; via != null && !via.trim().equals("") && i < mobileGateWayHeaders.length; i++) {
            if (via.contains(mobileGateWayHeaders[i])) {
                mobileFlag = true;
                break;
            }
        }
        for (int i = 0; !mobileFlag && userAgent != null && !userAgent.trim().equals("")
                && i < mobileUserAgents.length; i++) {
            if (userAgent.contains(mobileUserAgents[i])) {
                mobileFlag = true;
                break;
            }
        }
        for (int i = 0; userAgent != null && !userAgent.trim().equals("") && i < pcHeaders.length; i++) {
            if (userAgent.contains(pcHeaders[i])) {
                pcFlag = true;
                break;
            }
        }
        if (mobileFlag && !pcFlag) {
            b = true;
        }
        return b;
    }

}
