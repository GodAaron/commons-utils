package com.wf2311.commons.lang;


import com.wf2311.commons.exception.WfException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.*;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wf2311
 * @date: 2015/10/20 17:18
 */
public class CollectionUtils {

    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Return {@code true} if the supplied Map is {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * Convert the supplied array into a List. A primitive array gets converted
     * into a List of the appropriate wrapper type.
     * <p><b>NOTE:</b> Generally prefer the standard {@link Arrays#asList} method.
     * This {@code arrayToList} method is just meant to deal with an incoming Object
     * value that might be an {@code Object[]} or a primitive array at runtime.
     * <p>A {@code null} source value will be converted to an empty List.
     *
     * @param source the (potentially primitive) array
     * @return the converted List result
     * @see ObjectUtils#toObjectArray(Object)
     * @see Arrays#asList(Object[])
     */
    @SuppressWarnings("rawtypes")
    public static List arrayToList(Object source) {
        return Arrays.asList(ObjectUtils.toObjectArray(source));
    }


    /**
     * 将两个List组装成Map
     * <p>当keys.size()!=values.size()&&sizeMustEqual==true时，会抛出WfException异常，<br>
     * 反之，则忽略两个List的大小是否相等，取其二者size最小值作为Map的大小，按两个List内容的索引顺序，<br>
     * 依次将其作为键、值放入Map中
     * </p>
     *
     * @param keys          键List
     * @param values        值List
     * @param sizeMustEqual 键List与值List的大小是否必须相等
     * @param <K>           键List的类型
     * @param <V>           值List的类型
     * @return 键List和值List组成的Map
     */
    public static <K, V> Map<K, V> getMap(List<K> keys, List<V> values, boolean sizeMustEqual) {
        Map<K, V> map = new HashMap<>();
        if (keys.size() != values.size() && sizeMustEqual) {
            throw new WfException("键列表的大小和值列表的大小不想等");
        }
        int size = keys.size() > values.size() ? values.size() : keys.size();
        for (int i = 0; i < size; i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }

    /**
     * 将两个List组装成Map
     * <p>忽略两个List的大小是否相等，取其二者size最小值作为Map的大小，按两个List内容的索引顺序，<br>
     * 依次将其作为键、值放入Map中。若必须要确保两个List的大小相等，请调用{@link CollectionUtils#getMap(List, List, boolean)}方法
     * </p>
     *
     * @param keys   键List
     * @param values 值List
     * @param <K>    键List的类型
     * @param <V>    值List的类型
     * @return 键List和值List组成的Map
     */
    public static <K, V> Map<K, V> getMap(List<K> keys, List<V> values) {
        return getMap(keys, values, false);
    }


    /**
     * 将多个列表合并按索引顺序合并成一个列表
     *
     * @param flag       -1:最小合并；0：全相等合并；1：最大化合并
     * @param names      每个列表的说明
     * @param separators 分隔符
     * @param lists      列表
     * @return
     */
    public static List<String> getList(int flag, String[] names, String[] separators, List... lists) {
        if (separators.length != lists.length || names.length != lists.length) {
            throw new WfException("分割符数组长度不符合要求");
        }
        int[] sizes = new int[lists.length];
        for (int i = 0; i < lists.length; i++) {
            sizes[i] = lists[i].size();
        }
        Arrays.sort(sizes);
        int min = sizes[0], max = sizes[sizes.length - 1], size;
        if (flag == 0 && min != max) {
            throw new WfException("列表长度不完全一致");
        }
        if (flag > 0) {
            size = max;
        } else {
            size = min;
        }
        List list = new ArrayList<>();
        for (int i = 0; i < lists.length; i++) {
            lists[i] = sizeToSet(lists[i], size);
        }
        for (int i = 0; i < size; i++) {
            String s = "";
            for (int j = 0; j < lists.length; j++) {
                s += (names[j] == null ? "" : names[j]) + lists[j].get(i) + (separators[j] == null ? "" : separators[j]);
            }
            list.add(s);
        }

        return list;
    }

    /**
     * 将List调整到指定大小。
     * <p>若list.size()>size，则会从list中解除后半多余部分；</b>
     * 若list.size()<size,则会在最后补充null</p>
     *
     * @param list
     * @param size
     * @return
     */
    public static List sizeToSet(List list, int size) {
        int s = list.size();
        if (size <= s) {
            list = list.subList(0, size);
        } else {
            for (int i = 0; i < size - s; i++) {
                list.add(null);
            }
        }
        return list;
    }

    /**
     * 创建一个<code>ArrayList</code>。
     */
    public static <T> ArrayList<T> createArrayList() {
        return new ArrayList<T>();
    }

    /**
     * 创建一个<code>ArrayList</code>。
     */
    public static <T> ArrayList<T> createArrayList(int initialCapacity) {
        return new ArrayList<T>(initialCapacity);
    }

    /**
     * 创建一个<code>ArrayList</code>。
     */
    public static <T> ArrayList<T> createArrayList(Iterable<? extends T> c) {
        ArrayList<T> list;

        if (c instanceof Collection<?>) {
            list = new ArrayList<T>((Collection<? extends T>) c);
        } else {
            list = new ArrayList<T>();

            iterableToCollection(c, list);

            list.trimToSize();
        }

        return list;
    }

    /**
     * 创建一个<code>ArrayList</code>。
     */
    public static <T, V extends T> ArrayList<T> createArrayList(V... args) {
        if (args == null || args.length == 0) {
            return new ArrayList<T>();
        } else {
            ArrayList<T> list = new ArrayList<T>(args.length);

            for (V v : args) {
                list.add(v);
            }

            return list;
        }
    }

    /**
     * 创建一个<code>LinkedList</code>。
     */
    public static <T> LinkedList<T> createLinkedList() {
        return new LinkedList<T>();
    }

    /**
     * 创建一个<code>LinkedList</code>。
     */
    public static <T> LinkedList<T> createLinkedList(Iterable<? extends T> c) {
        LinkedList<T> list = new LinkedList<T>();

        iterableToCollection(c, list);

        return list;
    }

    /**
     * 创建一个<code>LinkedList</code>。
     */
    public static <T, V extends T> LinkedList<T> createLinkedList(V... args) {
        LinkedList<T> list = new LinkedList<T>();

        if (args != null) {
            for (V v : args) {
                list.add(v);
            }
        }

        return list;
    }

    /**
     * 创建一个<code>List</code>。
     * <p>
     * 和{@code createArrayList(args)}不同，本方法会返回一个不可变长度的列表，且性能高于
     * {@code createArrayList(args)}。
     * </p>
     */
    public static <T> List<T> asList(T... args) {
        if (args == null || args.length == 0) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(args);
        }
    }

    /**
     * 创建一个<code>HashMap</code>。
     */
    public static <K, V> HashMap<K, V> createHashMap() {
        return new HashMap<K, V>();
    }

    /**
     * 创建一个<code>HashMap</code>。
     */
    public static <K, V> HashMap<K, V> createHashMap(int initialCapacity) {
        return new HashMap<K, V>(initialCapacity);
    }

    /**
     * 创建一个<code>LinkedHashMap</code>。
     */
    public static <K, V> LinkedHashMap<K, V> createLinkedHashMap() {
        return new LinkedHashMap<K, V>();
    }

    /**
     * 创建一个<code>LinkedHashMap</code>。
     */
    public static <K, V> LinkedHashMap<K, V> createLinkedHashMap(int initialCapacity) {
        return new LinkedHashMap<K, V>(initialCapacity);
    }

    /**
     * 创建一个<code>TreeMap</code>。
     */
    public static <K, V> TreeMap<K, V> createTreeMap() {
        return new TreeMap<K, V>();
    }

    /**
     * 创建一个<code>TreeMap</code>。
     */
    public static <K, V> TreeMap<K, V> createTreeMap(Comparator<? super K> comparator) {
        return new TreeMap<K, V>(comparator);
    }

    /**
     * 创建一个<code>ConcurrentHashMap</code>。
     */
    public static <K, V> ConcurrentHashMap<K, V> createConcurrentHashMap() {
        return new ConcurrentHashMap<K, V>();
    }

    /**
     * 创建一个<code>HashSet</code>。
     */
    public static <T> HashSet<T> createHashSet() {
        return new HashSet<T>();
    }

    /**
     * 创建一个<code>HashSet</code>。
     */
    public static <T, V extends T> HashSet<T> createHashSet(V... args) {
        if (args == null || args.length == 0) {
            return new HashSet<T>();
        } else {
            HashSet<T> set = new HashSet<T>(args.length);

            for (V v : args) {
                set.add(v);
            }

            return set;
        }
    }

    /**
     * 创建一个<code>HashSet</code>。
     */
    public static <T> HashSet<T> createHashSet(Iterable<? extends T> c) {
        HashSet<T> set;

        if (c instanceof Collection<?>) {
            set = new HashSet<T>((Collection<? extends T>) c);
        } else {
            set = new HashSet<T>();
            iterableToCollection(c, set);
        }

        return set;
    }

    /**
     * 创建一个<code>LinkedHashSet</code>。
     */
    public static <T> LinkedHashSet<T> createLinkedHashSet() {
        return new LinkedHashSet<T>();
    }

    /**
     * 创建一个<code>LinkedHashSet</code>。
     */
    public static <T, V extends T> LinkedHashSet<T> createLinkedHashSet(V... args) {
        if (args == null || args.length == 0) {
            return new LinkedHashSet<T>();
        } else {
            LinkedHashSet<T> set = new LinkedHashSet<T>(args.length);

            for (V v : args) {
                set.add(v);
            }

            return set;
        }
    }

    /**
     * 创建一个<code>LinkedHashSet</code>。
     */
    public static <T> LinkedHashSet<T> createLinkedHashSet(Iterable<? extends T> c) {
        LinkedHashSet<T> set;

        if (c instanceof Collection<?>) {
            set = new LinkedHashSet<T>((Collection<? extends T>) c);
        } else {
            set = new LinkedHashSet<T>();
            iterableToCollection(c, set);
        }

        return set;
    }

    /**
     * 创建一个<code>TreeSet</code>。
     */
    public static <T> TreeSet<T> createTreeSet() {
        return new TreeSet<T>();
    }

    /**
     * 创建一个<code>TreeSet</code>。
     */
    @SuppressWarnings("unchecked")
    public static <T, V extends T> TreeSet<T> createTreeSet(V... args) {
        return (TreeSet<T>) createTreeSet(null, args);
    }

    /**
     * 创建一个<code>TreeSet</code>。
     */
    public static <T> TreeSet<T> createTreeSet(Iterable<? extends T> c) {
        return createTreeSet(null, c);
    }

    /**
     * 创建一个<code>TreeSet</code>。
     */
    public static <T> TreeSet<T> createTreeSet(Comparator<? super T> comparator) {
        return new TreeSet<T>(comparator);
    }

    /**
     * 创建一个<code>TreeSet</code>。
     */
    public static <T, V extends T> TreeSet<T> createTreeSet(Comparator<? super T> comparator, V... args) {
        TreeSet<T> set = new TreeSet<T>(comparator);

        if (args != null) {
            for (V v : args) {
                set.add(v);
            }
        }

        return set;
    }

    /**
     * 创建一个<code>TreeSet</code>。
     */
    public static <T> TreeSet<T> createTreeSet(Comparator<? super T> comparator, Iterable<? extends T> c) {
        TreeSet<T> set = new TreeSet<T>(comparator);

        iterableToCollection(c, set);

        return set;
    }


    private static <T> void iterableToCollection(Iterable<? extends T> c, Collection<T> list) {
        for (T element : c) {
            list.add(element);
        }
    }

    public static String[] toNoNullStringArray(Collection collection) {
        if (collection == null) {
            return org.apache.commons.lang.ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return toNoNullStringArray(collection.toArray());
    }

    static String[] toNoNullStringArray(Object[] array) {
        ArrayList list = new ArrayList(array.length);
        for (int i = 0; i < array.length; i++) {
            Object e = array[i];
            if (e != null) {
                list.add(e.toString());
            }
        }
        return (String[]) list.toArray(org.apache.commons.lang.ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /////////////////////////////////

    /**
     * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
     *
     * @param collection        来源集合.
     * @param keyPropertyName   要提取为Map中的Key值的属性名.
     * @param valuePropertyName 要提取为Map中的Value值的属性名.
     */
    public static Map extractToMap(final Collection collection, final String keyPropertyName,
                                   final String valuePropertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map map = new HashMap(collection.size());
        for (Object obj : collection) {
            map.put(PropertyUtils.getProperty(obj, keyPropertyName),
                    PropertyUtils.getProperty(obj, valuePropertyName));
        }
        return map;
    }

    /**
     * 提取集合中的对象的某个属性(通过Getter函数)作为key,集合对象作为值，组合成Map.
     *
     * @param collection      来源集合.
     * @param keyPropertyName 要提取为Map中的Key值的属性名.
     */
    public static Map extractIndexToMap(final Collection collection, final String keyPropertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map map = new HashMap(collection.size());
        for (Object obj : collection) {
            map.put(PropertyUtils.getProperty(obj, keyPropertyName), obj);
        }
        return map;
    }


    /**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static List extractToList(final Collection collection, final String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List list = new ArrayList(collection.size());
        for (Object obj : collection) {
            list.add(PropertyUtils.getProperty(obj, propertyName));
        }

        return list;
    }

    /**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成由分割符分隔的字符串.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     * @param separator    分隔符.
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static String extractToString(final Collection collection, final String propertyName, final String separator) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List list = extractToList(collection, propertyName);
        return org.apache.commons.lang.StringUtils.join(list, separator);
    }

    /**
     * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
     *
     * @param collection
     * @param separator
     * @return
     */
    public static String convertToString(final Collection collection, final String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * 转换Collection所有元素(通过toString())为String, 每个元素的前面加入prefix，后面加入postfix，如<div>mymessage</div>。
     *
     * @param collection
     * @param prefix
     * @param postfix
     * @return
     */
    public static String convertToString(final Collection collection, final String prefix, final String postfix) {
        StringBuilder builder = new StringBuilder();
        for (Object o : collection) {
            builder.append(prefix).append(o).append(postfix);
        }
        return builder.toString();
    }


    /**
     * 取得Collection的第一个元素，如果collection为空返回null.
     *
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> T getFirst(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.iterator().next();
    }

    /**
     * 获取Collection的最后一个元素 ，如果collection为空返回null.
     *
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> T getLast(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }

        //当类型为List时，直接取得最后一个元素 。
        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(list.size() - 1);
        }

        //其他类型通过iterator滚动到最后一个元素.
        Iterator<T> iterator = collection.iterator();
        while (true) {
            T current = iterator.next();
            if (!iterator.hasNext()) {
                return current;
            }
        }
    }

    /**
     * 返回a+b的新List.
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
        List<T> result = new ArrayList<T>(a);
        result.addAll(b);
        return result;
    }

    /**
     * 返回a-b的新List.
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
        List<T> list = new ArrayList<T>(a);
        for (T element : b) {
            list.remove(element);
        }
        return list;
    }

    /**
     * 返回a与b的交集的新List.
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<T>();

        for (T element : a) {
            if (b.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }
}
