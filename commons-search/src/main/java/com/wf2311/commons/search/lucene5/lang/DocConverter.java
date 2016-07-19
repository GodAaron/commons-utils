package com.wf2311.commons.search.lucene5.lang;

import com.wf2311.commons.lang.plugin.MapContainer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author wf2311
 * @date 2016/03/13 14:21.
 */
public class DocConverter {
    private DocConverter() {
    }

    public static MapContainer convert(Document obj) {
        MapContainer mc = new MapContainer();
        for (IndexableField field : obj.getFields()) {
            mc.put(field.name(), field.stringValue());
        }
        return mc;
    }

    public static MapContainer convert(Document obj, String... filters) {
        return convert(obj, Arrays.asList(filters));
    }

    public static MapContainer convert(Document obj, Collection<String> filters) {
        MapContainer mc = new MapContainer();
        for (IndexableField field : obj.getFields()) {
            if (filters.contains(field.name()))
                continue;
            mc.put(field.name(), field.stringValue());
        }

        return mc;
    }
}
