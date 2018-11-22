package com.elab.core.collections;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuhx on 2017/1/2 13:51
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class MapAdapter extends XmlAdapter<MapElements[],HashMap<String,String>> {

    /**
     * Convert a value type to a bound type.
     *
     * @param v The value to be converted. Can be null.
     * @throws Exception if there's an error during the conversion. The caller is responsible for
     *                   reporting the error to the user through {@link javax.xml.bind.ValidationEventHandler}.
     */
    @Override
    public HashMap<String, String> unmarshal(MapElements[] v) throws Exception {
        HashMap<String, String> r = new HashMap<String, String>();
        for (MapElements mapelement : v)
            r.put(mapelement.key, mapelement.value);
        return r;
    }

    /**
     * Convert a bound type to a value type.
     *
     * @param v The value to be convereted. Can be null.
     * @throws Exception if there's an error during the conversion. The caller is responsible for
     *                   reporting the error to the user through {@link javax.xml.bind.ValidationEventHandler}.
     */
    @Override
    public MapElements[] marshal(HashMap<String, String> v) throws Exception {
        MapElements[] mapElements = new MapElements[v.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : v.entrySet())
            mapElements[i++] = new MapElements(entry.getKey(), entry.getValue());
        return mapElements;
    }
}

