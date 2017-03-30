package com.example.prashant.monolith.articleObject;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Item {

    @Element
    private String pubDate;

    @Element
    private String title;

    @Element
    private String description;

    @Element
    private String link;

    @Element(required = false)
    private Thumbnail thumbnail;

    public Thumbnail getthumbnail() {
        if (thumbnail != null) {
            return thumbnail;
        } else {
            Thumbnail t = new Thumbnail();
            t.setUrl("https://images.unsplash.com/photo-1447433693259-c8549e937d62?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=240&fit=max&s=c35d20b6b5c18c91290fd5fa4c6c634a");
            t.setHeight("240");
            t.setWidth("240");
            return t;
        }
    }

    public void setthumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "ClassPojo [pubDate = " + pubDate + ", title = " + title + ", description = " + description + ", link = " + link + "]";
    }
}