package com.wf2311.commons.search.lucene5.lang;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

import java.io.IOException;

/**
 * @author wf2311
 * @date 2016/03/13 13:54.
 */
public class LuceneUtil {
    public static FieldType directType(){
        FieldType directType = new FieldType();
        directType.setIndexOptions(IndexOptions.DOCS);
        directType.setStored(true);

        return directType;
    }

    public static void closeQuietly(TokenStream stream) {
        if (stream == null)
            return;

        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
