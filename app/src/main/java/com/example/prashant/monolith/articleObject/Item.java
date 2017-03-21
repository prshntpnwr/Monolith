package com.example.prashant.monolith.articleObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Item {

    @SerializedName("pubDate")
    @Expose
    private String pubDate;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private Description description;

    @SerializedName("link")
    @Expose
    private link link;

    @SerializedName("thumbnail")
    @Expose
    private thumbnail thumbnail;

    public thumbnail getthumbnail () {
        return thumbnail;
    }

    public void setthumbnail (thumbnail thumbnail) {
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

    public link getLink () {
        return link;
    }

    public void setLink (link link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "ClassPojo [pubDate = "+pubDate+", title = "+title+", description = "+description+", link = "+link+"]";
    }
}