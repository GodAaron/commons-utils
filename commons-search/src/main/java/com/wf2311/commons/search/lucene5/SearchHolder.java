package com.wf2311.commons.search.lucene5;

import com.wf2311.commons.domain.Pager;
import com.wf2311.commons.lang.plugin.MapContainer;
import com.wf2311.commons.search.IKAnalyzer.IKAnalyzer5x;
import com.wf2311.commons.search.Searchable;
import com.wf2311.commons.search.lucene5.lang.DocConverter;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * @author wf2311
 * @time 2016/04/14 21:18.
 */
public class SearchHolder {
    private final static Logger logger = Logger.getLogger(SearchHolder.class);
    private final static LuceneManager luceneManager = LuceneManager.getInstance();
    private final static Analyzer analyzer = new IKAnalyzer5x();

    public static Analyzer getAnalyzer() {
        return analyzer;
    }

    /**
     * 打开索引目录
     *
     * @param luceneDir
     * @return
     * @throws IOException
     */
    public static FSDirectory openFSDirectory(String luceneDir) {
        FSDirectory directory = null;
        try {
            directory = FSDirectory.open(Paths.get(luceneDir));
            /**
             * 注意：isLocked方法内部会试图去获取Lock,如果获取到Lock，会关闭它，否则return false表示索引目录没有被锁，
             * 这也就是为什么unlock方法被从IndexWriter类中移除的原因
             */
            IndexWriter.isLocked(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directory;
    }

    /**
     * 关闭索引目录并销毁
     *
     * @param directory
     * @throws IOException
     */
    public static void closeDirectory(Directory directory) throws IOException {
        if (null != directory) {
            directory.close();
            directory = null;
        }
    }

    /**
     * 获取IndexWriter
     *
     * @param dir
     * @param config
     * @return
     */
    public static IndexWriter getIndexWriter(Directory dir, IndexWriterConfig config) {
        return luceneManager.getIndexWriter(dir, config);
    }

    /**
     * 获取IndexWriter
     *
     * @param directoryPath
     * @param config
     * @return
     */
    public static IndexWriter getIndexWrtier(String directoryPath, IndexWriterConfig config) {
        FSDirectory directory = openFSDirectory(directoryPath);
        return luceneManager.getIndexWriter(directory, config);
    }

    /**
     * 获取IndexReader
     *
     * @param dir
     * @param enableNRTReader 是否开启NRTReader
     * @return
     */
    public static IndexReader getIndexReader(Directory dir, boolean enableNRTReader) {
        return luceneManager.getIndexReader(dir, enableNRTReader);
    }

    /**
     * 获取IndexReader(默认不启用NRTReader)
     *
     * @param dir
     * @return
     */
    public static IndexReader getIndexReader(Directory dir) {
        return luceneManager.getIndexReader(dir);
    }

    /**
     * 获取IndexSearcher
     *
     * @param reader   IndexReader对象
     * @param executor 如果你需要开启多线程查询，请提供ExecutorService对象参数
     * @return
     */
    public static IndexSearcher getIndexSearcher(IndexReader reader, ExecutorService executor) {
        return luceneManager.getIndexSearcher(reader, executor);
    }

    /**
     * 获取IndexSearcher(不支持多线程查询)
     *
     * @param reader IndexReader对象
     * @return
     */
    public static IndexSearcher getIndexSearcher(IndexReader reader) {
        return luceneManager.getIndexSearcher(reader);
    }

    /**
     * 创建QueryParser对象
     *
     * @param field
     * @param analyzer
     * @return
     */
    public static QueryParser createQueryParser(String field, Analyzer analyzer) {
        return new QueryParser(field, analyzer);
    }

    /**
     * 关闭IndexReader
     *
     * @param reader
     */
    public static void closeIndexReader(IndexReader reader) {
        if (null != reader) {
            try {
                reader.close();
                reader = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭IndexWriter
     *
     * @param writer
     */
    public static void closeIndexWriter(IndexWriter writer) {
        luceneManager.closeIndexWriter(writer);
    }

    /**
     * 关闭IndexReader和IndexWriter
     *
     * @param reader
     * @param writer
     */
    public static void closeAll(IndexReader reader, IndexWriter writer) {
        closeIndexReader(reader);
        closeIndexWriter(writer);
    }

    /**
     * 删除索引[注意：请自己关闭IndexWriter对象]
     *
     * @param writer
     * @param field
     * @param value
     */
    public static void deleteIndex(IndexWriter writer, String field, String value) {
        try {
            writer.deleteDocuments(new Term(field, value));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除索引[注意：请自己关闭IndexWriter对象]
     *
     * @param writer
     * @param query
     */
    public static void deleteIndex(IndexWriter writer, Query query) {
        try {
            writer.deleteDocuments(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除索引[注意：请自己关闭IndexWriter对象]
     *
     * @param writer
     * @param terms
     */
    public static void deleteIndexs(IndexWriter writer, Term[] terms) {
        try {
            writer.deleteDocuments(terms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除索引[注意：请自己关闭IndexWriter对象]
     *
     * @param writer
     * @param querys
     */
    public static void deleteIndexs(IndexWriter writer, Query[] querys) {
        try {
            writer.deleteDocuments(querys);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有索引文档
     *
     * @param writer
     */
    public static void deleteAllIndex(IndexWriter writer) {
        try {
            writer.deleteAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void optimize(IndexWriter writer, Class<? extends Searchable> objClass) throws IOException {
        try {
            writer.forceMerge(1);
            writer.commit();
        } finally {
            closeIndexWriter(writer);
        }
    }

    /**
     * 添加索引
     *
     * @param doc
     * @return
     * @throws IOException
     */
    public static int add(IndexWriter writer, Document doc) throws IOException {
        if (doc == null) {
            return 0;
        }
        int count = 0;
        try {
            writer.addDocument(doc);
            count++;
            writer.commit();
        } finally {
            closeIndexWriter(writer);
            return count;
        }
    }

    /**
     * 添加索引
     *
     * @param searchable
     * @return
     * @throws IOException
     */
    public static int add(IndexWriter writer, Searchable searchable) throws IOException {
        Document doc = SearchHelper.obj2Doc(searchable);
        return add(writer, doc);
    }

    /**
     * 批量添加索引
     *
     * @param docs
     * @return
     * @throws IOException
     */
    public static void add(IndexWriter writer, Collection<Document> docs) throws IOException {
        if (docs == null || docs.size() == 0) {
            return;
        }
        int count = 0;
        try {
            writer.addDocuments(docs);
            writer.commit();
        } finally {
        }
    }

    /**
     * 批量添加索引
     *
     * @param objs
     * @return
     * @throws IOException
     */
    public static void addAll(IndexWriter writer, Collection<Searchable> objs) throws IOException {
        add(writer, SearchHelper.objs2Docs(objs));
    }

    /**
     * 更新一条索引
     *
     * @param term
     * @param doc
     * @return
     * @throws IOException
     */
    public static int update(IndexWriter writer, Term term, Document doc) throws IOException {
        int count = 0;
        try {
            writer.updateDocument(term, doc);
            writer.commit();
            count++;
        } finally {
            closeIndexWriter(writer);
            return count;
        }
    }

    public static void update(IndexWriter writer, Term term, Collection<Document> docs) throws IOException {
        try {
            writer.updateDocuments(term, docs);
            writer.commit();
        } finally {
            closeIndexWriter(writer);
        }
    }

    public static void updateAll(IndexWriter writer, Term term, Collection<Searchable> docs) throws IOException {
        try {
            writer.updateDocuments(term, SearchHelper.objs2Docs(docs));
            writer.commit();
        } finally {
            closeIndexWriter(writer);
        }
    }

    public static void update(IndexWriter writer, Collection<Document> docs) throws IOException {
        delete(writer, docs);
        add(writer, docs);
    }

    public static void updateAll(IndexWriter writer, Collection<Searchable> objs) throws IOException {
        deleteAll(writer, objs);
        addAll(writer, objs);
    }

    public static void delete(IndexWriter writer, Term term) throws IOException {
        try {
            writer.deleteDocuments(term);
            writer.commit();
        } finally {
            closeIndexWriter(writer);
        }
    }

    public static int delete(IndexWriter writer, Collection<Document> docs) throws IOException {
        if (docs == null || docs.size() == 0) {
            return 0;
        }
        int count = 0;
        try {
            for (Document doc : docs) {
                writer.deleteDocuments(new Term(SearchHelper.FN_ID, String.valueOf(doc.get(SearchHelper.FN_ID))));
                count++;
            }
            writer.commit();
        } finally {
            closeIndexWriter(writer);
            return count;
        }
    }

    public static int deleteAll(IndexWriter writer, Collection<Searchable> objs) throws IOException {
        return delete(writer, SearchHelper.objs2Docs(objs));
    }

    /**
     * 高亮再查询
     *
     * @param builder 查询构建器
     * @param model   分页数据模型
     * @param fields  需要从Document中获取的字段,为空时为全部获取
     */
    public static void searchHighlight(IndexSearcher searcher, QueryBuilder builder, Pager<MapContainer> model, Set<String> fields) {
        try {
//            TopDocs docs= searcher.search(builder.getQuery(), builder.getFilter(), Integer.MAX_VALUE);
            TopDocs docs = searcher.search(builder.getQuery(), Integer.MAX_VALUE);
            model.setTotalCount(docs.totalHits);
            ScoreDoc[] sd = docs.scoreDocs;

            int start = (model.getPageIndex() - 1) * model.getPageSize();
            int end = model.getPageIndex() * model.getPageSize();
            for (int i = start; i < end && i < sd.length; i++) {
                Document doc = null;
                if (fields == null || fields.isEmpty())
                    doc = searcher.doc(sd[i].doc);
                else
                    doc = searcher.doc(sd[i].doc, fields);

                MapContainer mc = DocConverter.convert(doc, builder.getHighlighter());
                for (String filter : builder.getHighlighter()) {
                    String content = doc.get(filter);
                    if (content == null || content.trim().length() == 0) {
                        logger.warn("field " + filter + " is null");
                        continue;
                    }

                    mc.put(filter, SearchHelper.getBestFragment(builder.getQuery(), content, filter, analyzer));
                }

                model.addContent(mc);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void searchHighlight(IndexSearcher searcher, QueryBuilder builder, Pager<MapContainer> model) {
        searchHighlight(searcher, builder, model, null);
    }
}
