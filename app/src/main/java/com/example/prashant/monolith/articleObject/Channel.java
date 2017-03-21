package com.example.prashant.monolith.articleObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Channel {

    @SerializedName("pubDate")
    @Expose
    private String pubDate;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("lastBuildDate")
    @Expose
    private String lastBuildDate;

    @SerializedName("item")
    @Expose
    private Item[] item;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("ttl")
    @Expose
    private String ttl;

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

    public String getLastBuildDate () {
        return lastBuildDate;
    }

    public void setLastBuildDate (String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public Item[] getItem () {
        return item;
    }

    public void setItem (Item[] item) {
        this.item = item;
    }

    public String getLanguage () {
        return language;
    }

    public void setLanguage (String language) {
        this.language = language;
    }

    public String getTtl () {
        return ttl;
    }

    public void setTtl (String ttl) {
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return "ClassPojo [pubDate = "+pubDate+", title = "+title+", description = "+description+", link = "+link+", lastBuildDate = "+lastBuildDate+", item = "+item+", language = "+language+", ttl = "+ttl+"]";
    }
}