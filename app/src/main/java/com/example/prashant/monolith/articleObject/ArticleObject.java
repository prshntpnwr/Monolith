package com.example.prashant.monolith.articleObject;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class ArticleObject {

    @Element
    private Rss rss;

    public Rss getRss() {
        return rss;
    }

    public void setRss(Rss rss) {
        this.rss = rss;
    }

    @Override
    public String toString() {
        return "ClassPojo [rss = " + rss + "]";
    }
}