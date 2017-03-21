package com.example.prashant.monolith.articleObject;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Image {
    private String title;

    private String description;

    private String link;

    private String url;

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getLink () {
        return link;
    }

    public void setLink (String link) {
        this.link = link;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ClassPojo [title = "+title+", description = "+description+", link = "+link+", url = "+url+"]";
    }
}