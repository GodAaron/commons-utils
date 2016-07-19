package com.wf2311.commons.lang;

import java.lang.reflect.Array;
import java.util.List;


/**
 * 数组工具类
 * @author wf2311
 * @author renhui
 * @date 2016/01/10 14:20
 */
public class ArrayUtils implements ArrayConsts {
    private static final Object[] EMPTY_ARRAY=new Object[0];
    /**
     * 从数组中查找指定元素出现的第一个索引位置；若不存在，则返回-1
     *
     * @param array 数组
     * @param s     要查找元素
     * @param <T>   数组类型
     * @return 索引位置
     */
    public static  <T> int indexOf(T[] array, T s) {
        for (int i = 0; i < array.length; i++) {
            if (s.equals(array[i]))
                return i;
        }
        return -1;
    }

    /**
     * 获取数组的长度
     *
     * @param array             要检查的数组
     * @param defaultIfNull     如果是空数组要返回的值
     * @param defaultIfNotArray 如果不是数组要返回的值
     * @return
     */
    public static int getLength(Object array, int defaultIfNull, int defaultIfNotArray) {
        if (array == null) {
            return defaultIfNull; // null
        } else if (array instanceof Object[]) {
            return ((Object[]) array).length;
        } else if (array instanceof long[]) {
            return ((long[]) array).length;
        } else if (array instanceof int[]) {
            return ((int[]) array).length;
        } else if (array instanceof short[]) {
            return ((short[]) array).length;
        } else if (array instanceof byte[]) {
            return ((byte[]) array).length;
        } else if (array instanceof double[]) {
            return ((double[]) array).length;
        } else if (array instanceof float[]) {
            return ((float[]) array).length;
        } else if (array instanceof boolean[]) {
            return ((boolean[]) array).length;
        } else if (array instanceof char[]) {
            return ((char[]) array).length;
        } else {
            return defaultIfNotArray;
        }
    }

    /**
     * 取得数组的长度。
     *
     * @param array 要检查的数组
     * @return 如果为空，或者非数组，则返回<code>0</code>。
     */
    public static int getLength(Object array) {
        return getLength(array, 0, 0);
    }

    /**
     * 检查数组是否为<code>null</code>或空数组<code>[]</code>。
     * <p/>
     * <pre>
     * ArrayUtil.isEmpty(null)              = true
     * ArrayUtil.isEmpty(new int[0])        = true
     * ArrayUtil.isEmpty(new int[10])       = false
     * </pre>
     *
     * @param array 要检查的数组
     * @return 如果为空, 则返回<code>true</code>
     */
    public static boolean isEmpty(Object array) {
        return getLength(array, 0, -1) == 0;
    }

    /**
     * 如果数组是<code>null</code>或空数组<code>[]</code>，则返回指定数组默认值。
     * <p/>
     * <pre>
     * ArrayUtil.defaultIfEmpty(null, defaultArray)           = defaultArray
     * ArrayUtil.defaultIfEmpty(new String[0], defaultArray)  = 数组本身
     * ArrayUtil.defaultIfEmpty(new String[10], defaultArray) = 数组本身
     * </pre>
     *
     * @param array        要转换的数组
     * @param defaultValue 默认数组
     * @return 数组本身或默认数组
     */
    public static <T, S extends T> T defaultIfEmptyArray(T array, S defaultValue) {
        return isEmpty(array) ? defaultValue : array;
    }

    /**
     * 反转数组的元素顺序。如果数组为<code>null</code>，则什么也不做。
     *
     * @param array 要反转的数组
     */
    public static void reverse(byte[] array) {
        if (array == null) {
            return;
        }

        byte tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }


    /**
     * 反转数组的元素顺序。如果数组为<code>null</code>，则什么也不做。
     *
     * @param array 要反转的数组
     */
    public static void reverse(boolean[] array) {
        if (array == null) {
            return;
        }

        boolean tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 反转数组的元素顺序。如果数组为<code>null</code>，则什么也不做。
     *
     * @param array 要反转的数组
     */
    public static void reverse(char[] array) {
        if (array == null) {
            return;
        }

        char tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 反转数组的元素顺序。如果数组为<code>null</code>，则什么也不做。
     *
     * @param array 要反转的数组
     */
    public static void reverse(float[] array) {
        if (array == null) {
            return;
        }

        float tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 反转数组的元素顺序。如果数组为<code>null</code>，则什么也不做。
     *
     * @param array 要反转的数组
     */
    public static void reverse(double[] array) {
        if (array == null) {
            return;
        }

        double tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 反转数组的元素顺序。如果数组为<code>null</code>，则什么也不做。
     *
     * @param array 要反转的数组
     */
    public static void reverse(short[] array) {
        if (array == null) {
            return;
        }

        short tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 反转数组的元素顺序。如果数组为<code>null</code>，则什么也不做。
     *
     * @param array 要反转的数组
     */
    public static void reverse(int[] array) {
        if (array == null) {
            return;
        }

        int tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 反转数组的元素顺序。如果数组为<code>null</code>，则什么也不做。
     *
     * @param array 要反转的数组
     */
    public static void reverse(long[] array) {
        if (array == null) {
            return;
        }

        long tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 反转数组的元素顺序。如果数组为<code>null</code>，则什么也不做。
     *
     * @param array 要反转的数组
     */
    public static void reverse(Object[] array) {
        if (array == null) {
            return;
        }

        Object tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param objectToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int arrayIndexOf(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int arrayIndexOf(Object[] array, Object[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param objectToFind 要查找的元素
     * @param startIndex   起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param objectToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(Object[] array, Object objectToFind) {
        return lastIndexOf(array, objectToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(Object[] array, Object[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array        要扫描的数组
     * @param objectToFind 要查找的元素
     * @param startIndex   起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        if (objectToFind == null) {
            for (int i = startIndex; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i >= 0; i--) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param longToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(long[] array, long longToFind) {
        return indexOf(array, longToFind, 0);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(long[] array, long[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param longToFind 要查找的元素
     * @param startIndex 起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(long[] array, long longToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (longToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(long[] array, long[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        long first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst:
        while (true) {
            // 查找第一个元素
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 已经找到第一个元素，接着找
            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 重新查找第一个元素
                    continue startSearchForFirst;
                }
            }

            // 找到了
            return i;
        }
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param longToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(long[] array, long longToFind) {
        return lastIndexOf(array, longToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(long[] array, long[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array      要扫描的数组
     * @param longToFind 要查找的元素
     * @param startIndex 起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(long[] array, long longToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (longToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(long[] array, long[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        long last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast:
        while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 判断指定对象是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param longToFind 要查找的元素
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(long[] array, long longToFind) {
        return indexOf(array, longToFind) != -1;
    }

    /**
     * 判断指定元素序列是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(long[] array, long[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：int[]
    // ==========================================================================

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array     要扫描的数组
     * @param intToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(int[] array, int intToFind) {
        return indexOf(array, intToFind, 0);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(int[] array, int[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param intToFind  要查找的元素
     * @param startIndex 起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(int[] array, int intToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (intToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(int[] array, int[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst:
        while (true) {
            // 查找第一个元素
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 已经找到第一个元素，接着找
            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 重新查找第一个元素
                    continue startSearchForFirst;
                }
            }

            // 找到了
            return i;
        }
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array     要扫描的数组
     * @param intToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(int[] array, int intToFind) {
        return lastIndexOf(array, intToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(int[] array, int[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array      要扫描的数组
     * @param intToFind  要查找的元素
     * @param startIndex 起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(int[] array, int intToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (intToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(int[] array, int[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        int last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast:
        while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 判断指定对象是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array     要扫描的数组
     * @param intToFind 要查找的元素
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(int[] array, int intToFind) {
        return indexOf(array, intToFind) != -1;
    }

    /**
     * 判断指定元素序列是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(int[] array, int[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：short[]
    // ==========================================================================

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param shortToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(short[] array, short shortToFind) {
        return indexOf(array, shortToFind, 0);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(short[] array, short[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param shortToFind 要查找的元素
     * @param startIndex  起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(short[] array, short shortToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (shortToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(short[] array, short[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        short first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst:
        while (true) {
            // 查找第一个元素
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 已经找到第一个元素，接着找
            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 重新查找第一个元素
                    continue startSearchForFirst;
                }
            }

            // 找到了
            return i;
        }
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param shortToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(short[] array, short shortToFind) {
        return lastIndexOf(array, shortToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(short[] array, short[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param shortToFind 要查找的元素
     * @param startIndex  起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(short[] array, short shortToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (shortToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(short[] array, short[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        short last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast:
        while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 判断指定对象是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param shortToFind 要查找的元素
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(short[] array, short shortToFind) {
        return indexOf(array, shortToFind) != -1;
    }

    /**
     * 判断指定元素序列是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(short[] array, short[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：byte[]
    // ==========================================================================

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param byteToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(byte[] array, byte byteToFind) {
        return indexOf(array, byteToFind, 0);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(byte[] array, byte[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param byteToFind 要查找的元素
     * @param startIndex 起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(byte[] array, byte byteToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (byteToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(byte[] array, byte[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        byte first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst:
        while (true) {
            // 查找第一个元素
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 已经找到第一个元素，接着找
            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 重新查找第一个元素
                    continue startSearchForFirst;
                }
            }

            // 找到了
            return i;
        }
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param byteToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(byte[] array, byte byteToFind) {
        return lastIndexOf(array, byteToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(byte[] array, byte[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array      要扫描的数组
     * @param byteToFind 要查找的元素
     * @param startIndex 起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(byte[] array, byte byteToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (byteToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(byte[] array, byte[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        byte last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast:
        while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 判断指定对象是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param byteToFind 要查找的元素
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(byte[] array, byte byteToFind) {
        return indexOf(array, byteToFind) != -1;
    }

    /**
     * 判断指定元素序列是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(byte[] array, byte[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：double[]
    // ==========================================================================

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param doubleToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(double[] array, double doubleToFind) {
        return indexOf(array, doubleToFind, 0, 0);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param doubleToFind 要查找的元素
     * @param tolerance    误差
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(double[] array, double doubleToFind, double tolerance) {
        return indexOf(array, doubleToFind, 0, tolerance);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(double[] array, double[] arrayToFind) {
        return indexOf(array, arrayToFind, 0, 0);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param tolerance   误差
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(double[] array, double[] arrayToFind, double tolerance) {
        return indexOf(array, arrayToFind, 0, tolerance);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param doubleToFind 要查找的元素
     * @param startIndex   起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(double[] array, double doubleToFind, int startIndex) {
        return indexOf(array, doubleToFind, startIndex, 0);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param doubleToFind 要查找的元素
     * @param startIndex   起始索引
     * @param tolerance    误差
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(double[] array, double doubleToFind, int startIndex, double tolerance) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        double min = doubleToFind - tolerance;
        double max = doubleToFind + tolerance;

        for (int i = startIndex; i < array.length; i++) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(double[] array, double[] arrayToFind, int startIndex) {
        return indexOf(array, arrayToFind, startIndex, 0);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @param tolerance   误差
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(double[] array, double[] arrayToFind, int startIndex, double tolerance) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        double firstMin = arrayToFind[0] - tolerance;
        double firstMax = arrayToFind[0] + tolerance;
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst:
        while (true) {
            // 查找第一个元素
            while (i <= max && (array[i] < firstMin || array[i] > firstMax)) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 已经找到第一个元素，接着找
            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (Math.abs(array[j++] - arrayToFind[k++]) > tolerance) {
                    i++;

                    // 重新查找第一个元素
                    continue startSearchForFirst;
                }
            }

            // 找到了
            return i;
        }
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param doubleToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(double[] array, double doubleToFind) {
        return lastIndexOf(array, doubleToFind, Integer.MAX_VALUE, 0);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param doubleToFind 要查找的元素
     * @param tolerance    误差
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(double[] array, double doubleToFind, double tolerance) {
        return lastIndexOf(array, doubleToFind, Integer.MAX_VALUE, tolerance);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(double[] array, double[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, 0);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param tolerance   误差
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(double[] array, double[] arrayToFind, double tolerance) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, tolerance);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array        要扫描的数组
     * @param doubleToFind 要查找的元素
     * @param startIndex   起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(double[] array, double doubleToFind, int startIndex) {
        return lastIndexOf(array, doubleToFind, startIndex, 0);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array        要扫描的数组
     * @param doubleToFind 要查找的元素
     * @param startIndex   起始索引
     * @param tolerance    误差
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(double[] array, double doubleToFind, int startIndex, double tolerance) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        double min = doubleToFind - tolerance;
        double max = doubleToFind + tolerance;

        for (int i = startIndex; i >= 0; i--) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(double[] array, double[] arrayToFind, int startIndex) {
        return lastIndexOf(array, arrayToFind, startIndex, 0);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @param tolerance   误差
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(double[] array, double[] arrayToFind, int startIndex, double tolerance) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        double lastMin = arrayToFind[lastIndex] - tolerance;
        double lastMax = arrayToFind[lastIndex] + tolerance;
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast:
        while (true) {
            while (i >= min && (array[i] < lastMin || array[i] > lastMax)) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (Math.abs(array[j--] - arrayToFind[k--]) > tolerance) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 判断指定对象是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param doubleToFind 要查找的元素
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(double[] array, double doubleToFind) {
        return indexOf(array, doubleToFind) != -1;
    }

    /**
     * 判断指定对象是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param doubleToFind 要查找的元素
     * @param tolerance    误差
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(double[] array, double doubleToFind, double tolerance) {
        return indexOf(array, doubleToFind, tolerance) != -1;
    }

    /**
     * 判断指定元素序列是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(double[] array, double[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    /**
     * 判断指定元素序列是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param tolerance   误差
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(double[] array, double[] arrayToFind, double tolerance) {
        return indexOf(array, arrayToFind, tolerance) != -1;
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：float[]
    // ==========================================================================

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param floatToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(float[] array, float floatToFind) {
        return indexOf(array, floatToFind, 0, 0);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param floatToFind 要查找的元素
     * @param tolerance   误差
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(float[] array, float floatToFind, float tolerance) {
        return indexOf(array, floatToFind, 0, tolerance);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(float[] array, float[] arrayToFind) {
        return indexOf(array, arrayToFind, 0, 0);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param tolerance   误差
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(float[] array, float[] arrayToFind, float tolerance) {
        return indexOf(array, arrayToFind, 0, tolerance);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param floatToFind 要查找的元素
     * @param startIndex  起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(float[] array, float floatToFind, int startIndex) {
        return indexOf(array, floatToFind, startIndex, 0);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param floatToFind 要查找的元素
     * @param startIndex  起始索引
     * @param tolerance   误差
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(float[] array, float floatToFind, int startIndex, float tolerance) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        float min = floatToFind - tolerance;
        float max = floatToFind + tolerance;

        for (int i = startIndex; i < array.length; i++) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(float[] array, float[] arrayToFind, int startIndex) {
        return indexOf(array, arrayToFind, startIndex, 0);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @param tolerance   误差
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(float[] array, float[] arrayToFind, int startIndex, float tolerance) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        float firstMin = arrayToFind[0] - tolerance;
        float firstMax = arrayToFind[0] + tolerance;
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst:
        while (true) {
            // 查找第一个元素
            while (i <= max && (array[i] < firstMin || array[i] > firstMax)) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 已经找到第一个元素，接着找
            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (Math.abs(array[j++] - arrayToFind[k++]) > tolerance) {
                    i++;

                    // 重新查找第一个元素
                    continue startSearchForFirst;
                }
            }

            // 找到了
            return i;
        }
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param floatToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(float[] array, float floatToFind) {
        return lastIndexOf(array, floatToFind, Integer.MAX_VALUE, 0);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param floatToFind 要查找的元素
     * @param tolerance   误差
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(float[] array, float floatToFind, float tolerance) {
        return lastIndexOf(array, floatToFind, Integer.MAX_VALUE, tolerance);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(float[] array, float[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, 0);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param tolerance   误差
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(float[] array, float[] arrayToFind, float tolerance) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, tolerance);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param floatToFind 要查找的元素
     * @param startIndex  起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(float[] array, float floatToFind, int startIndex) {
        return lastIndexOf(array, floatToFind, startIndex, 0);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param floatToFind 要查找的元素
     * @param startIndex  起始索引
     * @param tolerance   误差
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(float[] array, float floatToFind, int startIndex, float tolerance) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        float min = floatToFind - tolerance;
        float max = floatToFind + tolerance;

        for (int i = startIndex; i >= 0; i--) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(float[] array, float[] arrayToFind, int startIndex) {
        return lastIndexOf(array, arrayToFind, startIndex, 0);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @param tolerance   误差
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(float[] array, float[] arrayToFind, int startIndex, float tolerance) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        float lastMin = arrayToFind[lastIndex] - tolerance;
        float lastMax = arrayToFind[lastIndex] + tolerance;
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast:
        while (true) {
            while (i >= min && (array[i] < lastMin || array[i] > lastMax)) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (Math.abs(array[j--] - arrayToFind[k--]) > tolerance) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 判断指定对象是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param floatToFind 要查找的元素
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(float[] array, float floatToFind) {
        return indexOf(array, floatToFind) != -1;
    }

    /**
     * 判断指定对象是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param floatToFind 要查找的元素
     * @param tolerance   误差
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(float[] array, float floatToFind, float tolerance) {
        return indexOf(array, floatToFind, tolerance) != -1;
    }

    /**
     * 判断指定元素序列是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(float[] array, float[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    /**
     * 判断指定元素序列是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param tolerance   误差
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(float[] array, float[] arrayToFind, float tolerance) {
        return indexOf(array, arrayToFind, tolerance) != -1;
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：boolean[]
    // ==========================================================================

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array         要扫描的数组
     * @param booleanToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(boolean[] array, boolean booleanToFind) {
        return indexOf(array, booleanToFind, 0);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(boolean[] array, boolean[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array         要扫描的数组
     * @param booleanToFind 要查找的元素
     * @param startIndex    起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(boolean[] array, boolean booleanToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (booleanToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(boolean[] array, boolean[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        boolean first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst:
        while (true) {
            // 查找第一个元素
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 已经找到第一个元素，接着找
            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 重新查找第一个元素
                    continue startSearchForFirst;
                }
            }

            // 找到了
            return i;
        }
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array         要扫描的数组
     * @param booleanToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(boolean[] array, boolean booleanToFind) {
        return lastIndexOf(array, booleanToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(boolean[] array, boolean[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array         要扫描的数组
     * @param booleanToFind 要查找的元素
     * @param startIndex    起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(boolean[] array, boolean booleanToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (booleanToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(boolean[] array, boolean[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        boolean last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast:
        while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 判断指定对象是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array         要扫描的数组
     * @param booleanToFind 要查找的元素
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(boolean[] array, boolean booleanToFind) {
        return indexOf(array, booleanToFind) != -1;
    }

    /**
     * 判断指定元素序列是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(boolean[] array, boolean[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：char[]
    // ==========================================================================

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param charToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(char[] array, char charToFind) {
        return indexOf(array, charToFind, 0);
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(char[] array, char[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 在数组中查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param charToFind 要查找的元素
     * @param startIndex 起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(char[] array, char charToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (charToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则看作<code>0</code>，超出数组长度的起始索引则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int indexOf(char[] array, char[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        char first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst:
        while (true) {
            // 查找第一个元素
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 已经找到第一个元素，接着找
            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 重新查找第一个元素
                    continue startSearchForFirst;
                }
            }

            // 找到了
            return i;
        }
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param charToFind 要查找的元素
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(char[] array, char charToFind) {
        return lastIndexOf(array, charToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(char[] array, char[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 在数组中从末尾开始查找一个元素。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array      要扫描的数组
     * @param charToFind 要查找的元素
     * @param startIndex 起始索引
     * @return 该元素在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(char[] array, char charToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (charToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 在数组中从末尾开始查找一个元素序列。
     * <p>
     * 如果未找到或数组为<code>null</code>则返回<code>-1</code>。
     * </p>
     * <p>
     * 起始索引小于<code>0</code>则返回<code>-1</code>，超出数组长度的起始索引则从数组末尾开始找。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @param startIndex  起始索引
     * @return 该元素序列在数组中的序号，如果数组为<code>null</code>或未找到，则返回<code>-1</code>。
     */
    public static int lastIndexOf(char[] array, char[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        char last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast:
        while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 判断指定对象是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array      要扫描的数组
     * @param charToFind 要查找的元素
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(char[] array, char charToFind) {
        return indexOf(array, charToFind) != -1;
    }

    /**
     * 判断指定元素序列是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(char[] array, char[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    @SuppressWarnings("unchecked")
    public static Object[] toArray(Object value){
        if(value==null){
            return EMPTY_ARRAY;
        }
        Object[] values;
        if(value.getClass().isArray()){
            values=(Object[])value;
        }else if(List.class.isInstance(value)){
            List<Object> list=(List<Object>)value;
            values=list.toArray();
        }else{
            values=new Object[]{value};
        }
        return values;
    }



    /**
     * 判断指定对象是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array        要扫描的数组
     * @param objectToFind 要查找的元素
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(Object[] array, Object objectToFind) {
        return arrayIndexOf(array, objectToFind) != -1;
    }

    /**
     * 判断指定元素序列是否存在于指定数组中。
     * <p>
     * 如果数组为<code>null</code>则返回<code>false</code>。
     * </p>
     *
     * @param array       要扫描的数组
     * @param arrayToFind 要查找的元素序列
     * @return 如果找到则返回<code>true</code>
     */
    public static boolean contains(Object[] array, Object[] arrayToFind) {
        return arrayIndexOf(array, arrayToFind) != -1;
    }


    /**
     * 克隆一个数组
     *
     * @param array
     * @return
     */
    public static boolean[] clone(boolean[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * 克隆一个数组
     *
     * @param array
     * @return
     */
    public static byte[] clone(byte[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * 克隆一个数组
     *
     * @param array
     * @return
     */
    public static char[] clone(char[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * 克隆一个数组
     *
     * @param array
     * @return
     */
    public static short[] clone(short[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * 克隆一个数组
     *
     * @param array
     * @return
     */
    public static int[] clone(int[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * 克隆一个数组
     *
     * @param array
     * @return
     */
    public static long[] clone(long[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * 克隆一个数组
     *
     * @param array
     * @return
     */
    public static float[] clone(float[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * 克隆一个数组
     *
     * @param array
     * @return
     */
    public static double[] clone(double[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * 克隆一个数组
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> T[] clone(T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>截取数组</p>
     * <p>如果元素数组为空,返回null;
     * 如果截取起始位置小于0，起始位置为原始数组的第一个元素;
     * 如果截取结束为止大于元素数组长度，结束为止为原始数组的最后一个元素;</p>
     *
     * @param array      原始数组
     * @param startIndex 截取起始位置
     * @param endIndex   截取结束位置
     * @return 截取后的数组
     */
    public static boolean[] subArray(boolean[] array, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > array.length - 1) {
            endIndex = array.length - 1;
        }
        int size = endIndex - startIndex + 1;
        boolean[] subArray = new boolean[size];
        for (int i = startIndex, j = 0; i <= endIndex; i++, j++) {
            subArray[j] = array[i];
        }
        return subArray;
    }

    /**
     * <p>截取数组</p>
     * <p>如果元素数组为空,返回null;
     * 如果截取起始位置小于0，起始位置为原始数组的第一个元素;
     * 如果截取结束为止大于元素数组长度，结束为止为原始数组的最后一个元素;</p>
     *
     * @param array      原始数组
     * @param startIndex 截取起始位置
     * @param endIndex   截取结束位置
     * @return 截取后的数组
     */
    public static byte[] subArray(byte[] array, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > array.length - 1) {
            endIndex = array.length - 1;
        }
        int size = endIndex - startIndex + 1;
        byte[] subArray = new byte[size];
        for (int i = startIndex, j = 0; i <= endIndex; i++, j++) {
            subArray[j] = array[i];
        }
        return subArray;
    }

    /**
     * <p>截取数组</p>
     * <p>如果元素数组为空,返回null;
     * 如果截取起始位置小于0，起始位置为原始数组的第一个元素;
     * 如果截取结束为止大于元素数组长度，结束为止为原始数组的最后一个元素;</p>
     *
     * @param array      原始数组
     * @param startIndex 截取起始位置
     * @param endIndex   截取结束位置
     * @return 截取后的数组
     */
    public static char[] subArray(char[] array, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > array.length - 1) {
            endIndex = array.length - 1;
        }
        int size = endIndex - startIndex + 1;
        char[] subArray = new char[size];
        for (int i = startIndex, j = 0; i <= endIndex; i++, j++) {
            subArray[j] = array[i];
        }
        return subArray;
    }

    /**
     * <p>截取数组</p>
     * <p>如果元素数组为空,返回null;
     * 如果截取起始位置小于0，起始位置为原始数组的第一个元素;
     * 如果截取结束为止大于元素数组长度，结束为止为原始数组的最后一个元素;</p>
     *
     * @param array      原始数组
     * @param startIndex 截取起始位置
     * @param endIndex   截取结束位置
     * @return 截取后的数组
     */
    public static short[] subArray(short[] array, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > array.length - 1) {
            endIndex = array.length - 1;
        }
        int size = endIndex - startIndex + 1;
        short[] subArray = new short[size];
        for (int i = startIndex, j = 0; i <= endIndex; i++, j++) {
            subArray[j] = array[i];
        }
        return subArray;
    }

    /**
     * <p>截取数组</p>
     * <p>如果元素数组为空,返回null;
     * 如果截取起始位置小于0，起始位置为原始数组的第一个元素;
     * 如果截取结束为止大于元素数组长度，结束为止为原始数组的最后一个元素;</p>
     *
     * @param array      原始数组
     * @param startIndex 截取起始位置
     * @param endIndex   截取结束位置
     * @return 截取后的数组
     */
    public static int[] subArray(int[] array, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > array.length - 1) {
            endIndex = array.length - 1;
        }
        int size = endIndex - startIndex + 1;
        int[] subArray = new int[size];
        for (int i = startIndex, j = 0; i <= endIndex; i++, j++) {
            subArray[j] = array[i];
        }
        return subArray;
    }

    /**
     * <p>截取数组</p>
     * <p>如果元素数组为空,返回null;
     * 如果截取起始位置小于0，起始位置为原始数组的第一个元素;
     * 如果截取结束为止大于元素数组长度，结束为止为原始数组的最后一个元素;</p>
     *
     * @param array      原始数组
     * @param startIndex 截取起始位置
     * @param endIndex   截取结束位置
     * @return 截取后的数组
     */
    public static long[] subArray(long[] array, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > array.length - 1) {
            endIndex = array.length - 1;
        }
        int size = endIndex - startIndex + 1;
        long[] subArray = new long[size];
        for (int i = startIndex, j = 0; i <= endIndex; i++, j++) {
            subArray[j] = array[i];
        }
        return subArray;
    }

    /**
     * <p>截取数组</p>
     * <p>如果元素数组为空,返回null;
     * 如果截取起始位置小于0，起始位置为原始数组的第一个元素;
     * 如果截取结束为止大于元素数组长度，结束为止为原始数组的最后一个元素;</p>
     *
     * @param array      原始数组
     * @param startIndex 截取起始位置
     * @param endIndex   截取结束位置
     * @return 截取后的数组
     */
    public static float[] subArray(float[] array, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > array.length - 1) {
            endIndex = array.length - 1;
        }
        int size = endIndex - startIndex + 1;
        float[] subArray = new float[size];
        for (int i = startIndex, j = 0; i <= endIndex; i++, j++) {
            subArray[j] = array[i];
        }
        return subArray;
    }

    /**
     * <p>截取数组</p>
     * <p>如果元素数组为空,返回null;
     * 如果截取起始位置小于0，起始位置为原始数组的第一个元素;
     * 如果截取结束为止大于元素数组长度，结束为止为原始数组的最后一个元素;</p>
     *
     * @param array      原始数组
     * @param startIndex 截取起始位置
     * @param endIndex   截取结束位置
     * @return 截取后的数组
     */
    public static double[] subArray(double[] array, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > array.length - 1) {
            endIndex = array.length - 1;
        }
        int size = endIndex - startIndex + 1;
        double[] subArray = new double[size];
        for (int i = startIndex, j = 0; i <= endIndex; i++, j++) {
            subArray[j] = array[i];
        }
        return subArray;
    }

    /**
     * <p>截取数组</p>
     * <p>如果元素数组为空,返回null;
     * 如果截取起始位置小于0，起始位置为原始数组的第一个元素;
     * 如果截取结束为止大于元素数组长度，结束为止为原始数组的最后一个元素;</p>
     *
     * @param array 原始数组
     * @param startIndex    截取起始位置
     * @param endIndex  截取结束位置
     * @param <T>   数组类型
     * @return 截取后的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] subArray(T[] array, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > array.length - 1) {
            endIndex = array.length - 1;
        }
        int size = endIndex - startIndex + 1;
        Class type = array.getClass().getComponentType();
        if (size <= 0) {
            return (T[]) Array.newInstance(type, 0);
        }
        T[] subArray = (T[]) Array.newInstance(type, size);
        System.arraycopy(array, startIndex, subArray, 0, size);
        return subArray;
    }

    /**
     * <p>将数组转成字符串</p>
     *
     * @param array
     * @return
     */
    public static String toString(boolean[] array) {
        if (array == null) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * <p>将数组转成字符串</p>
     *
     * @param array
     * @return
     */
    public static String toString(byte[] array) {
        if (array == null) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * <p>将数组转成字符串</p>
     *
     * @param array
     * @return
     */
    public static String toString(char[] array) {
        if (array == null) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * <p>将数组转成字符串</p>
     *
     * @param array
     * @return
     */
    public static String toString(short[] array) {
        if (array == null) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * <p>将数组转成字符串</p>
     *
     * @param array
     * @return
     */
    public static String toString(int[] array) {
        if (array == null) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * <p>将数组转成字符串</p>
     *
     * @param array
     * @return
     */
    public static String toString(long[] array) {
        if (array == null) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * <p>将数组转成字符串</p>
     *
     * @param array
     * @return
     */
    public static String toString(float[] array) {
        if (array == null) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * <p>将数组转成字符串</p>
     *
     * @param array
     * @return
     */
    public static String toString(double[] array) {
        if (array == null) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * <p>将数组转成字符串</p>
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> String toString(T[] array) {
        if (array == null) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i].toString());
            if (i != array.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }
}
