package com.example.prashant.monolith.articleObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class ArticleObject{

    @SerializedName("rss")
    @Expose
    private Rss rss;

    public Rss getRss () {
        return rss;
    }

    public void setRss (Rss rss) {
        this.rss = rss;
    }

    @Override
    public String toString() {
        return "ClassPojo [rss = "+rss+"]";
    }
}