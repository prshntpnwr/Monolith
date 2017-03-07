package com.example.prashant.monolith.objects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Item {

    private String pubDate;

    private String title;

    @Element(required=false)
    private Enclosure enclosure;

    private String description;

    private String link;

    public String getPubDate ()
    {
        return pubDate;
    }

    public void setPubDate (String pubDate)
    {
        this.pubDate = pubDate;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public Enclosure getEnclosure ()
    {
        return enclosure;
    }

    public void setEnclosure (Enclosure enclosure)
    {
        this.enclosure = enclosure;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pubDate = "+pubDate+", title = "+title+", enclosure = "+enclosure+",  description = "+description+", link = "+link+"]";
    }
}
