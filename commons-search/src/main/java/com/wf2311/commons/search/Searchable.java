package com.wf2311.commons.search;

import java.util.List;
import java.util.Map;

/**
 * 可搜索对象
 *
 * @author wf2311
 * @date 2016/04/06 23:44.
 */
public interface Searchable extends Comparable<Searchable> {
    /**
     * 文档的唯一编号
     *
     * @return 文档id
     */
    long getId();

    void setId(long id);

    /**
     * 要存储的字段
     *
     * @return 返回字段名列表
     */
    List<String> storeFields();

    /**
     * 要进行分词索引的字段
     *
     * @return 返回字段名列表
     */
    List<String> indexFields();

    /**
     * 文档的优先级
     *
     * @return
     */
    float boost();

    /**
     * 扩展的存储数据
     *
     * @return 扩展数据K/V
     */
    Map<String, String> extendStoreDatas();

    /**
     * 扩展的索引数据
     *
     * @return 扩展数据K/V
     */
    Map<String, String> extendIndexDatas();

    /**
     * 列出id值大于指定值得所有对象
     *
     * @param id
     * @param count
     * @return
     */
    List<? extends Searchable> ListAfter(long id, int count);
}
