package com.example.prashant.monolith.articleObject;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(strict=false)
public class Description {

    @ElementList(inline = true,required = false)
    private ArrayList<String> content;

    private String em;

    public ArrayList<String> getContent () {
        return content;
    }

    public void setContent (ArrayList<String> content) {
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