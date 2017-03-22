package com.example.prashant.monolith.articleObject;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Description {

    @Element
    private String[] content;

    @Element
    private String em;

    public String[] getContent () {
        return content;
    }

    public void setContent (String[] content) {
        this.content = content;
    }

    public String getEm () {
        return em;
    }

    public void setEm (String em) {
        this.em = em;
    }

    @Override
    public String toString() {
        return "ClassPojo [content = "+content+", em = "+em+"]";
    }
}