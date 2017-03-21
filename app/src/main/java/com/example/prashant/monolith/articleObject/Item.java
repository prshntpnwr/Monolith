package com.example.prashant.monolith.articleObject;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Item {

    private String pubDate;

    private String title;

    private Description description;

    private String link;

    private String thumbnail;

    public String thumbnail () {
        return pubDate;
    }

    public void thumbnail (String thumbnail) {
        this.thumbnail = thumbnail;
    }


    public String getPubDate () {
        return pubDate;
    }

    public void setPubDate (String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public Description getDescription () {
        return description;
    }

    public void setDescription (Description description) {
        this.description = description;
    }

    public String getLink () {
        return link;
    }

    public void setLink (String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "ClassPojo [pubDate = "+pubDate+", title = "+title+", description = "+description+", link = "+link+"]";
    }
}