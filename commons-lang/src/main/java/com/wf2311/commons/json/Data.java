package com.wf2311.commons.json;



import com.wf2311.commons.lang.Consts;

import java.util.ArrayList;

/**
 * Json 统一返回消息类
 *
 * @author wf2311
 * @date 2015/12/02 14:11
 */
public class Data implements Consts {
    //    public static String[] NOOP = new String[]{""};
    public static String NOOP = "";

    private Integer code;   //处理状态
    private String message; //返回信息
    private Object data;    //返回数据

    private ArrayList<Button> links = new ArrayList<>();

    /**
     * @param link
     * @param text
     * @return
     */
    public Data addLink(String link, String text) {
        links.add(new Button(link, text));
        return this;
    }


    /**
     * 处理成功，并返回数据
     *
     * @param data 数据对象
     * @return data
     */
    public static final Data success(Object data) {
        return new Data(CODE_SUCCESS, "操作成功", data);
    }

    /**
     * 处理成功
     *
     * @param message 消息
     * @return data
     */
    public static final Data success(String message) {
        return new Data(CODE_SUCCESS, message, NOOP);
    }

    /**
     * 处理成功
     *
     * @param message 消息
     * @param data    数据对象
     * @return data
     */
    public static final Data success(String message, Object data) {
        return new Data(CODE_SUCCESS, message, data);
    }

    /**
     * 处理成功
     *
     * @param code    成功代码
     * @param message 消息
     * @param data    数据对象
     * @return data
     */
    public static final Data success(int code, String message, Object data) {
        return new Data(code, message, data);
    }

    /**
     * 操作完成，并返回数据
     *
     * @param data 数据对象
     * @return data
     */
    public static final Data finish(Object data) {
        return new Data(CODE_GENERAL, "操作成功", data);
    }

    /**
     * 操作完成
     *
     * @param message 消息
     * @return data
     */
    public static final Data finish(String message) {
        return new Data(CODE_GENERAL, message, NOOP);
    }

    /**
     * 操作完成，并返回数据
     *
     * @param message 消息
     * @param data    数据对象
     * @return data
     */
    public static final Data finish(String message, Object data) {
        return new Data(CODE_GENERAL, message, data);
    }

    /**
     * 操作完成
     *
     * @param code    完成代码
     * @param message 消息
     * @param data    数据对象
     * @return data
     */
    public static final Data finish(int code, String message, Object data) {
        return new Data(code, message, data);
    }


    /**
     * 处理失败，并返回数据（一般为错误信息）
     *
     * @param code    错误代码
     * @param message 消息
     * @return data
     */
    public static final Data failure(int code, String message) {
        return failure(code, message, NOOP);
    }

    /**
     * 处理失败
     *
     * @param message 消息
     * @return data
     */
    public static final Data failure(String message) {
        return failure(CODE_FAILURE, message);
    }

    /**
     * 处理失败，并返回数据
     *
     * @param message 消息
     * @param data    数据对象
     * @return data
     */
    public static final Data failure(String message, Object data) {
        return failure(CODE_FAILURE, message, data);
    }

    /**
     * 处理失败
     *
     * @param code    错误代码
     * @param message 消息
     * @param data    数据对象
     * @return data
     */
    public static final Data failure(int code, String message, Object data) {
        return new Data(code, message, data);
    }

    public Data(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Data(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Data(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Data(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ArrayList<Button> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Button> links) {
        this.links = links;
    }

    public String toString() {
        JsonUtils jsonUtils = new JsonUtils();
        return jsonUtils.writeJsonByFilter(this, null, null);
    }

    public String toString(String[] includesProperties, String[] excludesProperties) {
        JsonUtils jsonUtils = new JsonUtils();
        return jsonUtils.writeJsonByFilter(this, includesProperties, excludesProperties);
    }

    public class Button {
        private String text;
        private String link;

        public Button(String link, String text) {
            this.link = link;
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
