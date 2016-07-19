package com.wf2311.commons.exception;

/**
 * 自定义异常
 * @author: wf2311
 * @date: 2015/10/20 16:43
 */
public class WfException extends RuntimeException {
    protected int code;

    public WfException() {
    }

    /**
     * WfException
     *
     * @param code 错误代码
     */
    public WfException(int code) {
        super("code=" + code);
        this.code = code;
    }

    /**
     * WfException
     *
     * @param message 错误消息
     */
    public WfException(String message) {
        super(message);
    }

    /**
     * WfException
     *
     * @param cause 捕获的异常
     */
    public WfException(Throwable cause) {
        super(cause);
    }

    /**
     * WfException
     *
     * @param message 错误消息
     * @param cause   捕获的异常
     */
    public WfException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * WfException
     *
     * @param code    错误代码
     * @param message 错误消息
     */
    public WfException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
