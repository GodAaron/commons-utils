package com.wf2311.commons.search.lucene5;

import com.wf2311.commons.domain.Pager;
import com.wf2311.commons.lang.ArrayUtils;
import com.wf2311.commons.lang.plugin.MapContainer;
import com.wf2311.commons.search.IKAnalyzer.IKAnalyzer5x;
import com.wf2311.commons.search.Searchable;
import com.wf2311.commons.search.lucene5.exception.LuceneException;
import com.wf2311.commons.search.lucene5.lang.DocConverter;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 搜索引擎服务提供类
 *
 * @author wf2311
 */
public final class SearchEngineer {
    private static final Logger logger = Logger.getLogger(SearchEngineer.class);

    private final TrackingIndexWriter nrtWriter;
    private final IndexWriter writer;
    private final ReferenceManager<IndexSearcher> manager;
    private final Analyzer analyzer;
    private final ControlledRealTimeReopenThread<IndexSearcher> nmrt;
    /* 每次IndexReader的reopen都会导致generation增1 */
    private volatile long reopenToken;
    @Deprecated
    private static SearchEngineer instance;
    private final Path path;
    private final Directory directory;

    public Path getPath() {
        return path;
    }

    public Directory getDirectory() {
        return directory;
    }

    /**
     * @return
     */
    public static SearchEngineer init(String... storePath) {
        if (storePath.length < 1) {
            throw new LuceneException("请指定索引存储路径");
        }
        return new SearchEngineer(storePath);
    }

    /**
     *获取SearchEngineer单例对象
     * @param basePath
     * @param indexDir
     * @return
     */
    @Deprecated
    public static SearchEngineer getInstance(String basePath,String indexDir){
        if (instance==null){
            instance=new SearchEngineer(basePath, indexDir);
        }
        return instance;
    }

    private SearchEngineer(String... storePath) {
        try {
            /*定义索引存放目录*/
            //方式1：存放在内存中
            //Directory directory = new RAMDirectory();
            //方式2：存放在指定硬盘目录中
            String[] morePath = ArrayUtils.subArray(storePath, 1, storePath.length - 1);
            path = Paths.get(storePath[0], morePath);
            directory = FSDirectory.open(path);

             /*创建 IndexWriterConfig*/
            //方式1：使用lucene标准分词类
            // analyzer = new StandardAnalyzer();
            //方式2：使用最细粒度分词
            analyzer = new IKAnalyzer5x(true);
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            LogMergePolicy mergePolicy = new LogByteSizeMergePolicy();
            // 达到5个文件时就和合并,默认10个
            mergePolicy.setMergeFactor(5);
            indexWriterConfig.setMergePolicy(mergePolicy);
            indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
            writer = new IndexWriter(directory, indexWriterConfig);
            nrtWriter = new TrackingIndexWriter(writer);
            manager = new SearcherManager(writer, true, null);

            /*当前线程结束时，自动关闭IndexWriter，使用Runtime对象*/
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        shutdown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            nmrt = new ControlledRealTimeReopenThread<>(nrtWriter, manager, 5.0, 0.05);
            nmrt.setName("reopen thread");
            nmrt.start();
        } catch (IOException e) {
            throw new IllegalStateException("Lucene index could not be created: " + e.getMessage());
        }
    }

    public IndexWriter getIndexWriter() {
        return writer;
    }

    /**
     * 此方法返回切换Directory<br>
     * 要关闭复合文件格式(除段信息文件，锁文件，以及删除的文件外，其他的一系列索引文件压缩一个后缀名为cfs的文件,默认为关闭)
     * 生成复合文件将消耗更多的时间,但它有更好的查询效率,适合查询多更新少的场合<br>
     * IndexWriterConfig. setUseCompoundFile(false);
     *
     * @param indexPath
     * @return
     * @throws IOException
     */
    Directory initDirectory(String indexPath) throws IOException {
        Path path = Paths.get(indexPath);
        // 添加放置在nio文件里的索引文件,由主索引负责打开的文件
        // .fdt文件用于存储具有Store.YES属性的Field的数据；.fdx是一个索引，用于存储Document在.fdt中的位置
        Set<String> files = new HashSet<String>();
        files.add("fdt");
        files.add("fdx");

        FSDirectory dir = FSDirectory.open(path);// 装载磁盘索引
        /* RAMDirectory来访问索引其速度和效率都是非常优异的 */
        RAMDirectory map = new RAMDirectory(dir, IOContext.READ);
        NIOFSDirectory nio = new NIOFSDirectory(path);// 基于并发大文件的NIO索引
        // 组合不同Directory的优点
        FileSwitchDirectory fsd = new FileSwitchDirectory(files, nio, map, true);

        return fsd;
    }

    /**
     * 获取当前的分词器
     *
     * @return
     */
    public Analyzer getAnalyzer() {
        return analyzer;
    }

    /**
     * 添加文档
     * @param doc
     * @throws LuceneException
     */
    public int add(Document doc){
        int count=0;
        try {
            reopenToken = nrtWriter.addDocument(doc);
            count++;
        } catch (IOException e) {
            logger.error("Error while in Lucene index operation: {}", e);
//            throw new LuceneException("Error while add index", e);
        } finally {
            commit();
            return count;
        }
    }

    public int add(Searchable searchable){
        return add(SearchHelper.obj2Doc(searchable));
    }

    /**
     * 批量添加文档
     *
     * @param docs
     * @throws LuceneException
     */
    public int add(Collection<Document> docs){
        long start = System.currentTimeMillis();
        int count=0;
        try {
            for (Document doc : docs) {
                nrtWriter.updateDocument(new Term(SearchHelper.FN_ID, doc.get(SearchHelper.FN_ID)), doc);
                count++;
            }
//            count+=nrtWriter.addDocuments(docs);
            reopenToken = nrtWriter.getGeneration();
        } catch (IOException e) {
            logger.error("Error while in Lucene index operation: {}", e);
//            throw new LuceneException("Error while add index", e);
        } finally {
            commit();
            long end = System.currentTimeMillis();
//            logger.debug(String.format("本次写入索引共添加%d条索引，用时：%s", count, DateUtils.getSpeed(start, end)));
            return count;
        }
    }

    public int addAll(Collection<Searchable> searchables){
        return add(SearchHelper.objs2Docs(searchables));
    }



    public void update(Term term, Document doc) throws LuceneException {
        try {
            reopenToken = nrtWriter.updateDocument(term, doc);
        } catch (IOException e) {
            logger.error("Error in lucene re-indexing operation: {}", e);
            throw new LuceneException("Error while update index", e);
        } finally {
            commit();
        }
    }

    /**
     * 删除索引
     *
     * @param term
     * @throws LuceneException
     */
    public void delete(Term term) {
        try {
            reopenToken = nrtWriter.deleteDocuments(term);
        } catch (IOException e) {
            logger.error("Error in lucene re-indexing operation: {}", e);
//            throw new LuceneException("Error while remove index", e);
        } finally {
            commit();
        }
    }

    /**
     * 删除索引
     *
     * @param docs
     * @throws LuceneException
     */
    public void delete(Collection<Document> docs){
        try {
            for (Document doc:docs){
                reopenToken=nrtWriter.deleteDocuments(new Term(SearchHelper.FN_ID,doc.get(SearchHelper.FN_ID)));
            }
//            reopenToken = nrtWriter.deleteDocuments();
        } catch (IOException e) {
            logger.error("Error in lucene re-indexing operation: {}", e);
            throw new LuceneException("Error while remove index", e);
        } finally {
            commit();
        }
    }

    /**
     * 清空索引
     *
     * @throws LuceneException
     */
    public void truncate() throws LuceneException {
        try {
            reopenToken = nrtWriter.deleteAll();
        } catch (IOException e) {
            logger.error("Error truncating lucene index: {}", e);
            throw new LuceneException("Error while tuncate index", e);
        } finally {
            commit();
        }
    }

    /**
     * 获取所有文档数,不包含删除文档数
     *
     * @return
     */
    public int docCount() {
    /* 注:numDocs()返回为包含删除文档数 */
        return writer.maxDoc();
    }

    /**
     * 与指定文档相似搜索
     *
     * @param docNum 给定文档编号
     * @param fields
     * @return
     * @throws IOException
     */
    public List<MapContainer> like(int docNum, String[] fields) throws IOException {
        MoreLikeThis mlt = new MoreLikeThis(DirectoryReader.open(writer, false));
        mlt.setFieldNames(fields); // 设定查找域
        mlt.setMinTermFreq(2); // 一篇文档中一个词语至少出现次数，小于这个值的词将被忽略,默认值是2
        mlt.setMinDocFreq(3); // 一个词语最少在多少篇文档中出现，小于这个值的词会将被忽略，默认值是5
        mlt.setAnalyzer(getAnalyzer());
        Query query = mlt.like(docNum);

        return search(query, 5, null);
    }

    public void searchHighlight(QueryBuilder builder, Pager<MapContainer> model) {
        searchHighlight(builder, model, null);
    }

    private ScoreDoc lastScoreDoc(IndexSearcher searcher, QueryBuilder builder, Pager<MapContainer> model)
            throws IOException {
        if (model.getPageIndex() == 1)
            return null;

        int num = model.getPageSize() * (model.getPageIndex() - 1);
//        TopDocs tds = searcher.search(builder.getQuery(), builder.getFilter(), num);
        TopDocs tds = searcher.search(builder.getQuery(), num);
        return tds.scoreDocs[num - 1];
    }

    /**
     * 高亮查询(适合只做下一页搜索)
     *
     * @param builder
     * @param model
     * @param fields
     */
    public void searchAfterHighlight(QueryBuilder builder, Pager<MapContainer> model, Set<String> fields) {
        IndexSearcher searcher = null;
        try {
            nmrt.waitForGeneration(reopenToken);
            searcher = manager.acquire();
            // 先获取上一页的最后一个元素
            ScoreDoc last = lastScoreDoc(searcher, builder, model);
            // 通过最后一个元素搜索下页的pageSize个元素
            TopDocs docs = searcher.searchAfter(last, builder.getQuery(), model.getPageSize());
            model.setTotalCount(docs.totalHits);
            for (ScoreDoc sd : docs.scoreDocs) {
                Document doc = null;
                if (fields == null || fields.isEmpty())
                    doc = searcher.doc(sd.doc);
                else
                    doc = searcher.doc(sd.doc, fields);

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
        } finally {
            release(searcher);
        }
    }

    /**
     * 高亮再查询
     *
     * @param builder 查询构建器
     * @param model   分页数据模型
     * @param fields  需要从Document中获取的字段,为空时为全部获取
     */
    public void searchHighlight(QueryBuilder builder, Pager<MapContainer> model, Set<String> fields) {
        IndexSearcher searcher = null;
        try {
            nmrt.waitForGeneration(reopenToken);
            searcher = manager.acquire();
//            TopDocs docs= searcher.search(builder.getQuery(), builder.getFilter(), Integer.MAX_VALUE);
            TopDocs docs=searcher.search(builder.getQuery(),Integer.MAX_VALUE);
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
        } finally {
            release(searcher);
        }
    }

    /**
     * 搜索指定域的文档
     *
     * @param query
     * @param max
     * @param fields
     * @return
     */
    private List<MapContainer> search(Query query, int max, Set<String> fields) {
        List<MapContainer> result = new LinkedList<MapContainer>();
        IndexSearcher searcher = null;
        try {
            nmrt.waitForGeneration(reopenToken);
            searcher = manager.acquire();
            TopDocs docs = searcher.search(query, max);
            ScoreDoc[] sd = docs.scoreDocs;

            for (int i = 0; i < docs.totalHits; i++) {
                Document doc = null;
                if (fields == null || fields.isEmpty())
                    doc = searcher.doc(sd[i].doc);
                else
                    doc = searcher.doc(sd[i].doc, fields);

                MapContainer mc = DocConverter.convert(doc);
                result.add(mc);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            release(searcher);
        }

        return result;
    }

    private void commit() {
        try {
            writer.commit();
        } catch (IOException e) {
            logger.error("Error commit index");
        }
    }

    private void release(IndexSearcher searcher) {
        if (searcher == null)
            return;

        try {
            manager.release(searcher);
        } catch (IOException e) {
            logger.error("Error releaser IndexSearcher");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        shutdown();

        super.finalize();
    }

    public void shutdown() {
        try {
            nmrt.interrupt();
            nmrt.close();
            writer.commit();
            writer.close();
        } catch (Exception e) {
            logger.error("Error while closing lucene index: {}", e);
        }
    }

    /**
     * 这个方法尽量不要调用，除非极少的索引维护
     */
    public void optimize() {
        try {
            writer.forceMerge(1);
        } catch (IOException e) {
            logger.error("Error optimizing lucene index {}", e);
        }
    }
}
