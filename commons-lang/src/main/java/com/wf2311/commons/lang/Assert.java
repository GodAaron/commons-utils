package com.wf2311.commons.lang;


import com.wf2311.commons.exception.UnexpectedFailureException;
import com.wf2311.commons.exception.UnreachableCodeException;
import com.wf2311.commons.exception.WfException;

import java.util.Collection;
import java.util.Map;

import static com.wf2311.commons.lang.Assert.ExceptionType.*;

/**
 * 条件验证
 * @author: wf2311
 * @date: 2015/10/20 16:41
 */
public final class Assert {

    /**
     * 确保表达式为真
     *
     * @param expression 表达式
     * @param errorCode  失败抛出的异常代码
     */
    public static void isTrue(boolean expression, int errorCode) {
        if (!expression) {
            throw new WfException(errorCode);
        }
    }

    /**
     * 确保表达式为真
     *
     * @param expression 表达式
     * @param message    失败抛出的异常信息
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new WfException(message);
        }
    }

    /**
     * 确保表达式为假
     *
     * @param expression 表达式
     * @param errorCode  失败抛出的异常代码
     */
    public static void isFalse(boolean expression, int errorCode) {
        if (expression) {
            throw new WfException(errorCode);
        }
    }

    /**
     * 确保表达式为真
     *
     * @param expression 表达式
     * @param message  失败抛出的异常信息
     */
    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new WfException(message);
        }
    }

    /**
     * 确保对象为空<code>null</>
     *
     * @param object 要检查的对象
     * @param errorCode  失败抛出的异常代码
     */
    public static void isNull(Object object, int errorCode) {
        isTrue(object == null, errorCode);
    }

    /**
     * 确保对象为空<code>null</>
     *
     * @param object 要检查的对象
     * @param message  失败抛出的异常信息
     */
    public static void isNull(Object object, String message) {
        isTrue(object == null, message);
    }

    /**
     * 确保对象不为空<code>null</>
     *
     * @param object 要检查的对象
     * @param errorCode  失败抛出的异常代码
     */
    public static void notNull(Object object, int errorCode) {
        isFalse(object == null, errorCode);
    }

    /**
     * 确保对象不为空<code>null</>
     *
     * @param object 要检查的对象
     * @param message  失败抛出的异常信息
     */
    public static void notNull(Object object, String message) {
        isFalse(object == null, message);
    }

    /**
     * 确保数组不为空
     *
     * @param array 要检查的对象
     * @param errorCode  失败抛出的异常代码
     */
    public static void notEmpty(Object[] array, int errorCode) {
        if (ObjectUtils.isEmpty(array)) {
            throw new WfException(errorCode);
        }
    }

    /**
     * 确保数组不为空
     *
     * @param array 要检查的对象
     * @param message  失败抛出的异常信息
     */
    public static void notEmpty(Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new WfException(message);
        }
    }

    /**
     * 确保数组没有空元素
     *
     * @param array 要检查的对象
     * @param errorCode  失败抛出的异常代码
     */
    public static void noNullElements(Object[] array, int errorCode) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new WfException(errorCode);
                }
            }
        }
    }

    /**
     * 确保数组没有空元素
     *
     * @param array 要检查的对象
     * @param message  失败抛出的异常信息
     */
    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new WfException(message);
                }
            }
        }
    }

    /**
     * 确保集合没有空元素
     *
     * @param collection 要检查的对象
     * @param errorCode  失败抛出的异常代码
     */
    public static void notEmpty(Collection<?> collection, int errorCode) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new WfException(errorCode);
        }
    }


    /**
     * 确保集合没有空元素
     *
     * @param collection 要检查的对象
     * @param message  失败抛出的异常信息
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new WfException(message);
        }
    }

    /**
     * 确保map没有空元素
     *
     * @param map 要检查的对象
     * @param errorCode  失败抛出的异常代码
     */
    public static void notEmpty(Map<?, ?> map, int errorCode) {
        if (CollectionUtils.isEmpty(map)) {
            throw new WfException(errorCode);
        }
    }
    /**
     * 确保map没有空元素
     *
     * @param map 要检查的对象
     * @param message  失败抛出的异常信息
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new WfException(message);
        }
    }

    public static void hasLength(String text, int errorCode) {
        if (!StringUtils.hasLength(text)) {
            throw new WfException(errorCode);
        }
    }

    public static void hasLength(String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new WfException(message);
        }
    }

    public static void hasText(String text, int errorCode) {
        if (!StringUtils.hasText(text)) {
            throw new WfException(errorCode);
        }
    }

    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new WfException(message);
        }
    }

    public static void doesNotContain(String textToSearch, String substring, int message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw new WfException(message);
        }
    }

    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw new WfException(message);
        }
    }

    public static void instanceOf(Object object, Class<?> c, int errorCode) {
        isTrue(c.isInstance(object), errorCode);
    }

    public static void instanceOf(Object object, Class<?> c, String message) {
        isTrue(c.isInstance(object), message);
    }

    public static void notInstanceOf(Object object, Class<?> c, int errorCode) {
        isFalse(c.isInstance(object), errorCode);
    }

    public static void notInstanceOf(Object object, Class<?> c, String message) {
        isFalse(c.isInstance(object), message);
    }

    public static void gt(long x, long y, int errorCode) {
        isTrue(x > y, errorCode);
    }

    public static void gt(long x, long y, String message) {
        isTrue(x > y, message);
    }

    public static void ge(long x, long y, int errorCode) {
        isTrue(x >= y, errorCode);
    }

    public static void ge(long x, long y, String message) {
        isTrue(x >= y, message);
    }

    public static void lt(long x, long y, int errorCode) {
        isTrue(x < y, errorCode);
    }

    public static void lt(long x, long y, String message) {
        isTrue(x < y, message);
    }

    public static void le(long x, long y, int errorCode) {
        isTrue(x <= y, errorCode);
    }

    public static void le(long x, long y, String message) {
        isTrue(x <= y, message);
    }

    public static void eq(long x, long y, int errorCode) {
        isTrue(x == y, errorCode);
    }

    public static void eq(long x, long y, String message) {
        isTrue(x == y, message);
    }

    public static void ne(long x, long y, int errorCode) {
        isTrue(x != y, errorCode);
    }

    public static void ne(long x, long y, String message) {
        isTrue(x != y, message);
    }

    public static <T> void eq(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) == 0, errorCode);
    }

    public static <T> void eq(Comparable<T> x, T y, String message) {
        isTrue(x.compareTo(y) == 0, message);
    }

    public static <T> void ne(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) != 0, errorCode);
    }

    public static <T> void ne(Comparable<T> x, T y, String message) {
        isTrue(x.compareTo(y) != 0, message);
    }

    public static <T> void gt(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) == 1, errorCode);
    }

    public static <T> void gt(Comparable<T> x, T y, String message) {
        isTrue(x.compareTo(y) == 1, message);
    }

    public static <T> void ge(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) != -1, errorCode);
    }

    public static <T> void ge(Comparable<T> x, T y, String message) {
        isTrue(x.compareTo(y) != -1, message);
    }

    public static <T> void lt(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) == -1, errorCode);
    }

    public static <T> void lt(Comparable<T> x, T y, String message) {
        isTrue(x.compareTo(y) == -1, message);
    }

    public static <T> void le(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) != 1, errorCode);
    }

    public static <T> void le(Comparable<T> x, T y, String message) {
        isTrue(x.compareTo(y) != 1, message);
    }


    /**
     * 不可能到达的代码。
     */
    public static <T> T unreachableCode() {
        unreachableCode(null, (Object[]) null);
        return null;
    }

    /**
     * 不可能到达的代码。
     */
    public static <T> T unreachableCode(String message, Object... args) {
        throw UNREACHABLE_CODE.newInstance(getMessage(message, args,
                "[Assertion failed] - the code is expected as unreachable"));
    }

    /**
     * 不可能发生的异常。
     */
    public static <T> T unexpectedException(Throwable e) {
        unexpectedException(e, null, (Object[]) null);
        return null;
    }

    /**
     * 不可能发生的异常。
     */
    public static <T> T unexpectedException(Throwable e, String message, Object... args) {
        RuntimeException exception = UNEXPECTED_FAILURE.newInstance(getMessage(message, args,
                "[Assertion failed] - unexpected exception is thrown"));

        exception.initCause(e);

        throw exception;
    }

    /**
     * 未预料的失败。
     */
    public static <T> T fail() {
        fail(null, (Object[]) null);
        return null;
    }

    /**
     * 未预料的失败。
     */
    public static <T> T fail(String message, Object... args) {
        throw UNEXPECTED_FAILURE.newInstance(getMessage(message, args, "[Assertion failed] - unexpected failure"));
    }

    /**
     * 不支持的操作。
     */
    public static <T> T unsupportedOperation() {
        unsupportedOperation(null, (Object[]) null);
        return null;
    }

    /**
     * 不支持的操作。
     */
    public static <T> T unsupportedOperation(String message, Object... args) {
        throw UNSUPPORTED_OPERATION.newInstance(getMessage(message, args,
                "[Assertion failed] - unsupported operation or unimplemented function"));
    }

    /**
     * 取得带参数的消息。
     */
    private static String getMessage(String message, Object[] args, String defaultMessage) {
        if (message == null) {
            message = defaultMessage;
        }

        if (args == null || args.length == 0) {
            return message;
        }

        return String.format(message, args);
    }

    /**
     * Assertion错误类型。
     */
    public enum ExceptionType {
        ILLEGAL_ARGUMENT {
            RuntimeException newInstance(String message) {
                return new IllegalArgumentException(message);
            }
        },

        ILLEGAL_STATE {
            RuntimeException newInstance(String message) {
                return new IllegalStateException(message);
            }
        },

        NULL_POINT {
            RuntimeException newInstance(String message) {
                return new NullPointerException(message);
            }
        },

        UNREACHABLE_CODE {
            RuntimeException newInstance(String message) {
                return new UnreachableCodeException(message);
            }
        },

        UNEXPECTED_FAILURE {
            RuntimeException newInstance(String message) {
                return new UnexpectedFailureException(message);
            }
        },

        UNSUPPORTED_OPERATION {
            RuntimeException newInstance(String message) {
                return new UnsupportedOperationException(message);
            }
        };

        abstract RuntimeException newInstance(String message);
    }
}
