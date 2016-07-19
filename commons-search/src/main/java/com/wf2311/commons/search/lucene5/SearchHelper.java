package com.wf2311.commons.search.lucene5;

import com.wf2311.commons.search.Searchable;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.*;

import java.util.*;

/**
 * @author wf2311
 * @time 2016/04/07 23:56.
 */
public class SearchHelper {
    private final static Logger logger = Logger.getLogger(SearchHelper.class);
    public final static String FN_ID = "___id";
    public final static String FN_CLASSNAME = "___class";

    /**
     * 返回文档中保存的对象 id
     *
     * @param doc
     * @return
     */
    public static long docId(Document doc) {
        return NumberUtils.toLong(doc.get(FN_ID), 0);
    }

    /**
     * 获取文档对应的对象类
     *
     * @param doc
     * @return
     * @throws ClassNotFoundException
     */
    public static Searchable doc2Obj(Document doc) {
        try {
            long id = docId(doc);
            if (id <= 0)
                return null;
            Searchable obj = (Searchable) Class.forName(doc.get(FN_CLASSNAME)).newInstance();
            obj.setId(id);
            return obj;
        } catch (Exception e) {
            logger.error("Unabled generate object from document#id=" + doc.toString(), e);
            return null;
        }
    }

    /**
     * 将对象转成 Lucene 的文档
     *
     * @param obj Java 对象
     * @return 返回Lucene文档
     */
    public static Document obj2Doc(Searchable obj) {
        if (obj == null)
            return null;

        Document doc = new Document();
        doc.add(new LongField(FN_ID, obj.getId(), Field.Store.YES));
        doc.add(new StoredField(FN_CLASSNAME, obj.getClass().getName()));

        //存储字段
        List<String> fields = obj.storeFields();
        if (fields != null) {
            for (String name : fields) {
                Object value = readField(obj, name);
                if (value != null)
                    doc.add(obj2Field(name, value, true));
            }
        }

        //扩展存储字段
        Map<String, String> eDatas = obj.extendStoreDatas();
        if (eDatas != null) {
            for (String name : eDatas.keySet()) {
                if (fields != null && fields.contains(name))
                    continue;
                String value = eDatas.get(name);
                if (value != null)
                    doc.add(obj2Field(name, value, true));
            }
        }

        //索引字段
        fields = obj.indexFields();
        if (fields != null) {
            for (String name : fields) {
                String value = (String) readField(obj, name);
                if (value != null) {
                    TextField tf = new TextField(name, value, Field.Store.NO);
                    tf.setBoost(obj.boost());
                    doc.add(tf);
                }
            }
        }

        //扩展索引字段
        eDatas = obj.extendIndexDatas();
        if (eDatas != null) {
            for (String name : eDatas.keySet()) {
                if (fields != null && fields.contains(name))
                    continue;
                String value = eDatas.get(name);
                if (value != null) {
                    TextField tf = new TextField(name, value, Field.Store.NO);
                    tf.setBoost(obj.boost());
                    doc.add(tf);
                }
            }
        }
        return doc;
    }

    public static Collection<Document> objs2Docs(Collection<Searchable> objs) {
        Collection<Document> collection = new ArrayList<>();
        for (Searchable obj : objs) {
            if (obj != null) {
                collection.add(obj2Doc(obj));
            }
        }
        return collection;
    }

    /**
     * 获取高亮文本
     *
     * @param query
     * @param target
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static String getBestFragment(Query query, String target, String fieldName, Analyzer analyzer) throws Exception {
        org.apache.lucene.search.highlight.Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
        Scorer scorer = new QueryScorer(query);
        Highlighter hlighter = new Highlighter(formatter, scorer);

        // 设置文本摘要大小
        Fragmenter fg = new SimpleFragmenter(200);
        hlighter.setTextFragmenter(fg);

        String result = hlighter.getBestFragment(analyzer, fieldName, target);
        result = StringUtils.isBlank(result) ? target.substring(0, Math.min(200, target.length())) : result;
        return result;
    }

    /**
     * 访问对象某个属性的值
     *
     * @param obj   对象
     * @param field 属性名
     * @return Lucene 文档字段
     */
    private static Object readField(Object obj, String field) {
        try {
            return PropertyUtils.getProperty(obj, field);
        } catch (Exception e) {
            logger.error("Unabled to get property '" + field + "' of " + obj.getClass().getName(), e);
            return null;
        }

    }

    private static Field obj2Field(String field, Object fieldValue, boolean isStore) {
        Field.Store store = isStore ? Field.Store.YES : Field.Store.NO;
        if (fieldValue == null) {
            return null;
        }
        if (fieldValue instanceof Date) //日期
            return new LongField(field, ((Date) fieldValue).getTime(), store);
        if (fieldValue instanceof Number) //其他数值
            return new StringField(field, String.valueOf(((Number) fieldValue).longValue()), store);
        //其他默认当字符串处理
        return new StringField(field, (String) fieldValue, store);
    }

    private static Field obj2Field(String field, Object fieldValue, FieldType fieldType) {
        if (fieldValue == null) {
            return null;
        }
        if (fieldValue instanceof Date) //日期
            return new LongField(field, ((Date) fieldValue).getTime(), fieldType);
        if (fieldValue instanceof Number) //其他数值
            return new Field(field, String.valueOf(((Number) fieldValue).longValue()), fieldType);
        //其他默认当字符串处理
        return new Field(field, (String) fieldValue, fieldType);
    }
}
