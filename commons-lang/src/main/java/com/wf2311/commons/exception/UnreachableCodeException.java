package com.wf2311.commons.exception;

/**
 * 代表不可能到达的代码的异常
 *
 * @author wf2311
 * @time 2016/07/01 17:00.
 */
public class UnreachableCodeException extends RuntimeException {
    public UnreachableCodeException() {
        super();
    }

    public UnreachableCodeException(String message) {
        super(message);
    }

    public UnreachableCodeException(Throwable cause) {
        super(cause);
    }

    public UnreachableCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
