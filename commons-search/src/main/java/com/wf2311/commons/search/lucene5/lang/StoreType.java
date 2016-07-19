package com.wf2311.commons.search.lucene5.lang;

import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

/**
 * @author wf2311
 * @date 2016/03/14 11:16.
 */
public class StoreType {
    public static FieldType searchType() {
        FieldType searchType = new FieldType();
        searchType.setStored(true);
        searchType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        /* 设置分词 */
        searchType.setTokenized(true);

        return searchType;
//        return TextField.TYPE_STORED;
    }

    public static FieldType indexType() {
        FieldType indexType = new FieldType();
        indexType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        indexType.setTokenized(false);
        indexType.setStored(true);
        return indexType;
    }

    public static FieldType storeType() {
        FieldType storeType = new FieldType();
        storeType.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        storeType.setTokenized(false);
        storeType.setStored(true);
        return storeType;
    }
}
