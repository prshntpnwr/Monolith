package com.app.prashant.monolith.articleObject;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Link {

    @Element
    private String rel;

    @Element
    private String type;

    @Element
    private String href;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "ClassPojo [rel = " + rel + ", type = " + type + ", href = " + href + "]";
    }
}