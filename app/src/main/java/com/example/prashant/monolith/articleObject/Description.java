package com.example.prashant.monolith.articleObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Description {
    @SerializedName("content")
    @Expose
    private String[] content;

    @SerializedName("em")
    @Expose
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