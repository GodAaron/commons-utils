package com.wf2311.commons.lang.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author wf2311
 * @date 2015/8/19 22:15
 */
public class ServletUtils {

    public static void setResponseHeaders(HttpServletRequest req, HttpServletResponse resp, String filename) {
        try {
            String agent = req.getHeader("USER-AGENT").toLowerCase();

            if (agent.indexOf("msie") != -1 || agent.indexOf("edge") != -1) {
                resp.addHeader("Content-Disposition", "attachment;filename=" + new String(filename
                        .getBytes("gbk"),
                        "iso-8859-1"));
            } else if ((agent.indexOf("firefox") != -1) ||
                    (agent
                            .indexOf("chrome") !=
                            -1)) {
                resp.setHeader("filename", filename);
                resp.addHeader("Content-disposition", "attachment; filename=\"" + new String(filename
                        .getBytes("UTF-8"),
                        "ISO8859-1") + "\"");
            } else if (agent.indexOf("safari") != -1) {
                resp.addHeader("Content-Disposition", "attachment;filename=" + new String(filename
                        .getBytes("utf-8"),
                        "iso8859-1"));
            } else if (agent.indexOf("opera") != -1) {
                resp.addHeader("Content-Disposition", "attachment;filename=" + new String(filename
                        .getBytes("utf-8"),
                        "iso8859-1"));
            } else {
                resp.addHeader("Content-Disposition", "attachment;filename=" + new String(filename
                        .getBytes("gbk"),
                        "iso-8859-1"));
            }
            resp.setContentType("application/octet-stream");
        } catch (UnsupportedEncodingException e) {
        }
    }
}
