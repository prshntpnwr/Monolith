package com.app.prashant.monolith.articleObject;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(strict = false)
public class Channel {

    @Element
    private String pubDate;

    @Element
    private String title;

    @Element
    private String description;

    @Path("link")
    private String link;

    @Element
    private String lastBuildDate;

    @ElementList(inline = true)
    private ArrayList<Item> item;

    @Element
    private String language;

    @Element
    private String ttl;

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

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public void setItem(ArrayList<Item> item) {
        this.item = item;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return "ClassPojo [pubDate = " + pubDate + ", title = " + title + ", description = " + description + ", link = " + link + ", lastBuildDate = " + lastBuildDate + ", item = " + item + ", language = " + language + ", ttl = " + ttl + "]";
    }
}