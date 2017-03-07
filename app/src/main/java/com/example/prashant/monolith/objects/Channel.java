package com.example.prashant.monolith.objects;

import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(strict=false)

public class Channel {

    private String title;

    private String managingEditor;

    private String description;

    private String docs;

    private String link;

    private ArrayList<Item> item;

    private String language;

    private String webMaster;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getManagingEditor ()
    {
        return managingEditor;
    }

    public void setManagingEditor (String managingEditor)
    {
        this.managingEditor = managingEditor;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getDocs ()
    {
        return docs;
    }

    public void setDocs (String docs)
    {
        this.docs = docs;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

    public ArrayList<Item> getItem ()
    {
        return item;
    }

    public void setItem (ArrayList<Item> item)
    {
        this.item = item;
    }

    public String getLanguage ()
    {
        return language;
    }

    public void setLanguage (String language)
    {
        this.language = language;
    }

    public String getWebMaster ()
    {
        return webMaster;
    }

    public void setWebMaster (String webMaster)
    {
        this.webMaster = webMaster;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+", managingEditor = "+managingEditor+", description = "+description+", docs = "+docs+", link = "+link+", item = "+item+", language = "+language+", webMaster = "+webMaster+"]";
    }
}