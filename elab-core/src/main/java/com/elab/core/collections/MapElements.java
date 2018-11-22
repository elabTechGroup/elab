package com.elab.core.collections;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author liuhx on 2017/1/2 13:51
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class MapElements {
    @XmlElement
    public String key;
    @XmlElement
    public String value;
    @SuppressWarnings("unused")
    private MapElements() {
    }
    public MapElements(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
