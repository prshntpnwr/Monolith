package com.example.prashant.monolith.articleObject;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import static android.R.attr.path;

@Root(strict = false)
public class Item {

    @Element
    private String pubDate;

    @Element
    private String title;

    @Element
    private String description;

//    @Path("link")
    @Element
    private String link;

    @Element(required = false)
    private thumbnail thumbnail;

    @Path("thumbnail")
    public thumbnail getthumbnail() {
        if (thumbnail != null) {
            return thumbnail;
        } else {
            thumbnail t = new thumbnail();
            t.setUrl("https://unsplash.com/search/space?photo=OVO8nK-7Rfs");
            t.setHeight("300");
            t.setWidth("300");
            return t;
        }
    }

    public void setthumbnail(thumbnail thumbnail) {
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