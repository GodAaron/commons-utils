package com.wf2311.commons.exception;

/**
 * 代表未预期的失败
 *
 * @author wf2311
 * @time 2016/07/01 17:00.
 */
public class UnexpectedFailureException extends RuntimeException {
    public UnexpectedFailureException() {
        super();
    }

    public UnexpectedFailureException(String message) {
        super(message);
    }

    public UnexpectedFailureException(Throwable cause) {
        super(cause);
    }

    public UnexpectedFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
