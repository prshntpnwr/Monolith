package com.example.prashant.monolith.flickrObject;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Collection
{
    private String id;

    private String title;

    private String datecreate;

    private String child_count;

    private String iconlarge;

    private String description;

    private String secret;

    private String server;

    private String iconsmall;

    private Iconphotos iconphotos;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getDatecreate ()
    {
        return datecreate;
    }

    public void setDatecreate (String datecreate)
    {
        this.datecreate = datecreate;
    }

    public String getChild_count ()
    {
        return child_count;
    }

    public void setChild_count (String child_count)
    {
        this.child_count = child_count;
    }

    public String getIconlarge ()
    {
        return iconlarge;
    }

    public void setIconlarge (String iconlarge)
    {
        this.iconlarge = iconlarge;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getSecret ()
    {
        return secret;
    }

    public void setSecret (String secret)
    {
        this.secret = secret;
    }

    public String getServer ()
    {
        return server;
    }

    public void setServer (String server)
    {
        this.server = server;
    }

    public String getIconsmall ()
    {
        return iconsmall;
    }

    public void setIconsmall (String iconsmall)
    {
        this.iconsmall = iconsmall;
    }

    public Iconphotos getIconphotos ()
    {
        return iconphotos;
    }

    public void setIconphotos (Iconphotos iconphotos)
    {
        this.iconphotos = iconphotos;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", title = "+title+", datecreate = "+datecreate+", child_count = "+child_count+", iconlarge = "+iconlarge+", description = "+description+", secret = "+secret+", server = "+server+", iconsmall = "+iconsmall+", iconphotos = "+iconphotos+"]";
    }
}